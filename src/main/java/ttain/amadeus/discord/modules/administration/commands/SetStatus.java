package ttain.amadeus.discord.modules.administration.commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.administration.commands
 * Filename: SetStatus.java
 */

public class SetStatus
{
    public SetStatus(IDiscordClient client, String status) throws DiscordException, RateLimitException
    {
        client.changeStatus(Status.game(status));
    }
}
