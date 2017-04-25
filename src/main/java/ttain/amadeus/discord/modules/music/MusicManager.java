package ttain.amadeus.discord.modules.music;

import ttain.amadeus.discord.database.DatabaseManager;
import ttain.amadeus.discord.database.exceptions.NoActiveKeysException;
import ttain.amadeus.discord.database.exceptions.TooManyActiveKeysException;
import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.helpers.IO;
import ttain.amadeus.discord.modules.general.GeneralManager;
import ttain.amadeus.discord.modules.music.permissions.commands.*;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.permissions.exceptions.ModeratorNotFoundException;
import ttain.amadeus.discord.modules.music.permissions.exceptions.NotOwnerOrDeveloperException;
import ttain.amadeus.discord.modules.music.player.commands.*;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music
 * Filename: MusicManager.java
 */

public class MusicManager
{
    public static final String key = "/";

    private String youtube_api_key;
    private final IDiscordClient client;
    private final AudioPlayerManager playerManager;
    private final MusicPermissionManager permissions;
    private HashMap<Long, GuildProvider> players;

    public MusicManager(IDiscordClient client, DatabaseManager databaseManager) throws SQLException, NoActiveKeysException, TooManyActiveKeysException
    {
        this.client = client;
        this.players = new HashMap<>();
        this.permissions = new MusicPermissionManager(databaseManager);
        this.youtube_api_key = databaseManager.getAPIKey("YouTube");

        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    @EventSubscriber
    public void onGuildCreateEvent(GuildCreateEvent event) // I will most likely create a GuildManager in the future.
    {
        try
        {
            if (!permissions.isModerator(event.getGuild().getID(), event.getGuild().getOwnerID()))
            {
                permissions.addModerator(event.getGuild().getID(), event.getGuild().getOwnerID());
                new java.util.Timer().schedule
                (
                    new java.util.TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            sendWelcome(event.getGuild().getOwner());
                        }
                    },
                    5000
                );
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event)
    {
        if (event.getMessage().getContent().startsWith(key))
        {
            String[] arguments = event.getMessage().getContent().substring(key.length()).split(" ");
            try
            {
                if (permissions.isListening(event.getMessage().getGuild().getID(), event.getMessage().getChannel().getID()))
                {
                    checkUserCommands(event.getMessage(), arguments);
                }
                else
                {
                    checkOwnerCommands(event.getMessage(), arguments, false);
                }
            }
            catch (SQLException e)
            {
                Communicator.sendTempMessage(event.getMessage().getChannel(), "Database error. Command terminated.");
                e.printStackTrace();
            }
        }
    }

    private void sendWelcome(IUser owner) // I will most likely create a GuildManager in the future.
    {
        try
        {
            IPrivateChannel channel = client.getOrCreatePMChannel(owner);
            channel.sendMessage(IO.textFileToString("./prompts/new_guild.txt", Charset.defaultCharset()).replace("[music_key]", this.key).replace("[general_key]", GeneralManager.key));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String cleanTarget(String target)
    {
        if (target == null)
        {
            return null;
        }
        return target.replaceAll("[^0-9]+", "");
    }

    private String getParameters(String[] arguments)
    {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 1; i != arguments.length; i++)
        {
            result.add(arguments[i]);
        }
        return String.join(" ", result);
    }

    private GuildProvider getGuildProvider(IGuild guild)
    {
        long id = Long.parseLong(guild.getID());
        GuildProvider provider = players.get(id);

        if (provider == null)
        {
            provider = new GuildProvider(playerManager);
            players.put(id, provider);
        }

        guild.getAudioManager().setAudioProvider(provider.getAudioProvider());

        return provider;
    }

    private void checkUserCommands(IMessage message, String[] arguments)
    {
        try
        {
            switch (arguments[0].toLowerCase()) // I could make permissions static, but I want to stick with my design.
            {
                case "c":
                case "commands":
                    new Commands(message.getChannel());
                    break;
                case "h":
                case "help":
                    new Help(message.getChannel());
                    break;
                case "dj":
                case "djs":
                    Communicator.sendTempMessage(message.getChannel(), permissions.getPrintableDJs(message.getGuild().getID()));
                    break;
                case "mods":
                case "moderators":
                    Communicator.sendTempMessage(message.getChannel(), permissions.getPrintableModerators(message.getGuild().getID()));
                default:
                    checkDJCommands(message, arguments);
                    break;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Communicator.sendTempMessage(message.getChannel(), "Database error. Command terminated.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Communicator.sendTempMessage(message.getChannel(), "File/prompt error. Command terminated.");
        }
    }

    private void checkDJCommands(IMessage message, String[] arguments)
    {
        try
        {
            GuildProvider guildProvider = getGuildProvider(message.getGuild());
            switch (arguments[0].toLowerCase()) // I could make permissions static, but I want to stick with my design.
            {
                case "j":
                case "join":
                    new Join(message, permissions);
                    break;
                case "l":
                case "leave":
                    new Leave(message, permissions, players);
                    break;
                case "reset":
                    new Reset(message, permissions, playerManager, players);
                    break;
                case "r":
                case "request":
                    new Request(message, permissions, playerManager, guildProvider, arguments[1]);
                    break;
                case "s":
                case "skip":
                    new Skip(message, permissions, guildProvider, (arguments.length >= 2 ? arguments[1] : null));
                    break;
                case "yt":
                case "search":
                    new YoutubeSearch(client, youtube_api_key, message, permissions, playerManager, guildProvider, getParameters(arguments));
                    break;
                case "v":
                case "volume":
                    new Volume(message, permissions, guildProvider, arguments[1]);
                    break;
                case "resume":
                    new Resume(message, permissions, guildProvider);
                    break;
                case "pause":
                    new Pause(message, permissions, guildProvider);
                    break;
                case "sh":
                case "shuffle":
                    new Shuffle(message, permissions, guildProvider);
                    break;
                case "p":
                case "playing":
                    new Playing(message, permissions, guildProvider);
                    break;
                case "q":
                case "playlist":
                case "queue":
                    new Queue(message, permissions, guildProvider, (arguments.length >= 2 ? Integer.parseInt(arguments[1]) : 1));
                    break;
                case "loop":
                    new Loop(message, permissions, guildProvider);
                    break;
                case "remove":
                    new Remove(message, permissions, guildProvider, (arguments.length >= 2 ? arguments[1] : null));
                    break;
                default:
                    checkModeratorCommands(message, arguments);
                    break;
            }
        }
        catch (DJNotFoundException e)
        {
            Communicator.sendTempMessage(message.getChannel(), message.getAuthor() + ", you aren't a DJ.\n\nTo become a DJ, have the guild owner or a moderator enter the command **/ad**.\n\n");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Communicator.sendTempMessage(message.getChannel(), "Database error. Command terminated.");
        }
    }

    private void checkModeratorCommands(IMessage message, String[] arguments)
    {
        try
        {
            switch (arguments[0].toLowerCase()) // I could make permissions static, but I want to stick with my design.
            {
                case "adddj":
                    new AddDJ(message, permissions, cleanTarget(arguments[1]));
                    break;
                case "removedj":
                    new RemoveDJ(message, permissions,  cleanTarget(arguments[1]));
                    break;
                default:
                    checkOwnerCommands(message, arguments, true);
            }
        }
        catch (ModeratorNotFoundException e)
        {
            Communicator.sendTempMessage(message.getChannel(), message.getAuthor() + ", you aren't a moderator.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Communicator.sendTempMessage(message.getChannel(), "Database error. Command terminated.");
        }
    }

    private void checkOwnerCommands(IMessage message, String[] arguments, boolean listening)
    {
        try
        {
            switch (arguments[0].toLowerCase()) // I could make permissions static, but I want to stick with my design.
            {
                case "addmod":
                case "addmoderator":
                    new AddModerator(message, permissions, cleanTarget(arguments[1]));
                    break;
                case "removemod":
                case "removemoderator":
                    new RemoveModerator(message, permissions, cleanTarget(arguments[1]));
                    break;
                case "li":
                case "listen":
                    new Listen(message, permissions);
                    break;
                case "ul":
                case "unlisten":
                    new Unlisten(message, permissions);
                    break;
                default: // Gets here if a user tries to do a command but the channel isn't being listened to.
                    if (!listening)
                    {
                        Communicator.sendTempMessage(message.getChannel(), "Not listening for music commands in this channel.\n\nTo listen to this channel for commands, have the guild owner enter the command **-listen**.\n\n" + permissions.getPrintableChannels(message.getGuild().getID()));
                    }
                    break;
            }
        }
        catch (NotOwnerOrDeveloperException e)
        {
            Communicator.sendTempMessage(message.getChannel(), message.getAuthor() + ", you aren't the owner of the guild.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Communicator.sendTempMessage(message.getChannel(), "Database error. Command terminated.");
        }
    }
}
