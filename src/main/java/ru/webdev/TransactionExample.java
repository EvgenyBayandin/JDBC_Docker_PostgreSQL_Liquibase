package ru.webdev;

import java.sql.Connection;
import java.sql.Statement;
import ru.webdev.util.DatabaseManager;

public class TransactionExample {
    
    public static void main(String[] args) {
        Connection connection = null;
        
        try {
            DatabaseManager.initializeDatabase();
            try {
                connection = DatabaseManager.getConnection();
                connection.setAutoCommit(false);
                
                String createTableSQL = "CREATE TABLE IF NOT EXISTS pupils (id serial primary key, name varchar(255) unique not null)";
                
                Statement statement = connection.createStatement();
                statement.executeUpdate(createTableSQL);
                
                String insert1 = "INSERT INTO pupils (name) VALUES ('Mike')";
                String insert2 = "INSERT INTO pupils  (name) VALUES  ('Michelle')";
                
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}
