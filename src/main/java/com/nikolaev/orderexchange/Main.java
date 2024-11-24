package com.nikolaev.orderexchange;

import com.nikolaev.orderexchange.dataSource.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        try (Connection connection = databaseConnection.getConnection()) {
        System.out.println(connection.getTransactionIsolation());
        }
    }
}
