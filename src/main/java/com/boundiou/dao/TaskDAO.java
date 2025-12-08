package com.boundiou.dao;

import com.boundiou.models.Task;
import com.boundiou.utils.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    // CREATE : ajouter une tâche
    public void addTask(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (title, description, due_date, priority, status, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());

            // Gestion du cas où dueDate est null
            if (task.getDueDate() != null) {
                stmt.setDate(3, Date.valueOf(task.getDueDate()));
            } else {
                stmt.setNull(3, Types.DATE);
            }

            stmt.setString(4, task.getPriority());
            stmt.setString(5, task.getStatus());
            stmt.setInt(6, task.getUserId());

            stmt.executeUpdate();
        }
    }

    // READ : récupérer une tâche par id
    public Task getTaskById(int id) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        Task task = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    task = mapRowToTask(rs);
                }
            }
        }
        return task;
    }

    // READ : récupérer toutes les tâches d'un utilisateur
    public List<Task> getTasksByUser(int userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapRowToTask(rs));
                }
            }
        }
        return tasks;
    }

    // READ ALL : récupérer toutes les tâches (version admin)
    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tasks.add(mapRowToTask(rs));
            }
        }
        return tasks;
    }

    // UPDATE : modifier une tâche
    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, " +
                "priority = ?, status = ?, user_id = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());

            if (task.getDueDate() != null) {
                stmt.setDate(3, Date.valueOf(task.getDueDate()));
            } else {
                stmt.setNull(3, Types.DATE);
            }

            stmt.setString(4, task.getPriority());
            stmt.setString(5, task.getStatus());
            stmt.setInt(6, task.getUserId());
            stmt.setInt(7, task.getId());

            stmt.executeUpdate();
        }
    }

    // DELETE : supprimer une tâche
    public void deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Méthode utilitaire : convertir une ligne SQL → objet Task
    private Task mapRowToTask(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String description = rs.getString("description");

        Date dueDateSql = rs.getDate("due_date");
        LocalDate dueDate = (dueDateSql != null) ? dueDateSql.toLocalDate() : null;

        String priority = rs.getString("priority");
        String status = rs.getString("status");
        int userId = rs.getInt("user_id");

        return new Task(id, title, description, dueDate, priority, status, userId);
    }
}
