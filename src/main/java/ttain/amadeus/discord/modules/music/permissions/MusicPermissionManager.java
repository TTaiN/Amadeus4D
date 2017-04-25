package ttain.amadeus.discord.modules.music.permissions;

import ttain.amadeus.discord.database.DatabaseManager;
import ttain.amadeus.discord.modules.music.permissions.exceptions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.modules.music.permissions
 * Filename: MusicPermissionManager.java
 */

public class MusicPermissionManager {
    private final DatabaseManager db;

    public MusicPermissionManager(DatabaseManager db) {
        this.db = db;
    }

    public boolean isDJ(String guild_id, String user_id) throws SQLException
    {
        return (_checkDJ(guild_id, user_id) || isModerator(guild_id, user_id) || isDeveloper(user_id));
    }

    public boolean isPureDJ(String guild_id, String user_id) throws SQLException {
        return (_checkDJ(guild_id, user_id));
    }

    public boolean isModerator(String guild_id, String user_id) throws SQLException {
        return _checkModerator(guild_id, user_id);
    }

    public boolean isDeveloper(String user_id) throws SQLException {
        return _checkDeveloper(user_id);
    }

    public boolean isListening(String guild_id, String channel_id) throws SQLException
    {
        return (_checkChannel(guild_id, channel_id));
    }

    public String getPrintableDJs(String guild_id) throws SQLException
    {
        ArrayList<String> djs = _getAllDJs(guild_id);
        StringBuilder result = new StringBuilder("The DJs for this guild are: \n\n");
        if (djs.isEmpty())
        {
            result.append("(none)");
        }
        else
        {
            for (String dj : djs)
            {
                result.append("<@" + dj + ">" + "\n");
            }
        }
        return result.toString();
    }

    public String getPrintableModerators(String guild_id) throws SQLException
    {
        ArrayList<String> moderators = _getAllModerators(guild_id);
        StringBuilder result = new StringBuilder("The moderators for this guild are: \n\n");
        if (moderators.isEmpty())
        {
            result.append("(none listening)");
        }
        else
        {
            for (String moderator : moderators)
            {
                result.append("<@" + moderator + ">" + "\n");
            }
        }
        return result.toString();
    }

    public String getPrintableChannels(String guild_id) throws SQLException
    {
        ArrayList<String> channels = _getAllChannels(guild_id);
        StringBuilder result = new StringBuilder("-> Listening to the following channels: \n\n");
        if (channels.isEmpty())
        {
            result.append("(none)");
        }
        else
        {
            for (String channel : channels)
            {
                result.append("<#" + channel + ">" + "\n");
            }
        }
        return result.toString();
    }

    public void addDJ(String guild_id, String user_id) throws SQLException, DJAlreadyExistsException {
        if (isPureDJ(guild_id, user_id)) {
            throw new DJAlreadyExistsException();
        } else {
            _addDJ(guild_id, user_id);
        }
    }

    public void removeDJ(String guild_id, String user_id) throws SQLException, DJNotFoundException {
        if (!isPureDJ(guild_id, user_id)) {
            throw new DJNotFoundException();
        } else {
            _removeDJ(guild_id, user_id);
        }
    }

    public void addModerator(String guild_id, String user_id) throws SQLException, ModeratorAlreadyExistsException {
        if (isModerator(guild_id, user_id)) {
            throw new ModeratorAlreadyExistsException();
        } else {
            _addModerator(guild_id, user_id);
        }
    }

    public void removeModerator(String guild_id, String user_id) throws SQLException, ModeratorNotFoundException {
        if (!isModerator(guild_id, user_id)) {
            throw new ModeratorNotFoundException();
        } else {
            _removeModerator(guild_id, user_id);
        }
    }

    public void addChannel(String guild_id, String channel_id) throws SQLException, ChannelAlreadyListeningException {
        if (_checkChannel(guild_id, channel_id)) {
            throw new ChannelAlreadyListeningException();
        } else {
            _addChannel(guild_id, channel_id);
        }
    }

    public void removeChannel(String guild_id, String channel_id) throws SQLException, ChannelNotFoundException {
        if (!_checkChannel(guild_id, channel_id)) {
            throw new ChannelNotFoundException();
        } else {
            _removeChannel(guild_id, channel_id);
        }
    }


    private int _addDJ(String guild_id, String user_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO djs(guild_id, user_id) VALUES (?,?)");
        statement.setString(1, guild_id);
        statement.setString(2, user_id);

        int result = statement.executeUpdate();

        connection.close();
        return result;
    }

