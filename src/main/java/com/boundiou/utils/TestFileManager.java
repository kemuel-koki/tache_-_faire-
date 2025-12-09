package com.boundiou.utils;

import com.boundiou.models.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestFileManager {

    public static void main(String[] args) {

        // 1) On crée une liste de tâches pour tester
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Faire les courses", "Acheter du lait", LocalDate.now(), "HIGH", "TODO", 1));
        tasks.add(new Task("Réviser Java", "Finir le projet TaskFlow", LocalDate.now().plusDays(2), "MEDIUM", "IN_PROGRESS", 1));

        // 2) On sauvegarde dans le fichier
        FileManager.saveTasks(tasks);

        // 3) On recharge depuis le fichier
        List<Task> loaded = FileManager.loadTasks();

        System.out.println("📂 Tâches chargées depuis le fichier :");
        for (Task t : loaded) {
            System.out.println(t);
        }
    }
}
