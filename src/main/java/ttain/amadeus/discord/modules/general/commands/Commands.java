package ttain.amadeus.discord.modules.general.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.helpers.IO;
import sx.blah.discord.handle.obj.IChannel;
import ttain.amadeus.discord.modules.general.GeneralManager;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.general.commands
 * Filename: Commands.java
 */

public class Commands
{
    private String filepath = "./prompts/general_commands.txt";

    public Commands(IChannel channel) throws IOException
    {
        Communicator.sendMessage(channel, IO.textFileToString(filepath, Charset.defaultCharset()).replace("[general_key]", GeneralManager.key));
    }
}
