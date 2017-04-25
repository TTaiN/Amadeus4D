package ttain.amadeus.discord.modules.music.permissions.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.exceptions
 * Filename: DJAlreadyExistsException.java
 */

public class DJAlreadyExistsException extends Exception
{
    public DJAlreadyExistsException()
    {
        super();
    }

    public DJAlreadyExistsException(String message)
    {
        super(message);
    }

    public DJAlreadyExistsException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DJAlreadyExistsException(Throwable cause)
    {
        super(cause);
    }
}