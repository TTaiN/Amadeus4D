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
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Shuffle.java
 */

public class Shuffle
{
    public Shuffle(IMessage message, MusicPermissionManager permissions, GuildProvider provider) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }

        this.shuffle(message.getChannel(), provider);

    }

    private void shuffle(IChannel textChannel, GuildProvider provider)
    {
        provider.getScheduler().shuffle();
        Communicator.sendMessage(textChannel, "Tracks shuffled.");
    }
}
