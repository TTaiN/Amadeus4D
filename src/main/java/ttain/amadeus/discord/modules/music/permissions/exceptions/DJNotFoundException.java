package ttain.amadeus.discord.modules.music.permissions.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.exceptions
 * Filename: DJNotFoundException.java
 */

public class DJNotFoundException extends Exception
{
    public DJNotFoundException()
    {
        super();
    }

    public DJNotFoundException(String message)
    {
        super(message);
    }

    public DJNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DJNotFoundException(Throwable cause)
    {
        super(cause);
    }
}
