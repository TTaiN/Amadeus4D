package ttain.amadeus.discord.database;

import ttain.amadeus.discord.database.exceptions.NoActiveAuthTokensException;
import ttain.amadeus.discord.database.exceptions.NoActiveKeysException;
import ttain.amadeus.discord.database.exceptions.TooManyActiveAuthTokensException;
import ttain.amadeus.discord.database.exceptions.TooManyActiveKeysException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.database
 * Filename: DatabaseManager.java
 */

public class DatabaseManager
{
    private HikariDataSource dataSource;

    public DatabaseManager() {
        dataSource = new HikariDataSource(getConfig());
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public String getAuthToken() throws SQLException, NoActiveAuthTokensException, TooManyActiveAuthTokensException
    {
        String result;
        Connection connection = dataSource.getConnection();
        PreparedStatement statement;

        statement = connection.prepareStatement("SELECT * FROM credentials WHERE active = ?");
        statement.setBoolean(1, true);
        ResultSet query = statement.executeQuery();

        checkAuthTokenCount(query);
        query.next();
        result = query.getString("token");
        query.close();
        connection.close();
        return result.trim();
    }

    public String getAPIKey(String purpose) throws SQLException, NoActiveKeysException, TooManyActiveKeysException // Generic, works for any API key.
    {
        String result;
        Connection connection = dataSource.getConnection();
        PreparedStatement statement;

        statement = connection.prepareStatement("SELECT * FROM api WHERE purpose = ? AND active = ?");
        statement.setString(1, purpose);
        statement.setBoolean(2, true);
        ResultSet query = statement.executeQuery();

        checkKeyCount(query, purpose);
        query.next();
        result = query.getString("api_key");
        query.close();
        connection.close();
        return result.trim();
    }

    private void checkAuthTokenCount(ResultSet query) throws SQLException, NoActiveAuthTokensException, TooManyActiveAuthTokensException
    {
        query.last();
        int result = query.getRow();

        if (result > 1)
        {
            throw new TooManyActiveAuthTokensException("There are too many active tokens in the database. Ensure one, and only one, credential is selected active in the database.");
        }
        else if (result <= 0)
        {
            throw new NoActiveAuthTokensException("There are no active tokens in the database. Ensure that at least one token is in the database, and that it is selected active.");
        }

        query.beforeFirst();
    }

    private void checkKeyCount(ResultSet query, String purpose) throws SQLException, TooManyActiveKeysException, NoActiveKeysException {

        query.last();
        int result = query.getRow();

        if (result > 1)
        {
            throw new TooManyActiveKeysException("There are too many active keys for " + purpose + " in the database. Ensure one, and only one, key is selected active in the database.");
        }
        else if (result <= 0)
        {
            throw new NoActiveKeysException("There are no active keys for " + purpose + " in the database. Ensure that at least one key is in the database, and that it is selected active.");
        }

        query.beforeFirst();
    }


    private HikariConfig getConfig()
    {
        return new HikariConfig("./config/database.config");
    }
}
