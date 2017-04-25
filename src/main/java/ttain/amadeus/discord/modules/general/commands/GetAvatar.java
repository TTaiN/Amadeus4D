package ttain.amadeus.discord.modules.general.commands;

import ttain.amadeus.discord.helpers.Communicator;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.general.commands
 * Filename: GetAvatar.java
 */

public class GetAvatar
{
    public GetAvatar(IDiscordClient client, IChannel channel, String target)
    {
        Communicator.sendMessage(channel, client.getUserByID(cleanTarget(target)).getAvatarURL());
    }

    private String cleanTarget(String target)
    {
        return target.replaceAll("[^0-9]+", "");
    }

}
