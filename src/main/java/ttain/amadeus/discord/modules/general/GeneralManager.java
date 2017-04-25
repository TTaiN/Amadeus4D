package ttain.amadeus.discord.modules.general;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.general.commands.*;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import java.util.Random;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.general
 * Filename: GeneralManager.java
 */

public class GeneralManager
{
    public static final String key = "$";
    private final IDiscordClient client;
    private final Random generator;

    public GeneralManager(IDiscordClient client)
    {
        this.client = client;
        this.generator = new Random();
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event)
    {
        if (event.getMessage().getContent().startsWith(key))
        {
            IMessage message = event.getMessage();
            String[] arguments = message.getContent().toLowerCase().substring(key.length()).split(" ");
            try
            {
                switch (arguments[0])
                {
                    case "h":
                    case "info":
                    case "help":
                        new Help(message.getChannel());
                        break;
                    case "c":
                    case "commands":
                        new Commands(message.getChannel());
                        break;
                    case "getavatar":
                        new GetAvatar(client, message.getChannel(), arguments[1]);
                        break;
                    case "flip":
                        new Flip(message.getChannel(), message.getAuthor().toString(), generator);
                        break;
                    case "yn":
                    case "yorn":
                    case "yesorno":
                        new YesOrNo(message.getChannel(), message.getAuthor().toString(), generator);
                        break;
                    case "roll":
                        new Roll(message.getChannel(), message.getAuthor().toString(), generator, (arguments.length >= 2 ? arguments[1] : null));
                        break;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Communicator.sendTempMessage(message.getChannel(), "Unknown error. Command terminated.");
            }
        }
    }


}
