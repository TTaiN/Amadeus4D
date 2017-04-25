package ttain.amadeus.discord.modules.music.permissions.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.ModeratorAlreadyExistsException;
import ttain.amadeus.discord.modules.music.permissions.exceptions.NotOwnerOrDeveloperException;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.commands
 * Filename: AddModerator.java
 */

public class AddModerator
{
    public AddModerator(IMessage message, MusicPermissionManager permissions, String target) throws SQLException, NotOwnerOrDeveloperException
    {
        if (!message.getAuthor().equals(message.getGuild().getOwner()) && !permissions.isDeveloper(message.getAuthor().getID()))
        {
            throw new NotOwnerOrDeveloperException();
        }

        if (target != null && !target.isEmpty())
        {
            try
            {
                Long.parseLong(target);
                permissions.addModerator(message.getGuild().getID(), target);
                Communicator.sendTempMessage(message.getChannel(), "Moderator added.");
            }
            catch (NumberFormatException e)
            {
                Communicator.sendTempMessage(message.getChannel(), "Wrong format. Use @ mention.");
            }
            catch (ModeratorAlreadyExistsException e)
            {
                Communicator.sendTempMessage(message.getChannel(), "That user is already a moderator.");
            }
        }
        else
        {
            Communicator.sendTempMessage(message.getChannel(), "You must provide a moderator to add. Use @ mention.");
        }
    }
}
