package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Volume.java
 */

public class Volume
{
    public Volume(IMessage message, MusicPermissionManager permissions, GuildProvider provider, String volume) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }

        this.volume(message.getChannel(), provider, volume);
    }

    public void volume(IChannel textChannel, GuildProvider provider, String volume)
    {
        int previous_volume = provider.getPlayer().getVolume();
        int new_volume = Integer.parseInt(volume);

        if (new_volume > 150 || new_volume < 0)
        {
            Communicator.sendTempMessage(textChannel, "That volume is too high. You can't go higher than 150, or lower than 0. The current volume is " + previous_volume + ".");
        }
        else
        {
            provider.getPlayer().setVolume(new_volume);
            Communicator.sendTempMessage(textChannel, "The volume changed from " + previous_volume + " to " + provider.getPlayer().getVolume() + ".");
        }
    }
}
