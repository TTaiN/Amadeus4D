package ttain.amadeus.discord.modules.music.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.utlity
 * Filename: GuildProvider.java
 */

public class GuildProvider
{
    private final AudioPlayer player;
    private final TrackScheduler scheduler;

    public GuildProvider(AudioPlayerManager manager)
    {
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
    }

    public TrackScheduler getScheduler() { return scheduler; }

    public AudioPlayer getPlayer()
    {
        return player;
    }

    public void destroy()
    {
        player.destroy();
    }

    public AudioProvider getAudioProvider()
    {
        return new AudioProvider(player);
    }
}
