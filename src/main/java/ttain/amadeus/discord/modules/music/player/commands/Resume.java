package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Resume.java
 */

public class Resume
{
    public Resume(IMessage message, MusicPermissionManager permissions, GuildProvider provider) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }

        this.resume(message.getChannel(), provider);
    }

    public void resume(IChannel textChannel, GuildProvider provider)
    {
        if (provider.getPlayer().isPaused())
        {
            provider.getPlayer().setPaused(false);
            Communicator.sendTempMessage(textChannel, "Music player has resumed.");
        }
        else Communicator.sendTempMessage(textChannel, "Music player is not paused.");
    }
}
