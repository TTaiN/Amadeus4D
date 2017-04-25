package ttain.amadeus.discord.modules.music.player;

import ttain.amadeus.discord.helpers.Communicator;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IChannel;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.utlity
 * Filename: AudioLoadHandler.java
 */

public class AudioLoadHandler implements AudioLoadResultHandler
{
    private final IChannel channel;
    private final GuildProvider provider;
    private final String identifier;

    public AudioLoadHandler(IChannel channel, GuildProvider provider, String identifier)
    {
        this.channel = channel;
        this.provider = provider;
        this.identifier = identifier;
    }

    @Override
    public void trackLoaded(AudioTrack track)
    {
        Communicator.sendTempMessage(channel, "Adding to queue: **" + track.getInfo().title + "** by " + track.getInfo().author);
        provider.getScheduler().queue(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist)
    {
        StringBuilder info = new StringBuilder();
        Communicator.sendTempMessage(channel, "Adding to queue " + playlist.getTracks().size() + " songs. " + info.toString() + "(playlist: " + playlist.getName() + ")");
        for (AudioTrack current : playlist.getTracks())
        {
            provider.getScheduler().queue(current);
        }
    }

    @Override
    public void noMatches()
    {
        Communicator.sendTempMessage(channel, "Nothing found for " + identifier);
    }

    @Override
    public void loadFailed(FriendlyException exception)
    {
        Communicator.sendTempMessage(channel, "Could not play song: " + exception.getMessage());
    }
}
