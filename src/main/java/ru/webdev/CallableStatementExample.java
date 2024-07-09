package ru.webdev;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Types;

public class CallableStatementExample {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres"; // имя по умолчанию, если используете собсевенное наименование, замените
    private static final String PASSWORD = "password"; // пароль указан при запуске контейнера, если используете собственное наименование, замените
    
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String createProcedureSQL = "CREATE OR REPLACE FUNCTION calculate_sum(a integer, b integer) " +
                    "RETURNS integer AS $$ " +
                    "BEGIN  " +
                    "   RETURN a + b; " +
                    "   END;  "  +
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
    }
}