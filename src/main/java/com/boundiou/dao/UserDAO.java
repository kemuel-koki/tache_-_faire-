package com.boundiou.dao;

import com.boundiou.models.User;
import com.boundiou.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // CREATE : ajouter un utilisateur dans la base
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getRole());

            stmt.executeUpdate();
        }
    }

    // READ : récupérer un utilisateur par son id
    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = mapRowToUser(rs);
                }
            }
        }
        return user;
    }

    // READ : récupérer un utilisateur par son username
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = mapRowToUser(rs);
                }
            }
        }
        return user;
    }

    // READ ALL : récupérer tous les utilisateurs
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        }
        return users;
    }

    // UPDATE : modifier un utilisateur
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password_hash = ?, role = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getId());

            stmt.executeUpdate();
        }
    }

    // DELETE : supprimer un utilisateur
    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Vérifier login (username + mot de passe)
    public boolean checkLogin(String username, String passwordHash) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ? AND password_hash = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, passwordHash);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true si un user existe
            }
        }
    }

    // Méthode utilitaire : transformer une ligne SQL en objet User
    private User mapRowToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getString("role")
        );
    }
}
