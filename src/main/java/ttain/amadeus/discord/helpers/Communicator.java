package ttain.amadeus.discord.helpers;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.helpers
 * Filename: Communicator.java
 */

public class Communicator
{
    public static void sendMessage(IChannel channel, String message)
    {
        try
        {
            channel.sendMessage(message);
        }
        catch (RateLimitException | DiscordException | MissingPermissionsException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendImageFile(IChannel channel, String content, File image)
    {
        try
        {
            channel.sendFile(content, image);
        }
        catch (RateLimitException | DiscordException | MissingPermissionsException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendEmbedMessage(IChannel channel, String message, EmbedObject embed)
    {
        try
        {
            channel.sendMessage(message, embed, false);
        }
        catch (RateLimitException | DiscordException | MissingPermissionsException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendTempMessage(IChannel channel, String message)
    {
        try
        {
            IMessage temp = channel.sendMessage(message);
            scheduleDeleteMessage(temp);
        }
        catch (RateLimitException | DiscordException | MissingPermissionsException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendTempMessage(IChannel channel, String message, Integer time)
    {
        try
        {
            IMessage temp = channel.sendMessage(message);
            scheduleDeleteMessage(temp, time);
        }
        catch (RateLimitException | DiscordException | MissingPermissionsException e)
        {
            e.printStackTrace();
        }
    }

    public static void scheduleDeleteMessage(IMessage message)
    {
        new java.util.Timer().schedule(
                new java.util.TimerTask()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            message.delete();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                50000
        );
    }

    public static void scheduleDeleteMessage(IMessage message, Integer time)
    {
        new java.util.Timer().schedule(
                new java.util.TimerTask()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            message.delete();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                time
        );
    }
}
