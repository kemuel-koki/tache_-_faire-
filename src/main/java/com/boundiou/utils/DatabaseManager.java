package com.boundiou.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/tache_faire?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";      // adapte ici
    private static final String PASSWORD = "";      // adapte ici

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
