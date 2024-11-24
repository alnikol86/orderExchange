package com.nikolaev.orderexchange.dataSource;

import com.nikolaev.orderexchange.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static ConnectionPool connectionPool;

    private static final String URL_KEY = "db_URL";
    private static final String USER_KEY = "db_USER";
    private static final String PASSWORD_KEY = "db_PASSWORD";

    private DatabaseConnection() throws SQLException {
        connectionPool = ConnectionPool.create(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USER_KEY),
                PropertiesUtil.get(PASSWORD_KEY));
    }

    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            System.out.println("Connection to DB Postgresql is active");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connection to DB Postgresql", e);}
        return connection;
    }

    public void releaseConnection (Connection connection) {
        connectionPool.releaseConnection(connection);
    }
}
