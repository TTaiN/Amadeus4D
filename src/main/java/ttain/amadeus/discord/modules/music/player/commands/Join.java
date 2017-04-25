package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Join.java
 */

public class Join
{
    public Join(IMessage message, MusicPermissionManager permissions) throws DJNotFoundException, SQLException
    {
        if (!permissions.isDJ(message.getGuild().getID(), message.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }

        this.joinVoice(message.getChannel(), message.getAuthor().getConnectedVoiceChannels());
    }

    private void joinVoice(IChannel textChannel, List<IVoiceChannel> connectedVoiceChannels)
    {
        try
        {
            if (connectedVoiceChannels.isEmpty())
            {
                Communicator.sendTempMessage(textChannel, "Join a voice channel first.");
            }
            else if (connectedVoiceChannels.get(0).isConnected())
            {
                Communicator.sendTempMessage(textChannel, "I'm already connected to your voice channel.");
            }
            else
            {
                connectedVoiceChannels.get(0).join();
                Communicator.sendTempMessage(textChannel, "Now joining **" + connectedVoiceChannels.get(0).getName() + "**.");
            }
        }
        catch (MissingPermissionsException e)
        {
            Communicator.sendTempMessage(textChannel, "I can't join **" + connectedVoiceChannels.get(0).getName() + "**. (Did you give me permissions?)");
        }
    }
}
