package ttain.amadeus.discord.modules.administration.commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.administration.commands
 * Filename: SendMessage.java
 */

public class SendMessage
{
    public SendMessage(IDiscordClient client, String channel_id, String message) throws DiscordException, RateLimitException, MissingPermissionsException
    {
        client.getChannelByID(channel_id).sendMessage(message);
    }
}
