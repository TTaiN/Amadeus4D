package ttain.amadeus.discord.modules.music.permissions.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.permissions.exceptions.ModeratorNotFoundException;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.commands
 * Filename: RemoveDJ.java
 */

public class RemoveDJ
{
    public RemoveDJ(IMessage message, MusicPermissionManager permissions, String user) throws ModeratorNotFoundException, SQLException
    {
        if (!permissions.isModerator(message.getGuild().getID(), message.getAuthor().getID()))
        {
            if (!permissions.isDeveloper(message.getAuthor().getID()))
            {
                throw new ModeratorNotFoundException();
            }
        }

        try
        {
            permissions.removeDJ(message.getGuild().getID(), user);
            Communicator.sendTempMessage(message.getChannel(), "DJ removed.");
        }
        catch (DJNotFoundException e)
        {
            Communicator.sendTempMessage(message.getChannel(), "That user is not a DJ.");
        }

    }
}
