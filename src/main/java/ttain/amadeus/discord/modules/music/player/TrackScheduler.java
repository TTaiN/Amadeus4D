package ttain.amadeus.discord.modules.music.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.utlity
 * Filename: TrackScheduler.java
 */

public class TrackScheduler extends AudioEventAdapter
{
    private boolean loop;
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    private void nextTrack()
    {
        player.startTrack(queue.poll(), false);
    }

    public TrackScheduler(AudioPlayer player)
    {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.loop = false;
    }

    public boolean isLooping()
    {
        return loop;
    }

    public void setLoop(boolean setting)
    {
        loop = setting;
    }

    public int getQueueSize()
    {
        return queue.size();
    }

    public ArrayList<String> getQueueList()
    {
        ArrayList<String> result = new ArrayList<String>();
        AudioTrack currentTrack = player.getPlayingTrack();
        StringBuilder temp = new StringBuilder();
        int counter = 1;

        if (currentTrack != null)
        {
            temp.append("-> **#" + (counter++) + ": " + currentTrack.getInfo().title + " by " + currentTrack.getInfo().author + "**\n\n");
        }
        for (AudioTrack track : queue)
        {
            temp.append("-> #" + (counter++) + ": " + track.getInfo().title + " by " + track.getInfo().author + "\n");
            if (temp.length() >= 1500)
            {
                result.add(temp.toString());
                temp = new StringBuilder();
            }
        }

        if (temp.length() != 0)
        {
            result.add(temp.toString());
        }
        return result;
    }

    public void queue(AudioTrack track)
    {
        if (!player.startTrack(track, true))
        {
            queue.offer(track);
        }
    }

    public void shuffle()
    {
        List<AudioTrack> tempQueue = new ArrayList<>(new LinkedHashSet<>(queue));
        Collections.shuffle(tempQueue);
        queue.clear();
        queue.addAll(tempQueue);
    }

    public void placeNext(int number)
    {
        List<AudioTrack> tempQueue = new ArrayList<>(new LinkedHashSet<>(queue));
        AudioTrack target = tempQueue.get(number-2);
        tempQueue.remove(target);
        queue.clear();
        queue.add(target);
        queue.addAll(tempQueue);
    }

    public void remove(int number)
    {
        queue.remove(queue.toArray()[number-2]); // More elegant solution, hopefully.
        //List<AudioTrack> tempQueue = new ArrayList<>(new LinkedHashSet<>(queue));
        //AudioTrack target = tempQueue.get(number-2);
        //tempQueue.remove(target);
        //queue.clear();
        //queue.addAll(tempQueue);
    }

    public void skip()
    {
        if (loop)
        {
            queue.offer(player.getPlayingTrack().makeClone());
        }
        this.nextTrack();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
    {
        if (endReason.mayStartNext)
        {
            if (loop)
            {
                queue.offer(track.makeClone());
            }
            nextTrack();
        }
    }
}