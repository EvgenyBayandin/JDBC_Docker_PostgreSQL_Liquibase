package ru.webdev;

import java.sql.Connection;
import java.sql.Statement;
import ru.webdev.util.DatabaseManager;

/**
 * Класс TransactionExample демонстрирует работу с транзакциями в JDBC.
 * <p>
 * Этот класс показывает, как создать таблицу и вставить данные в рамках одной транзакции.
 * Он также демонстрирует обработку ошибок и откат транзакции в случае возникновения исключения.
 * <p>
 * Основные функции:
 * <ul>
 *   <li>Создание таблицы pupils</li>
 *   <li>Вставка записей в таблицу в рамках транзакции</li>
 *   <li>Демонстрация отката транзакции при возникновении ошибки</li>
 * </ul>
 *
 * @author webdev
 * @version 1.0
 * @since 2024-07-09
 */
public class TransactionExample {
    
    /**
     * Главный метод, демонстрирующий использование транзакций в JDBC.
     * <p>
     * Этот метод выполняет следующие действия:
     * <ol>
     *   <li>Инициализирует подключение к базе данных через DatabaseManager.</li>
     *   <li>Отключает автоматическую фиксацию изменений (autocommit).</li>
     *   <li>Создает таблицу pupils, если она не существует.</li>
     *   <li>Вставляет две записи в таблицу pupils.</li>
     *   <li>Фиксирует транзакцию, если все операции выполнены успешно.</li>
     *   <li>В случае возникновения исключения, выводит сообщение об ошибке.</li>
     * </ol>
     * <p>
     * Метод также включает закомментированную строку, которая при раскомментировании
     * вызовет ошибку уникальности и продемонстрирует откат транзакции.
     *
     * @param args аргументы командной строки (не используются в данном методе)
     * @throws RuntimeException если происходит неперехваченное исключение при инициализации базы данных
     */
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
