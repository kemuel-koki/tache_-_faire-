package com.boundiou.utils;

import com.boundiou.dao.TaskDAO;
import com.boundiou.models.Task;

import java.time.LocalDate;

public class TestTaskDAO {
    public static void main(String[] args) {
        try {
            TaskDAO dao = new TaskDAO();

            // ⚠️ Mets un user_id qui existe vraiment dans users
            int userId = 1;

            // Ajouter une tâche
            Task t = new Task(
                    "Réviser Java",
                    "Finir le projet Task Manager",
                    LocalDate.now().plusDays(3),
                    "HIGH",
                    "TODO",
                    userId
            );

            dao.addTask(t);
            System.out.println("✅ Tâche ajoutée !");

            // Afficher les tâches de l'utilisateur
            System.out.println("📋 Tâches du user " + userId + ":");
            for (Task task : dao.getTasksByUser(userId)) {
                System.out.println(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
