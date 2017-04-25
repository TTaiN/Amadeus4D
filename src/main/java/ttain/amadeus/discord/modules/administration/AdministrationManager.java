package ttain.amadeus.discord.modules.administration;

import ttain.amadeus.discord.database.DatabaseManager;
import ttain.amadeus.discord.modules.administration.commands.*;
import ttain.amadeus.discord.modules.administration.permissions.AdministrationPermissionManager;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IPrivateChannel;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.administration
 * Filename: AdministrationManager.java
 */

public class AdministrationManager
{
    public static final String key = "!";

    private final IDiscordClient client;
    private final AdministrationPermissionManager permissions;

    public AdministrationManager(IDiscordClient client, DatabaseManager databaseManager)
    {
        this.client = client;
        this.permissions = new AdministrationPermissionManager(databaseManager);
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event)
    {
        System.out.println("[Amadeus] Discord connection complete. Modules now loaded.");
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event)
    {
        try
        {
            if (event.getMessage().getContent().startsWith(key) && permissions.isDeveloper(event.getMessage().getAuthor().getID()))
            {
                IPrivateChannel PM = client.getOrCreatePMChannel(event.getMessage().getAuthor());
                if (event.getMessage().getChannel().getID().equals(PM.getID()))
                {
                    String[] arguments = event.getMessage().getContent().substring(1).split(" ");
                    String[] combined_arguments = event.getMessage().getContent().substring(1).split(" ", 2);
                    switch (arguments[0].toLowerCase())
                    { // I know these commands are only one-liners, so classes are overkill. However, in the future there might be some that aren't. I want to keep it consistent.
                        case "setusername":
                            new SetUsername(client, combined_arguments[1]);
                            break;
                        case "setavatar":
                            new SetAvatar(client, arguments[1], combined_arguments[1].split(" ", 2)[1]);
                            break;
                        case "setstatus":
                            new SetStatus(client, combined_arguments[1]);
                            break;
                        case "sendmessage":
                            new SendMessage(client, arguments[1], combined_arguments[1].split(" ", 2)[1]);
                            break;
                        case "leave":
                            new Leave(client, PM, arguments[1]);
                            break;
                        case "q":
                        case "die":
                        case "quit":
                        case "terminate":
                            new Terminate(client);
                            System.exit(0);
                    }
                }
            }
        }
        catch (Exception e) // Command classes offer ability to do more advanced exception handling. May want to implement in the future.
        {
            e.printStackTrace();
        }
    }
}
