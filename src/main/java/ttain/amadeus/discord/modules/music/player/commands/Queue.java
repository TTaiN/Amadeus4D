package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.MusicManager;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Queue.java
 */

public class Queue
{
    public Queue(IMessage message, MusicPermissionManager permissions, GuildProvider provider, int page) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }
        this.queue(message.getChannel(), provider, page);
    }

    private void queue(IChannel textChannel, GuildProvider provider, int page)
    {
        int trackCount = provider.getScheduler().getQueueSize()+1;
        ArrayList<String> list = provider.getScheduler().getQueueList();
        if (list.isEmpty())
        {
            Communicator.sendTempMessage(textChannel, "Nothing is currently playing.");
        }
        else if (list.size() == 1)
        {
            Communicator.sendTempMessage(textChannel, "The number of tracks currently in the queue is **" + trackCount + "**.\n\n" + list.get(0) + "\n\n-> (Page 1 of 1).");
        }
        else
        {
            if (page < 1)
            {
                page = 1;
            }
            else if (page > list.size())
            {
                page = list.size();
            }
            Communicator.sendTempMessage(textChannel, "The number of tracks currently in the queue is **" + trackCount + "**.\n\n" + list.get(page-1) + "\n\n-> (Page " + (page) + " of " + list.size() + ").\n\n" +
             "``Note: to browse other pages, type in " + MusicManager.key + "queue #.``");
        }

    }
}
