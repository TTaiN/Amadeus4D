package ttain.amadeus.discord.modules.administration.permissions;

import ttain.amadeus.discord.database.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/23/2017
 * Package: ttain.amadeus.discord.modules.administration.permissions
 * Filename: AdministrationPermissionManager.java
 */

public class AdministrationPermissionManager
{
    private final DatabaseManager db;

    public AdministrationPermissionManager(DatabaseManager db) {
        this.db = db;
    }

    public boolean isDeveloper(String user_id) throws SQLException {
        return _checkDeveloper(user_id);
    }

    private boolean _checkDeveloper(String user_id) throws SQLException
    {
        System.out.println("[LOG] Developer command initiated, checking developer...");
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
}
