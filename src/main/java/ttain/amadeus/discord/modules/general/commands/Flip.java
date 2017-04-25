package ttain.amadeus.discord.modules.general.commands;

import ttain.amadeus.discord.helpers.Communicator;
import sx.blah.discord.handle.obj.IChannel;
import java.util.Random;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.general.commands
 * Filename: Flip.java
 */

public class Flip
{
    public Flip(IChannel channel, String author, Random generator)
    {
        generator.setSeed(System.currentTimeMillis());
        Communicator.sendMessage(channel, author + ", you flipped a coin and got " + (generator.nextInt() % 2 == 0 ? "**heads" : "**tails") + "!**");
    }
}
