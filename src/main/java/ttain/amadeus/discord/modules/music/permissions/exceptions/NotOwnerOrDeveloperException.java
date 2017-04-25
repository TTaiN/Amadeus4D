package ttain.amadeus.discord.modules.music.permissions.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.music.permissions.exceptions
 * Filename: NotOwnerOrDeveloperException.java
 */

public class NotOwnerOrDeveloperException extends Exception
{
    public NotOwnerOrDeveloperException()
    {
        super();
    }

    public NotOwnerOrDeveloperException(String message)
    {
        super(message);
    }

    public NotOwnerOrDeveloperException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NotOwnerOrDeveloperException(Throwable cause)
    {
        super(cause);
    }
}
