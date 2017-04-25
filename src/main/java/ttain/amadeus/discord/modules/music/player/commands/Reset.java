package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Reset.java
 */

public class Reset
{
    public Reset(IMessage message, MusicPermissionManager permissions, AudioPlayerManager playerManager, HashMap<Long, GuildProvider> players) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }

        this.reset(message.getChannel(), message.getGuild(), playerManager, players);
    }

    public void reset(IChannel textChannel, IGuild guild, AudioPlayerManager playerManager, HashMap<Long, GuildProvider> players)
    {
        long id = Long.parseLong(guild.getID());
        GuildProvider provider = players.get(id);

        if (provider != null) // Sanity check.
        {
            provider.destroy();
            players.remove(id);
        }

        provider = new GuildProvider(playerManager);
        players.put(id, provider);
        guild.getAudioManager().setAudioProvider(provider.getAudioProvider());

        Communicator.sendTempMessage(textChannel, "The music player has been reset.");
    }
}
