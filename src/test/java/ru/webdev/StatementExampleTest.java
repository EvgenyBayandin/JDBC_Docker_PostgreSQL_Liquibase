package ru.webdev;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatementExampleTest {
    
    private static final Properties props = new Properties();
    
    static {
        try {
            InputStream input = StatementExample.class.getClassLoader().getResourceAsStream("application.properties");
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
            }
            props.load(input);
        } catch (IOException e) {
            System.out.println("Error loading properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static final String URL = props.getProperty("jdbc.url");
    private static final String USER = props.getProperty("jdbc.user");
    private static final String PASSWORD = props.getProperty("jdbc.password");
    
    @Test
    public void testDatabaseConnection() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            assertNotNull(connection, "Connection to database should not be null");
        } catch (Exception e) {
            fail("Error connecting to database: " + e.getMessage());
        }
    }
    
    @Test
    public void testQueryExecution() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM information_schema.tables;");
            while (resultSet.next()) {
                int count = resultSet.getInt("count");
                assertTrue(count >= 0, "Count of tables should be non-negative");
            }
        } catch (Exception e) {
            fail("Error executing query: " + e.getMessage());
        }
    }
}