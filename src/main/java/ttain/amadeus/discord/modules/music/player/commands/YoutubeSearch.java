package ttain.amadeus.discord.modules.music.player.commands;

import ttain.amadeus.discord.helpers.Communicator;
import ttain.amadeus.discord.modules.music.permissions.MusicPermissionManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.DJNotFoundException;
import ttain.amadeus.discord.modules.music.player.GuildProvider;
import ttain.amadeus.discord.modules.music.player.YoutubeSearchListener;
import ttain.amadeus.discord.modules.music.player.YoutubeSearchResult;
import ttain.amadeus.discord.modules.music.player.exceptions.NoYoutubeSearchResultsException;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.net.ssl.HttpsURLConnection;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/24/2017
 * Package: ttain.amadeus.discord.modules.music.player
 * Filename: YoutubeSearch.java
 */

public class YoutubeSearch
{
    private static HashMap<IUser, YoutubeSearchListener> listeners = new HashMap<IUser, YoutubeSearchListener>();
    private JsonParser parser;
    private String api_key;
    private String duration = "any"; /* any, long (>20min), medium (4-20min), short (<4min). */
    private int maxResults = 5; /* note: 1 <= maxResult <= 50 */

    public YoutubeSearch(IDiscordClient client, String api_key, IMessage request, MusicPermissionManager permissions, AudioPlayerManager playerManager, GuildProvider provider, String query) throws SQLException, DJNotFoundException
    {
        if (!permissions.isDJ(request.getGuild().getID(), request.getAuthor().getID()))
        {
            throw new DJNotFoundException();
        }

        this.api_key = api_key;
        this.parser = new JsonParser();
        this.search(client, request, permissions, playerManager, provider, query);
    }

    public void search(IDiscordClient client, IMessage request, MusicPermissionManager permissions, AudioPlayerManager playerManager, GuildProvider provider, String query)
    {
        try
        {
            LinkedHashMap<String, YoutubeSearchResult> results = _search(query);
            ArrayList<YoutubeSearchResult> videos = new ArrayList<YoutubeSearchResult>(results.values());

            YoutubeSearchListener current = listeners.get(request.getAuthor());
            if (current != null)
            {
                client.getDispatcher().unregisterListener(current);
                listeners.remove(current);
            }

            YoutubeSearchListener listener = new YoutubeSearchListener(client, request, permissions, playerManager, provider, query, videos);
            listeners.put(request.getAuthor(), listener);
            listener.run();
        }
        catch (NoYoutubeSearchResultsException e)
        {
            Communicator.sendTempMessage(request.getChannel(), request.getAuthor() + ", your search for **\"" + query + "\"** returned no results.");
        }
        catch (IOException e)
        {
            Communicator.sendTempMessage(request.getChannel(), request.getAuthor() +  ", your search for **\"" + query + "\"** returned an error.");
        }
    }

    private String clean(String target)
    {
        return target.substring(1, target.length() - 1); // Removes trailing and leading quotes.
    }

    private String getReadableViews(String views)
    {
        Double parsable = Double.parseDouble(views);
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);
        return formatter.format(parsable);
    }

    private String getReadableDuration(String duration) // Credit: James Deal (Uebios)
    {
        PeriodFormatter formatter = ISOPeriodFormat.standard();
        Period p = formatter.parsePeriod(duration);
        Seconds s = p.toStandardSeconds();

        int timeSeconds = s.getSeconds();
        int hours = timeSeconds / 3600;
        int minutes = (timeSeconds % 3600) / 60;
        int seconds = (timeSeconds % 3600) % 60;

        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    private LinkedHashMap<String, YoutubeSearchResult> _search(String query) throws IOException, NoYoutubeSearchResultsException
    {
        LinkedHashMap<String, YoutubeSearchResult> videos = new LinkedHashMap<String, YoutubeSearchResult>();

        URL url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + URLEncoder.encode(query, "UTF-8") + "&maxResults=" + maxResults + "&videoDuration=" + URLEncoder.encode(duration, "UTF-8") + "&type=video&key=" + URLEncoder.encode(api_key, "UTF-8"));
        HttpsURLConnection request = (HttpsURLConnection) url.openConnection();
        request.connect();

        JsonElement element = parser.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject root = element.getAsJsonObject();
        JsonArray results = (JsonArray) root.get("items");

        for (JsonElement current : results)
        {
            YoutubeSearchResult video = new YoutubeSearchResult();
            JsonObject result = (JsonObject) current;

            /* Get Video ID */
            JsonObject id = (JsonObject) result.get("id");
            String identifier = clean(id.get("videoId").toString());
            video.setIdentifier(identifier);


            /* Get Video Title */
            JsonObject snippet = (JsonObject) result.get("snippet");
            String title = clean(snippet.get("title").toString());
            video.setTitle(title);

            videos.put(identifier, video);
        }
        return _resolve(videos);
    }

    private LinkedHashMap<String, YoutubeSearchResult> _resolve(LinkedHashMap<String, YoutubeSearchResult> videos) throws IOException, NoYoutubeSearchResultsException
    {
        String list = String.join(",", videos.keySet());

        URL url = new URL("https://www.googleapis.com/youtube/v3/videos?id=" + URLEncoder.encode(list, "UTF-8") + "&part=contentDetails,statistics&key=" + URLEncoder.encode(api_key, "UTF-8"));
        HttpsURLConnection request = (HttpsURLConnection) url.openConnection();
        request.connect();

        JsonElement element = parser.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject root = element.getAsJsonObject();
        JsonArray results = (JsonArray) root.get("items");

        for (JsonElement current : results)
        {
            JsonObject result = (JsonObject) current;
            YoutubeSearchResult video = videos.get(result.get("id").toString().replace("\"", ""));

            JsonObject contentDetails = (JsonObject) result.get("contentDetails");
            String duration = contentDetails.get("duration").toString();
            video.setDuration(getReadableDuration(clean(duration)));

            JsonObject statistics = (JsonObject) result.get("statistics");

            try
            {
                String viewCounts = statistics.get("viewCount").toString();
                video.setViews(getReadableViews(clean(viewCounts)));
            }
            catch (NullPointerException e)
            {
                video.setViews("N/A");
            }
        }

        if (videos.isEmpty())
        {
            throw new NoYoutubeSearchResultsException();
        }

        return videos;
    }
}
