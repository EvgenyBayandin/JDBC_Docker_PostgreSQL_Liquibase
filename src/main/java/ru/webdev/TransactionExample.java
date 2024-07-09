package ru.webdev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TransactionExample {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres"; // имя по умолчанию, если используете собсевенное наименование, замените
    private static final String PASSWORD = "password"; // пароль укзан при запуске контейнера, если используете собственное наименование, замените
    
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            
            String createTableSQL  =  "CREATE TABLE IF NOT EXISTS pupils (id serial primary key, name varchar(255) unique not null)";
            
            Statement statement  =   connection.createStatement();
            statement.executeUpdate(createTableSQL);
            
            String insert1   =   "INSERT INTO pupils (name) VALUES ('Mike')";
            String insert2    =   "INSERT INTO pupils  (name) VALUES  ('Michelle')";
            
            statement.executeUpdate(insert1);
            statement.executeUpdate(insert2);
//            statement.executeUpdate(insert1); // расскомментируйте чтобы увидеть ошибку с попыткой добавления уникальной записи
            
            connection.commit();
            
            System.out.println("Transaction is successfully committed");
        } catch (Exception e) {
            System.out.println("Got SQL Exception in transaction: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception exception) {
                System.out.println("Error closing database connection: " + exception.getMessage());
                exception.printStackTrace();
            }
        }
    }
}
