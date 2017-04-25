package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IVoiceChannel;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Leave.java
 */

public class Leave
{
    public Leave(IMessage message, MusicPermissionManager permissions, HashMap<Long, GuildProvider> players) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }

        this.leave(message.getChannel(), message.getGuild().getVoiceChannels());
        this.removeGuildProvider(message.getGuild().getID(), players);
    }

    private void leave(IChannel textChannel, List<IVoiceChannel> voiceChannels)
    {
        for (IVoiceChannel voiceChannel : voiceChannels)
        {
            if (voiceChannel.isConnected())
            {
                voiceChannel.leave();
                Communicator.sendTempMessage(textChannel, "I've left **" + voiceChannel.getName() + "**.");
            }
        }
    }

    private void removeGuildProvider(String guildId, HashMap<Long, GuildProvider> players)
    {
        long id = Long.parseLong(guildId);
        GuildProvider provider = players.get(id);
        provider.destroy();
        players.remove(id);
    }
}
