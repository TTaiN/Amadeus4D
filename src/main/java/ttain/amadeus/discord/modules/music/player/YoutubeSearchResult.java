package ttain.amadeus.discord.modules.music.player;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/24/2017
 * Package: ttain.amadeus.discord.modules.music.player
 * Filename: YoutubeSearchResult.java
 */

public class YoutubeSearchResult
{
    private String identifier;
    private String title;
    private String duration;
    private String views;


    public YoutubeSearchResult()
    {

    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}
