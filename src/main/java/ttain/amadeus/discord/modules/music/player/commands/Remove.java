package ttain.amadeus.discord.modules.music.player.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import ttain.amadeus.discord.modules.music.player.TrackScheduler;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Remove.java
 */

public class Remove
{
    public Remove(IMessage message, MusicPermissionManager permissions, GuildProvider provider, String number) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID())) {
            throw new DJNotFoundException();
        }

        this.Remove(message.getChannel(), provider, number);
    }

    private void Remove(IChannel textChannel, GuildProvider provider, String number)
    {
        AudioPlayer player = provider.getPlayer();
        TrackScheduler scheduler = provider.getScheduler();

        if (player.getPlayingTrack() == null)
        {
            Communicator.sendTempMessage(textChannel, "Nothing is currently playing.");
        }
        else
        {
            if (number != null && (scheduler.getQueueSize() > 0))
            {
                try
                {
                    int index = Integer.parseInt(number);
                    if (index < 2 || index > scheduler.getQueueSize()+1)
                    {
                        throw new NumberFormatException();
                    }
                    else
                    {
                        scheduler.remove(index);
                        Communicator.sendTempMessage(textChannel, "Removed track #" + index + ".");
                    }
                }
                catch (NumberFormatException e)
                {
                    Communicator.sendTempMessage(textChannel, "Enter a number between 2 and " + (scheduler.getQueueSize()+1) + ".");
                }
            }
            else if (scheduler.getQueueSize() == 0)
            {
                Communicator.sendTempMessage(textChannel, "There are no removable songs in the queue.");
            }
            else
            {
                Communicator.sendTempMessage(textChannel, "Enter a track number to remove.");
            }
        }
    }
}