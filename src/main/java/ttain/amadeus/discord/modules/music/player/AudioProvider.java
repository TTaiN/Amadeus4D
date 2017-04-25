package ttain.amadeus.discord.modules.music.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import sx.blah.discord.handle.audio.IAudioProvider;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.utlity
 * Filename: AudioProvider.java
 */

public class AudioProvider implements IAudioProvider
{
    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;
    private int channelNumber = 2;

    public AudioProvider(AudioPlayer audioPlayer)
    {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public boolean isReady()
    {
        if (lastFrame == null)
        {
            lastFrame = audioPlayer.provide();
        }

        return lastFrame != null;
    }

    @Override
    public byte[] provide()
    {
        if (lastFrame == null)
        {
            lastFrame = audioPlayer.provide();
        }

        byte[] data = lastFrame != null ? lastFrame.data : null;
        lastFrame = null;

        return data;
    }

    @Override
    public int getChannels()
    {
        return channelNumber;
    }

    @Override
    public AudioEncodingType getAudioEncodingType()
    {
        return AudioEncodingType.OPUS;
    }
}