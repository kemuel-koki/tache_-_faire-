package com.boundiou.models;

import com.boundiou.utils.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TaskRepository {

    // Liste observable utilisée par l'interface (TableView, ListView, etc.)
    private static final ObservableList<Task> tasks = FXCollections.observableArrayList();

    // Charger les tâches depuis le fichier au démarrage
    public static void loadFromFile() {
        tasks.clear();
        tasks.addAll(FileManager.loadTasks());
        System.out.println("✅ Tâches chargées en mémoire : " + tasks.size());
    }

    // Sauvegarder les tâches dans le fichier à la fermeture
    public static void saveToFile() {
        FileManager.saveTasks(new ArrayList<>(tasks));
    }

    public static ObservableList<Task> getTasks() {
        return tasks;
    }
}
