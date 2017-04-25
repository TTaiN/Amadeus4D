package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.helpers.IO;
import sx.blah.discord.handle.obj.IChannel;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.player.commands
 * Filename: Help.java
 */

public class Help
{
    private String filepath = "./prompts/music_help.txt";

    public Help(IChannel channel) throws IOException
    {
        Communicator.sendMessage(channel, IO.textFileToString(filepath, Charset.defaultCharset()));
    }
}