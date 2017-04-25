package ttain.amadeus.discord.modules.general.commands;

import ttain.amadeus.discord.helpers.Communicator;
import sx.blah.discord.handle.obj.IChannel;
import java.text.NumberFormat;
import java.util.Random;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.general.commands
 * Filename: Roll.java
 */

public class Roll
{
    public Roll(IChannel channel, String author, Random generator, String sides)
    {
        generator.setSeed(System.currentTimeMillis());
        if (sides == null)
        {
            Communicator.sendMessage(channel, author + ", you rolled a 6-sided dice and got the number **" + (generator.nextInt(6) + 1) + "!**");
        }
        else
        {
            try
            {
                if (Integer.parseInt(sides) < 1)  // Note: parseInt will also break if it exceeds max int. =)
                {
                    throw new NumberFormatException();
                }
                Communicator.sendMessage(channel, author + ", you rolled a " + NumberFormat.getInstance().format(Integer.parseInt(sides)) + "-sided dice and got the number **" + NumberFormat.getInstance().format((generator.nextInt(Integer.parseInt(sides)) + 1)) + "!**");
            }
            catch (NumberFormatException e)
            {
                Communicator.sendMessage(channel, author + ", your # of sides needs to be an integer between 1 and " + Integer.MAX_VALUE + ".");
            }
        }
    }
}
