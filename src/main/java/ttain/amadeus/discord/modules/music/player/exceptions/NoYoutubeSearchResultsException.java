package ttain.amadeus.discord.modules.music.player.exceptions;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/24/2017
 * Package: ttain.amadeus.discord.modules.music.player.exceptions
 * Filename: NoYoutubeSearchResultssException.java
 */


public class NoYoutubeSearchResultsException extends Exception
{
    public NoYoutubeSearchResultsException()
    {
        super();
    }

    public NoYoutubeSearchResultsException(String message)
    {
        super(message);
    }

    public NoYoutubeSearchResultsException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NoYoutubeSearchResultsException(Throwable cause)
    {
        super(cause);
    }
}
