package ttain.amadeus.discord.modules.music.permissions.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.ChannelNotFoundException;
import ttain.amadeus.discord.modules.music.permissions.exceptions.NotOwnerOrDeveloperException;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.commands
 * Filename: Unlisten.java
 */

public class Unlisten
{
    public Unlisten(IMessage message, MusicPermissionManager permissions) throws SQLException, NotOwnerOrDeveloperException
    {
        if (!message.getAuthor().equals(message.getGuild().getOwner()) && !permissions.isDeveloper(message.getAuthor().getID()))
        {
            throw new NotOwnerOrDeveloperException();
        }
        else
        {
            try
            {
                permissions.removeChannel(message.getGuild().getID(), message.getChannel().getID());
                Communicator.sendTempMessage(message.getChannel(), "No longer listening to this channel.");
            }
            catch (ChannelNotFoundException e)
            {
                Communicator.sendTempMessage(message.getChannel(), "Already not listening to this channel.");
            }
        }
    }
}
