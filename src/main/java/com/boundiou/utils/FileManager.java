package com.boundiou.utils;

import com.boundiou.models.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParseException;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String FILE_PATH = "src/main/resources/data/tasks.json";

    // Gson configuré pour gérer LocalDate correctement
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(src.toString()))   // Écrit LocalDate en "2025-01-01"
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> {
                        try {
                            return LocalDate.parse(json.getAsString());
                        } catch (Exception e) {
                            throw new JsonParseException("Erreur LocalDate: " + e.getMessage());
                        }
                    })
            .setPrettyPrinting()
            .create();

    // Charger les tâches depuis le fichier JSON (fichier principal)
    public static List<Task> loadTasks() {
        return loadTasksFromPath(FILE_PATH);
    }

    // Sauvegarder les tâches dans le fichier JSON (fichier principal)
    public static void saveTasks(List<Task> tasks) {
        saveTasksToPath(tasks, FILE_PATH);
    }

    // Méthode générique pour CHARGER un fichier JSON
    public static List<Task> loadTasksFromPath(String path) {
        File file = new File(path);

        try {
            if (!file.exists()) {
                System.out.println("⚠ Fichier JSON absent : " + path + " → liste vide.");
                return new ArrayList<>();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                Type listType = new TypeToken<List<Task>>() {}.getType();
                List<Task> tasks = gson.fromJson(reader, listType);
                return (tasks != null) ? tasks : new ArrayList<>();
            }

        } catch (Exception e) {
            System.out.println("❌ Erreur lors du chargement JSON : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Méthode générique pour SAUVEGARDER un fichier JSON
    public static void saveTasksToPath(List<Task> tasks, String path) {
        try {
            File dir = new File(path).getParentFile();
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }

            try (FileWriter writer = new FileWriter(path)) {
                gson.toJson(tasks, writer);
                System.out.println("💾 Tâches sauvegardées dans : " + path);
            }

        } catch (Exception e) {
            System.out.println("❌ Erreur de sauvegarde JSON : " + e.getMessage());
        }
    }

    // ----------- EXPORT / IMPORT -------------

    public static void exportTasks(List<Task> tasks, String exportPath) {
        saveTasksToPath(tasks, exportPath);
    }

    public static List<Task> importTasks(String importPath) {
        return loadTasksFromPath(importPath);
    }
}
