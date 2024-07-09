package ru.webdev;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * Класс LiquibaseExample демонстрирует использование Liquibase для управления схемой базы данных.
 * <p>
 * Этот класс выполняет следующие функции:
 * <ul>
 *   <li>Загрузка параметров подключения к базе данных из файла application.properties</li>
 *   <li>Установление соединения с базой данных</li>
 *   <li>Инициализация и выполнение Liquibase для обновления схемы базы данных</li>
 * </ul>
 * <p>
 * Класс использует Liquibase для применения изменений схемы, определенных в файле changelog.xml.
 *
 * @author webdev
 * @version 1.0
 * @since 2024-07-09
 */
public class LiquibaseExample {
    
    private static final Properties props = new Properties();
    // Статический блок для загрузки свойств из файла
    static {
        try {
            InputStream input = StatementExample.class.getClassLoader().getResourceAsStream("application.properties");
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
            }
            props.load(input);
        } catch (IOException e) {
            System.out.println("Error loading properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static final String URL = props.getProperty("jdbc.url");
    private static final String USER = props.getProperty("jdbc.user");
    private static final String PASSWORD = props.getProperty("jdbc.password");
    
    /**
     * Главный метод, демонстрирующий применение Liquibase для обновления схемы базы данных.
     * <p>
     * Этот метод выполняет следующие шаги:
     * <ol>
     *   <li>Устанавливает соединение с базой данных, используя параметры из файла свойств.</li>
     *   <li>Инициализирует объект Database для работы с Liquibase.</li>
     *   <li>Создает экземпляр Liquibase, указывая путь к файлу changelog.xml.</li>
     *   <li>Выполняет обновление базы данных с помощью Liquibase.</li>
     *   <li>Выводит сообщение об успешном обновлении.</li>
     * </ol>
     * <p>
     * В случае возникновения SQL-исключения или исключения Liquibase,
     * метод выводит сообщение об ошибке.
     *
     * @param args аргументы командной строки (не используются в данном методе)
     */
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Liquibase was successfully updated");
        } catch (SQLException | LiquibaseException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
}