    private int _addModerator(String guild_id, String user_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO moderators(guild_id, user_id) VALUES (?,?)");
        statement.setString(1, guild_id);
        statement.setString(2, user_id);

        int result = statement.executeUpdate();

        connection.close();
        return result;
    }

    private int _addChannel(String guild_id, String channel_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO listening_channels(guild_id, channel_id) VALUES (?,?)");
        statement.setString(1, guild_id);
        statement.setString(2, channel_id);

        int result = statement.executeUpdate();

        connection.close();
        return result;
    }

    private int _removeDJ(String guild_id, String user_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM djs WHERE guild_id = ? AND user_id = ?");
        statement.setString(1, guild_id);
        statement.setString(2, user_id);

        int result = statement.executeUpdate();

        connection.close();
        return result;
    }

    private int _removeModerator(String guild_id, String user_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM moderators WHERE guild_id = ? AND user_id = ?");
        statement.setString(1, guild_id);
        statement.setString(2, user_id);

        int result = statement.executeUpdate();

        connection.close();
        return result;
    }

    private int _removeChannel(String guild_id, String channel_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM listening_channels WHERE guild_id = ? AND channel_id = ?");
        statement.setString(1, guild_id);
        statement.setString(2, channel_id);

        int result = statement.executeUpdate();

        connection.close();
        return result;
    }

    private boolean _checkChannel(String guild_id, String channel_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement;

        statement = connection.prepareStatement("SELECT channel_id FROM listening_channels WHERE guild_id = ? AND channel_id = ?");
        statement.setString(1, guild_id);
        statement.setString(2, channel_id);


        ResultSet query = statement.executeQuery();
        boolean result = query.next();

        query.close();
        connection.close();
        return result;
    }

    private boolean _checkDJ(String guild_id, String user_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement;

        statement = connection.prepareStatement("SELECT user_id FROM djs WHERE guild_id = ? AND user_id = ?");
        statement.setString(1, guild_id);
        statement.setString(2, user_id);


        ResultSet query = statement.executeQuery();
        boolean result = query.next();

        query.close();
        connection.close();
        return result;
    }

    private boolean _checkModerator(String guild_id, String user_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement;

        statement = connection.prepareStatement("SELECT user_id FROM moderators WHERE guild_id = ? AND user_id = ?");
        statement.setString(1, guild_id);
        statement.setString(2, user_id);


        ResultSet query = statement.executeQuery();
        boolean result = query.next();

        query.close();
        connection.close();
        return result;
    }

    private boolean _checkDeveloper(String user_id) throws SQLException
    {
        Connection connection = db.getConnection();
        PreparedStatement statement;

        statement = connection.prepareStatement("SELECT * FROM developers WHERE user_id = ?");
        statement.setString(1, user_id);


        ResultSet query = statement.executeQuery();
        boolean result = query.next();

        query.close();
        connection.close();
        return result;
    }

    private ArrayList<String> _getAllChannels(String guild_id) throws SQLException
    {
        ArrayList<String> result = new ArrayList<>();

        Connection connection = db.getConnection();
        PreparedStatement statement;

        statement = connection.prepareStatement("SELECT channel_id FROM listening_channels WHERE guild_id = ?");
        statement.setString(1, guild_id);
        ResultSet query = statement.executeQuery();

        while (query.next())
        {
            result.add(query.getString("channel_id"));
        }

        query.close();
        connection.close();
        return result;
    }

    private ArrayList<String> _getAllDJs(String guild_id) throws SQLException
    {
        ArrayList<String> result = new ArrayList<>();

        Connection connection = db.getConnection();
        PreparedStatement statement;

        statement = connection.prepareStatement("SELECT user_id FROM djs WHERE guild_id = ?");
        statement.setString(1, guild_id);
        ResultSet query = statement.executeQuery();

        while (query.next())
        {
            result.add(query.getString("user_id"));
        }

        query.close();
        connection.close();
        return result;
    }

    private ArrayList<String> _getAllModerators(String guild_id) throws SQLException
    {
        ArrayList<String> result = new ArrayList<>();

        Connection connection = db.getConnection();
        PreparedStatement statement;

        statement = connection.prepareStatement("SELECT user_id FROM moderators WHERE guild_id = ?");
        statement.setString(1, guild_id);
        ResultSet query = statement.executeQuery();

        while (query.next())
        {
            result.add(query.getString("user_id"));
        }

        query.close();
        connection.close();
        return result;
    }
}
