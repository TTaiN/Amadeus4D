package ttain.amadeus.discord.modules.music.permissions.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.exceptions
 * Filename: ChannelAlreadyListeningException.java
 */


public class ChannelAlreadyListeningException extends Exception
{
    public ChannelAlreadyListeningException()
    {
        super();
    }

    public ChannelAlreadyListeningException(String message)
    {
        super(message);
    }

    public ChannelAlreadyListeningException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ChannelAlreadyListeningException(Throwable cause)
    {
        super(cause);
    }
}
