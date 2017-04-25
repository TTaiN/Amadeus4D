package ttain.amadeus.discord.modules.music.permissions.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJAlreadyExistsException;
import ttain.amadeus.discord.modules.music.permissions.exceptions.ModeratorNotFoundException;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.commands
 * Filename: AddDJ.java
 */

public class AddDJ
{
    public AddDJ(IMessage message, MusicPermissionManager permissions, String user) throws ModeratorNotFoundException, SQLException
    {
        if (!permissions.isModerator(message.getGuild().getID(), message.getAuthor().getID()))
        {
            if (!permissions.isDeveloper(message.getAuthor().getID()))
            {
                throw new ModeratorNotFoundException();
            }
        }

        if (user != null && !user.isEmpty())
        {
            try
            {
                Long.parseLong(user);
                permissions.addDJ(message.getGuild().getID(), user);
                Communicator.sendTempMessage(message.getChannel(), "DJ added.");
            }
            catch (NumberFormatException e)
            {
                Communicator.sendTempMessage(message.getChannel(), "Wrong format. Use @ mention.");
            }
            catch (DJAlreadyExistsException e)
            {
                Communicator.sendTempMessage(message.getChannel(), "That user is already a DJ.");
            }
        }
        else
        {
            Communicator.sendTempMessage(message.getChannel(), "You must provide a DJ to add. Use @ mention.");
        }
    }
}
