package main.java.com.bank.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String DB_NAME = "bank";
    private static final String USER = "postgres";
    private static final String PASSWORD = "gugastorm3";

    public static Connection connectDb() {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + DB_NAME, USER, PASSWORD);
            if (conn != null) {
                System.out.println("Database connection established.");
            } else {
                System.out.println("Database connection could not be established.");
            }
        } catch (Exception e) {
            System.out.println("Database connection could not be established.");
            e.printStackTrace();
        }
        return conn;
    }
}