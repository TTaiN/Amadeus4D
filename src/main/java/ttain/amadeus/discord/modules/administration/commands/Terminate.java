package ttain.amadeus.discord.modules.administration.commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.administration.commands
 * Filename: Terminate.java
 */

public class Terminate
{
    public Terminate(IDiscordClient client) throws DiscordException
    {
        client.logout();
    }
}
