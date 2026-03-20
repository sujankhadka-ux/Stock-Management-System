package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/stock_db";
    private static final String USER = "root";
    private static final String PASSWORD = "sujan5067";

    // FIX: purely static — no need to instantiate DBConnection anywhere
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected.");
            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
            return null;
        }
    }
}