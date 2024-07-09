package ru.webdev;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import ru.webdev.util.DatabaseManager;

public class StatementExample {
    
    public static void main(String[] args) {
        try {
            DatabaseManager.initializeDatabase();
            try (Connection connection = DatabaseManager.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM information_schema.tables")) {
                
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    System.out.println("There are " + count + " tables in the database");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}