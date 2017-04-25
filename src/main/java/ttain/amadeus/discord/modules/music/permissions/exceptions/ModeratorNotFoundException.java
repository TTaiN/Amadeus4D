package ttain.amadeus.discord.modules.music.permissions.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.exceptions
 * Filename: ModeratorNotFoundException.java
 */

public class ModeratorNotFoundException extends Exception
{
    public ModeratorNotFoundException()
    {
        super();
    }

    public ModeratorNotFoundException(String message)
    {
        super(message);
    }

    public ModeratorNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ModeratorNotFoundException(Throwable cause)
    {
        super(cause);
    }
}
