package ru.webdev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Утилитный класс DatabaseManager для управления подключением к базе данных.
 * <p>
 * Этот класс обеспечивает централизованное управление параметрами подключения
 * к базе данных и предоставляет методы для инициализации и получения соединения.
 * <p>
 * Класс использует файл application.properties для хранения параметров подключения.
 *
 * @author webdev
 * @version 1.0
 * @since 2024-07-09
 */
public class DatabaseManager {
    
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    
    /**
     * Инициализирует параметры подключения к базе данных.
     * <p>
     * Этот метод должен быть вызван перед любыми попытками установить соединение.
     * Он загружает параметры подключения из файла application.properties.
     *
     * @throws Exception если файл свойств не найден или параметры заданы некорректно
     */
    public static void initializeDatabase() throws Exception {
        loadProperties();
    }
    
    /**
     * Загружает параметры подключения из файла application.properties.
     * <p>
     * Этот приватный метод читает файл свойств и устанавливает значения URL, USER и PASSWORD.
     *
     * @throws Exception если файл не найден, не удалось его прочитать, или отсутствуют необходимые параметры
     */
    private static void loadProperties() throws Exception {
        Properties props = new Properties();
        try (var inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new Exception("Unable to find application.properties");
            }
            props.load(inputStream);
        }
        
        URL = props.getProperty("jdbc.url");
        USER = props.getProperty("jdbc.user");
        PASSWORD = props.getProperty("jdbc.password");
        
        if (URL == null || USER == null || PASSWORD == null) {
            throw new Exception("Database properties are not set correctly");
        }
    }
    
    /**
     * Устанавливает соединение с базой данных.
     * <p>
     * Этот метод использует ранее загруженные параметры подключения для создания
     * и возврата объекта Connection.
     *
     * @return объект Connection, представляющий соединение с базой данных
     * @throws SQLException если не удалось установить соединение с базой данных
     * @throws SQLException если база данных не была инициализирована (не был вызван метод initializeDatabase())
     */
    public static Connection getConnection() throws SQLException {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new SQLException("Database is not initialized. Call initializeDatabase() first.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

