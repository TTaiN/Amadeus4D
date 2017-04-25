package ttain.amadeus.discord.database.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/30/2017
 * Package: ttain.amadeus.discord.database.exceptions
 * Filename: TooManyActiveAuthTokensException.java
 */

public class TooManyActiveAuthTokensException extends Exception
{
    public TooManyActiveAuthTokensException()
    {
        super();
    }

    public TooManyActiveAuthTokensException(String message)
    {
        super(message);
    }

    public TooManyActiveAuthTokensException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public TooManyActiveAuthTokensException(Throwable cause)
    {
        super(cause);
    }
}
