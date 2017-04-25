package ttain.amadeus.discord.modules.administration.commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.RateLimitException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.administration.commands
 * Filename: SetAvatar.java
 */

public class SetAvatar
{
    public SetAvatar(IDiscordClient client, String type, String url) throws DiscordException, RateLimitException
    {
        client.changeAvatar(Image.forUrl(type, url)); // format, url
    }
}
