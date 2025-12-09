package com.boundiou.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDatabase {
    public static void main(String[] args) {
        try (Connection conn = DatabaseManager.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Connexion MySQL OK, bravo Choupinou !");
            } else {
                System.out.println("❌ Connexion MySQL NULL, y a un souci.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
