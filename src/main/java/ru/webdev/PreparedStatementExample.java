package ru.webdev;

import java.sql.*;
import ru.webdev.util.DatabaseManager;

public class PreparedStatementExample {
    
    public static void main(String[] args) {
        try {
            DatabaseManager.initializeDatabase();
            try (Connection connection = DatabaseManager.getConnection()) {
                createTable(connection);
                insertRecords(connection);
                ResultSet resultSet = retriveRecords(connection);
                printRecords(resultSet);
            } catch (Exception e) {
                System.out.println("Error connecting to databas e" + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    
    private static void printRecords(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.println("ID: " + id + ", name: " + name + ", age: " + age);
        }
    }
    
    private static ResultSet retriveRecords(Connection connection) throws SQLException {
        String retriveStudentsSQL = "SELECT * FROM students";
        Statement statement = connection.createStatement();
        statement.execute(retriveStudentsSQL);
        ResultSet resultSet = statement.getResultSet();
        return resultSet;
    }
    
    private static void insertRecords(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO students (name, age) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL);
        preparedStatement.setString(1, "Alex");
        preparedStatement.setInt(2, 25);
        preparedStatement.executeUpdate();
    }
    
    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255)," +
                "age INT)";
        Statement statement = connection.createStatement();
        statement.execute(createTableSQL);
    }
}
