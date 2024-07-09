package ru.webdev;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import ru.webdev.util.DatabaseManager;

/**
 * Класс StatementExample демонстрирует базовые операции подключения к базе данных используя JDBC.
 * <p>
 * Этот класс содержит метод вывода информации о количестве таблиц базы данных. Он использует утилитный класс
 * DatabaseManager для управления соединением с базой данных.
 * <p>
 * Основные функции:<br>
 * - Получение данных из базы, помещение результата в resultSet.<br>
 * - Вывод resultSet в консоль
 *
 * @author webdev
 * @version 1.0
 * @since 2024-07-09
 */
public class StatementExample {
    
    /**
     * Метод main - точка входа в приложение. Инициализирует базу данных и запускает приложение.
     * <p>
     * Инициализация базы данных и получение подключения осуществляется методами DatabaseManager.initializeDatabase() и DatabaseManager.getConnection().
     * <p>
     * Создается Statement, который используется для выполнения SQL-запросов.<br>
     * Результат помещяается в resultSet и выводится в консоль.
     *
     * @param args массив аргументов командной строки (не используется в данном приложении).
     * @throws Exception если произошла ошибка при инициализации базы данных.
     */
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