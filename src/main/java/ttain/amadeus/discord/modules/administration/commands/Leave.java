package ttain.amadeus.discord.modules.administration.commands;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import ttain.amadeus.discord.helpers.Communicator;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.administration.commands
 * Filename: Leave.java
 */

public class Leave
{
    public Leave(IDiscordClient client, IChannel private_channel, String guild_id) throws DiscordException, RateLimitException, MissingPermissionsException
    {
        for (IGuild guild : client.getGuilds())
        {
            if (guild.getID().equals(guild_id))
            {
                guild.leaveGuild();
                Communicator.sendMessage(private_channel, "Left the guild \"" + guild.getName() + "\".");
                return;
            }
        }

        Communicator.sendMessage(private_channel, "Could not find the guild with ID \"" + guild_id + "\".");
    }
}
