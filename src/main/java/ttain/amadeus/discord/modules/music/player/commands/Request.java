package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.AudioLoadHandler;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Request.java
 */

public class Request
{
    public Request(IMessage message, MusicPermissionManager permissions, AudioPlayerManager playerManager, GuildProvider provider, String identifier) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }

        this.queue(message.getChannel(), playerManager, provider, identifier);
    }

    private void queue(IChannel textChannel, AudioPlayerManager playerManager, GuildProvider provider, final String trackUrl)
    {
        playerManager.loadItemOrdered(provider, trackUrl, new AudioLoadHandler(textChannel, provider, trackUrl));
    }
}
