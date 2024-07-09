package ru.webdev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatementExample {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres"; // имя по умолчанию, если используете собсевенное наименование, замените
    private static final String PASSWORD = "password"; // пароль укзан при запуске контейнера, если используете собственное наименование, замените
    
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement  = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM information_schema.tables;");
            while (resultSet.next())  {
                int count = resultSet.getInt("count");
                System.out.println("There is " + count + " tables in database");
            }
        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
