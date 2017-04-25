package ttain.amadeus.discord.database.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 4/24/2017
 * Package: ttain.amadeus.discord.database.exceptions
 * Filename: NoActiveKeysException.java
 */

public class NoActiveKeysException extends Exception
{
    public NoActiveKeysException()
    {
        super();
    }

    public NoActiveKeysException(String message)
    {
        super(message);
    }

    public NoActiveKeysException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NoActiveKeysException(Throwable cause)
    {
        super(cause);
    }
}