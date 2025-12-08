package com.boundiou;

import com.boundiou.models.TaskRepository;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        // Charger les tâches au démarrage
        TaskRepository.loadFromFile();

        StackPane root = new StackPane(new Label("UI à brancher (collab 1/3)"));
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Task Manager");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Sauvegarder les tâches à la fermeture
        TaskRepository.saveToFile();
    }

    public static void main(String[] args) {
        launch();
    }
}
