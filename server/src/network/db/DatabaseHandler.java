package network.db;

import utility.Program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;


    public DatabaseHandler(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("-- Успешное подключение к базе данных.");
    }

    public Connection getConnection() {
        return connection;
    }
}
