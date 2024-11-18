package com.nikolaev.orderexchange.dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;

    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASSWORD = "postgres";
    private final String DRIVER = "org.postgresql.driver";

    private DatabaseConnection(){

    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to DB Postgresql is active");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connection to DB Postgresql", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found" + DRIVER, e);
        }
        return connection;
    }

}
