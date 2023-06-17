package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static final String userName = "root";
    private static final String password = "Aeiouy13";
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/test";


    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl, userName, password);
            System.out.println("Connected");
        } catch (SQLException | ClassNotFoundException e) {
        }
        return connection;
    }
}
