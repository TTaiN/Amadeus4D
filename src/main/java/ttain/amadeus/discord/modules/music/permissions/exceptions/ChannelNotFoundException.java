package ttain.amadeus.discord.modules.music.permissions.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.exceptions
 * Filename: ChannelNotFoundException.java
 */

public class ChannelNotFoundException extends Exception
{
    public ChannelNotFoundException()
    {
        super();
    }

    public ChannelNotFoundException(String message)
    {
        super(message);
    }

    public ChannelNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ChannelNotFoundException(Throwable cause)
    {
        super(cause);
    }
}
