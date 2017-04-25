package ttain.amadeus.discord.database.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/30/2017
 * Package: ttain.amadeus.discord.database.exceptions
 * Filename: NoActiveAuthTokensException.java
 */

public class NoActiveAuthTokensException extends Exception
{
    public NoActiveAuthTokensException()
    {
        super();
    }

    public NoActiveAuthTokensException(String message)
    {
        super(message);
    }

    public NoActiveAuthTokensException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NoActiveAuthTokensException(Throwable cause)
    {
        super(cause);
    }
}

