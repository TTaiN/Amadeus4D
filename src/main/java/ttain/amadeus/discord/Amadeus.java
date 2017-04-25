package ttain.amadeus.discord;

import ttain.amadeus.discord.modules.administration.AdministrationManager;
import ttain.amadeus.discord.modules.general.GeneralManager;
import ttain.amadeus.discord.modules.music.MusicManager;
import ttain.amadeus.discord.database.DatabaseManager;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.ClientBuilder;

// All non-critical imports have been automatically removed for this version.

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/21/2017
 * Package: ttain.amadeus.discord
 * Filename: Amadeus.java
 */

public class Amadeus
{
    public static IDiscordClient client;

    public static void main(String[] args) throws Exception
    {
        try
        {
            DatabaseManager db = new DatabaseManager();
            client = new ClientBuilder().withToken(db.getAuthToken()).login();
            client.getDispatcher().registerListener(new GeneralManager(client));
            client.getDispatcher().registerListener(new AdministrationManager(client, db));
            client.getDispatcher().registerListener(new MusicManager(client, db));
        }
        catch (Exception e)
        {
            System.out.println("[ERROR] There was an error during the startup process. Now terminating.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
