package ttain.amadeus.discord.modules.music.player;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.MusicManager;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.commands.Request;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;


import java.sql.SQLException;
import java.util.ArrayList;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/24/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: YoutubeSearchListener.java
 */

public class YoutubeSearchListener implements Runnable
{
    private final IDiscordClient client;
    private final IMessage request;
    private final MusicPermissionManager permissions;
    private final AudioPlayerManager playerManager;
    private final GuildProvider provider;
    private final String query;
    private final ArrayList<YoutubeSearchResult> videos;
    private final int listenTime = 30*1000;

    public YoutubeSearchListener(IDiscordClient client, IMessage request, MusicPermissionManager permissions, AudioPlayerManager playerManager, GuildProvider provider, String query, ArrayList<YoutubeSearchResult> videos)
    {
        this.client = client;
        this.request = request;
        this.permissions = permissions;
        this.playerManager = playerManager;
        this.provider = provider;
        this.query = query;
        this.videos = videos;
    }

    @Override
    public void run()
    {
        int counter = 1;
        StringBuilder result = new StringBuilder( request.getAuthor() + ", search results for \"" + query + "\":\n\n");
        for (YoutubeSearchResult video : videos)
        {
            result.append("``#" + (counter++) + ": " + video.getTitle() + " [" + video.getDuration() + "] - " + video.getViews() + " views``\n");
        }
        result.append("\n-> To play the song you want, do **" + MusicManager.key + "play #**. Searches only last " + (listenTime/1000) + " seconds.");
        Communicator.sendTempMessage(request.getChannel(), result.toString());
        listen();
        return;
    }

    public boolean isAlive()
    {
        return isAlive();
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event)
    {
        if (event.getMessage().getChannel().getID().equals(request.getChannel().getID()))
        {
            if (event.getMessage().getAuthor().getID().equals(request.getAuthor().getID()) && event.getMessage().getContent().startsWith(MusicManager.key))
            {
                String[] arguments = event.getMessage().getContent().toLowerCase().substring(MusicManager.key.length()).split(" ");
                try
                {
                   switch (arguments[0])
                   {
                       case "start":
                       case "play":
                           if (verify(arguments[1], videos.size()))
                           {
                               new Request(request, permissions, playerManager, provider, videos.get(Integer.parseInt(arguments[1])-1).getIdentifier());
                           }
                           else
                           {
                               Communicator.sendTempMessage(request.getChannel(), "Use valid numbers only.");
                           }
                           break;
                   }
                }
                catch (DJNotFoundException e)
                {
                    Communicator.sendTempMessage(request.getChannel(), request.getAuthor() + ", you aren't a DJ anymore.");
                }
                catch (SQLException e)
                {
                    Communicator.sendTempMessage(request.getChannel(), "Database error. Command terminated.");
                }
            }
        }
    }

    private boolean verify(String index, int size)
    {
        try
        {
            int result = Integer.parseInt(index);
            if (result < 1 || result > size+1)
            {
                return false;
            }
            else return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private void listen()
    {
        try
        {
            client.getDispatcher().registerListener(this);
            Thread.sleep(listenTime);
            client.getDispatcher().unregisterListener(this);
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
