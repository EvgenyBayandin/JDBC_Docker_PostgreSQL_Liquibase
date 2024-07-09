package ru.webdev;

import java.sql.*;
import ru.webdev.util.DatabaseManager;

/**
 * Класс PreparedStatementExample демонстрирует базовые операции с базой данных используя JDBC.
 * <p>
 * Этот класс содержит методы для создания таблицы, вставки данных,
 * получения и вывода записей из базы данных. Он использует утилитный класс
 * DatabaseManager для управления соединением с базой данных.
 * <p>
 * Основные функции:<br>
 * - Создание таблицы students<br>
 * - Вставка записей в таблицу<br>
 * - Получение всех записей из таблицы<br>
 * - Вывод полученных записей в консоль
 *
 * @author webdev
 * @version 1.0
 * @since 2024-07-09
 */
public class PreparedStatementExample {
    
    /**
     * Метод main - точка входа в приложение. Инициализирует базу данных и запускает приложение.
     * <p>
     * Инициализация базы данных и получение подключения осуществляется методами DatabaseManager.initializeDatabase() и DatabaseManager.getConnection().
     * <p>
     * Далее вызываются методы:
     * createTables() - создает таблицу students в базе данных.
     * insertRecords() - добавляет записи в таблицу students.
     * retrieveRecords() - получает записи из таблицы students и возвращает ResultSet.
     * printRecords() - выводит записи из ResultSet в консоль.
     *
     * @param args массив аргументов командной строки (не используется в данном приложении).
     * @throws RuntimeException если произошла ошибка при инициализации базы данных или выполнении SQL-операций.
     */
    public static void main(String[] args) {
        try {
            DatabaseManager.initializeDatabase();
            try (Connection connection = DatabaseManager.getConnection()) {
                createTable(connection);
                insertRecords(connection);
                ResultSet resultSet = retrieveRecords(connection);
                printRecords(resultSet);
            } catch (SQLException e) {
                System.out.println("Error connecting to database: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database", e);
        }
    }
    
    /**
     * Метод printRecords() выводит в консоль все найденные записи из таблицы students, хранящиеся в ResultSet.
     *
     * @param resultSet результат выполнения запроса к базе данных метода retrieveRecords()
     * @throws SQLException ошибка выполнения запроса к базе данных
     */
    private static void printRecords(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.println("ID: " + id + ", name: " + name + ", age: " + age);
        }
    }
    
    /**
     * Метод retrieveRecords() возвращает все записи из таблицы students и помещает результат выполнения запроса к базе данных в ResultSet.
     *
     * @param connection подключение к базе данных, полученное методом getConnection() класса DatabaseManager
     * @return ResultSet - результат выполнения запроса к базе данных, содержащий все записи из таблицы students
     * @throws SQLException при ошибке выполнения SQL-запроса
     */
    private static ResultSet retrieveRecords(Connection connection) throws SQLException {
        String retrieveStudentsSQL = "SELECT * FROM students";
        Statement statement = connection.createStatement();
        return statement.executeQuery(retrieveStudentsSQL);
    }
    
    /**
     * Метод insertRecords() добавляет одну новую запись (Alex, 25 лет) в таблицу students.
     * <p>
     * Для исключения SQL-иньекций в запросе к базе данных, необходимо использовать PreparedStatement.
     *
     * @param connection подключение к базе данных, полученное методом getConnection() класса DatabaseManager
     * @throws SQLException при ошибке выполнения SQL-запроса
     */
    private static void insertRecords(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO students (name, age) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)) {
            preparedStatement.setString(1, "Alex");
            preparedStatement.setInt(2, 25);
            preparedStatement.executeUpdate();
        }
    }
    
    /**
     * Метод createTable() создает таблицу students в базе данных, если таблица не существует
     *
     * @param connection подключение к базе методом getConnection() DatabaseManager
     * @throws SQLException ошибка выполнения запроса к базе данных
     */
    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" + "id SERIAL PRIMARY KEY," + "name VARCHAR(255)," + "age INT)";
        Statement statement = connection.createStatement();
        statement.execute(createTableSQL);
    }
}
