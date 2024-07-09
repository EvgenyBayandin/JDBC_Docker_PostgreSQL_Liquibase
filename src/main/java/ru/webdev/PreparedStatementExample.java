package ru.webdev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class PreparedStatementExample {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres"; // имя по умолчанию, если используете собсевенное наименование, замените
    private static final String PASSWORD = "password"; // пароль укзан при запуске контейнера, если используете собственное наименование, замените
    
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createTable(connection);
            insertRecords(connection);
            ResultSet resultSet = retriveRecords(connection);
            printRecords(resultSet);
        } catch (Exception e) {
            System.out.println("Error connecting to databas e" + e.getMessage());
            e.printStackTrace();
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
