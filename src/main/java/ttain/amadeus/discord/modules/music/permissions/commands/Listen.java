package ttain.amadeus.discord.modules.music.permissions.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.ChannelAlreadyListeningException;
import ttain.amadeus.discord.modules.music.permissions.exceptions.NotOwnerOrDeveloperException;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.commands
 * Filename: Listen.java
 */

public class Listen
{
    public Listen(IMessage message, MusicPermissionManager permissions) throws SQLException, NotOwnerOrDeveloperException
    {
        if (!message.getAuthor().equals(message.getGuild().getOwner()) && !permissions.isDeveloper(message.getAuthor().getID()))
        {
            throw new NotOwnerOrDeveloperException();
        }
        else
        {
            try
            {
                permissions.addChannel(message.getGuild().getID(), message.getChannel().getID());
                Communicator.sendTempMessage(message.getChannel(), "Now listening to this channel.");
            }
            catch (ChannelAlreadyListeningException e)
            {
                Communicator.sendTempMessage(message.getChannel(), "Already listening to this channel.");
            }
        }
    }
}
