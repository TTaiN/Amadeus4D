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
 * Filename: Pause.java
 */

public class Pause
{
    public Pause(IMessage message, MusicPermissionManager permissions, GuildProvider provider) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }
        this.pause(message.getChannel(), provider);
    }

    public void pause(IChannel textChannel, GuildProvider provider)
    {
        if (!provider.getPlayer().isPaused())
        {
            provider.getPlayer().setPaused(true);
            Communicator.sendTempMessage(textChannel, "Music player is now paused.");
        }
        else Communicator.sendTempMessage(textChannel, "Music player is already paused.");
    }
}
