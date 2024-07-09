package ru.webdev;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import ru.webdev.util.DatabaseManager;

/**
 * Этот класс предназначен для демонстрационных целей и показывает базовое
 *  * использование CallableStatement в JDBC.
 * <p>
 * Этот класс содержит методы для создания, вызова и получения результатов хранимой процедуры.<br>
 * Он использует утилитный класс DatabaseManager для управления соединением с базой данных.
 * <p>
 * Основные функции:<br>
 * - Создание процедуры для вычисления суммы двух чисел<br>
 * - Вызов созданной процедуры<br>
 * - Получение результата выполнения процедуры<br>
 * - Вывод результата в консоль
 *
 * @author webdev
 * @version 1.0
 * @since 2024-07-09
 */
public class CallableStatementExample {
    
    /**
     * Основной метод, демонстрирующий создание и вызов хранимой процедуры в базе данных.
     * <p>
     * Этот метод выполняет следующие шаги:
     * <ol>
     *   <li>Инициализирует подключение к базе данных через DatabaseManager.</li>
     *   <li>Создает хранимую процедуру calculate_sum для вычисления суммы двух целых чисел.</li>
     *   <li>Вызывает созданную процедуру с тестовыми значениями (10 и 20).</li>
     *   <li>Получает результат выполнения процедуры.</li>
     *   <li>Выводит результат в консоль.</li>
     * </ol>
     * <p>
     * Метод обрабатывает возможные исключения, возникающие при работе с базой данных,
     * и выводит сообщения об ошибках в консоль.
     *
     * @param args аргументы командной строки (не используются в данном методе)
     * @throws RuntimeException если происходит неперехваченное исключение при инициализации базы данных
     */
    public static void main(String[] args) {
        try {
            DatabaseManager.initializeDatabase();
            try (Connection connection = DatabaseManager.getConnection()) {
                String createProcedureSQL = "CREATE OR REPLACE FUNCTION calculate_sum(a integer, b integer) " +
                        "RETURNS integer AS $$ " +
                        "BEGIN " +
                        "RETURN a + b; " +
                        "END; " +
                        "$$ LANGUAGE plpgsql";
                Statement statement  = connection.createStatement();
                statement.executeUpdate(createProcedureSQL);
                System.out.println("Procedure created");
                String functionCall = " { ? = call calculate_sum(?,  ?) }";
                int a = 10;
                int b  =  20;
                CallableStatement callableStatement  = connection.prepareCall(functionCall);
                callableStatement.setInt(2, a);
                callableStatement.setInt(3, b);
                callableStatement.registerOutParameter(1, Types.INTEGER);
                callableStatement.execute();
                int result  =  callableStatement.getInt(1);
                System.out.println("Sum of " + a + " and " + b + " is equal to " + result);
                callableStatement.close();
            } catch (Exception e)  {
                System.out.println("Error database connection: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}