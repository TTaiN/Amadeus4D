package ttain.amadeus.discord.database.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 4/24/2017
 * Package: ttain.amadeus.discord.database.exceptions
 * Filename: TooManyActiveKeysException.java
 */

public class TooManyActiveKeysException extends Exception
{
    public TooManyActiveKeysException()
    {
        super();
    }

    public TooManyActiveKeysException(String message)
    {
        super(message);
    }

    public TooManyActiveKeysException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public TooManyActiveKeysException(Throwable cause)
    {
        super(cause);
    }
}