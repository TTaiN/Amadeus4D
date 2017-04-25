package ttain.amadeus.discord.modules.administration.commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.administration.commands
 * Filename: SetUsername.java
 */

public class SetUsername
{
    public SetUsername(IDiscordClient client, String username) throws DiscordException, RateLimitException
    {
        client.changeUsername(username);
    }
}
