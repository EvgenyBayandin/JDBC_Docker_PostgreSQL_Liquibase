package ru.webdev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    
    public static void initializeDatabase() throws Exception {
        loadProperties();
    }
    
    private static void loadProperties() throws Exception {
        Properties props = new Properties();
        try (var inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new Exception("Unable to find application.properties");
            }
            props.load(inputStream);
        }
        
        URL = props.getProperty("jdbc.url");
        USER = props.getProperty("jdbc.user");
        PASSWORD = props.getProperty("jdbc.password");
        
        if (URL == null || USER == null || PASSWORD == null) {
            throw new Exception("Database properties are not set correctly");
        }
    }
    
    public static Connection getConnection() throws SQLException {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new SQLException("Database is not initialized. Call initializeDatabase() first.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

