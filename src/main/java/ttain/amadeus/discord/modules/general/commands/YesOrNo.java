package ttain.amadeus.discord.modules.general.commands;

import ttain.amadeus.discord.helpers.Communicator;
import sx.blah.discord.handle.obj.IChannel;
import java.io.File;
import java.util.Random;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.general.commands
 * Filename: YesOrNo.java
 */

public class YesOrNo
{
    private String yesImagePath = "./images/YES.jpg";
    private String noImagePath = "./images/NO.jpg";

    public YesOrNo(IChannel channel, String author, Random generator)
    {
        generator.setSeed(System.currentTimeMillis());
        boolean evenNumber = generator.nextInt() % 2 == 0 ? true : false; // Even number is equivalent to "YES!"
        Communicator.sendImageFile(channel, author + ", " +  (evenNumber ? "**YES!!**" : "**NO!!**"), getImageFile(evenNumber));
    }

    private File getImageFile(boolean evenNumber)
    {
        return new File(evenNumber ? yesImagePath : noImagePath);
    }
}
