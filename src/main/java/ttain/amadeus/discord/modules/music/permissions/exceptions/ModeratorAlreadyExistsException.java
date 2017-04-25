package ttain.amadeus.discord.modules.music.permissions.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.exceptions
 * Filename: ModeratorAlreadyExistsException.java
 */

public class ModeratorAlreadyExistsException extends Exception
{
    public ModeratorAlreadyExistsException()
    {
        super();
    }

    public ModeratorAlreadyExistsException(String message)
    {
        super(message);
    }

    public ModeratorAlreadyExistsException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ModeratorAlreadyExistsException(Throwable cause)
    {
        super(cause);
    }
}