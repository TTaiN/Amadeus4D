package ttain.amadeus.discord.modules.music.permissions.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.ModeratorNotFoundException;
import ttain.amadeus.discord.modules.music.permissions.exceptions.NotOwnerOrDeveloperException;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.commands
 * Filename: RemoveModerator.java
 */

public class RemoveModerator
{
    public RemoveModerator(IMessage message, MusicPermissionManager permissions, String target) throws SQLException, NotOwnerOrDeveloperException
    {
        if (!message.getAuthor().equals(message.getGuild().getOwner()) && !permissions.isDeveloper(message.getAuthor().getID()))
        {
            throw new NotOwnerOrDeveloperException();
        }
        else
        {
            if (message.getGuild().getOwnerID().equals(target))
            {
                Communicator.sendTempMessage(message.getChannel(), "You cannot remove the owner of the guild from moderators.");
                return;
            }

            try
            {
                permissions.removeModerator(message.getGuild().getID(), target);
                Communicator.sendTempMessage(message.getChannel(), "Moderator removed.");
            }
            catch (ModeratorNotFoundException e)
            {
                Communicator.sendTempMessage(message.getChannel(), "That user is not a moderator.");
            }
        }
    }
}