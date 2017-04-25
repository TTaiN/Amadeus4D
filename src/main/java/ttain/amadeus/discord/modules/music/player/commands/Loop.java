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
 * Filename: Loop.java
 */

public class Loop
{
    public Loop(IMessage message, MusicPermissionManager permissions, GuildProvider provider) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }
        this.loop(message.getChannel(), provider);
    }

    public void loop(IChannel textChannel, GuildProvider provider)
    {
        if (provider.getScheduler().isLooping())
        {
            provider.getScheduler().setLoop(false);
            Communicator.sendTempMessage(textChannel, "Looping disabled.");
        }
        else
        {
            provider.getScheduler().setLoop(true);
            Communicator.sendTempMessage(textChannel, "Looping enabled.");
        }
    }
}