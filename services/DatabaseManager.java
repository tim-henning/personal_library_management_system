package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:src/database/library.db"; // Path to database

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL); // Connect to SQLite
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn; // Return the database connection
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL, "
                + "author TEXT NOT NULL, "
                + "isbn TEXT UNIQUE NOT NULL"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql); // Execute the SQL command
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }
}
