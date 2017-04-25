package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Playing.java
 */

public class Playing
{
    public Playing(IMessage message, MusicPermissionManager permissions, GuildProvider provider) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }

        this.playing(message.getChannel(), provider);

    }

    private void playing(IChannel textChannel, GuildProvider provider)
    {
        AudioPlayer player = provider.getPlayer();
        AudioTrack currentTrack = player.getPlayingTrack();

        if (currentTrack != null)
        {
            Communicator.sendTempMessage(textChannel, "Currently playing: **" + currentTrack.getInfo().title + "** by " + currentTrack.getInfo().author + ".\n" + currentTrack.getInfo().uri);
        } else Communicator.sendTempMessage(textChannel, "Nothing is currently playing.");
    }
}