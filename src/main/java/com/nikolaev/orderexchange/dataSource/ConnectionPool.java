package com.nikolaev.orderexchange.dataSource;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static final List<Connection> AVAILABLE_CONNECTIONS = new ArrayList<>();
    private static final List<Connection> USED_CONNECTIONS = new ArrayList<>();
    private static final String DB_POOL_SIZE_KEY = "db_POOL_SIZE";
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    private ConnectionPool(String URL, String USERNAME, String PASSWORD) throws SQLException {
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;

        for (int i = 0; i < Integer.parseInt(DB_POOL_SIZE_KEY); i++) {
            AVAILABLE_CONNECTIONS.add(createConnections());
        }
    }

    private Connection createConnections() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public synchronized Connection getConnection() throws SQLException {
        if (AVAILABLE_CONNECTIONS.isEmpty()) {
            throw new SQLException("No available connections");
        }

        // Извлекаем реальное соединение из пула
        Connection connection = AVAILABLE_CONNECTIONS.remove(AVAILABLE_CONNECTIONS.size() - 1);
        USED_CONNECTIONS.add(connection);

        // Возвращаем прокси-объект
        return (Connection) Proxy.newProxyInstance(
                connection.getClass().getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if ("close".equals(method.getName())) {
                        // При вызове close возвращаем соединение в пул
                        releaseConnection(connection);
                        return null;
                    }
                    return method.invoke(connection, args);
                }
        );
    }

    public synchronized void releaseConnection(Connection connection) {
        USED_CONNECTIONS.remove(connection);
        AVAILABLE_CONNECTIONS.add(connection);
        notifyAll();
    }

    public int getAvailableConnectionsCount() {
        return AVAILABLE_CONNECTIONS.size();
    }

    public static ConnectionPool create(String url, String username, String password) throws SQLException {
        return new ConnectionPool(url, username, password);
    }

    public static void close() {
        try {
            for (Connection connection : AVAILABLE_CONNECTIONS) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
