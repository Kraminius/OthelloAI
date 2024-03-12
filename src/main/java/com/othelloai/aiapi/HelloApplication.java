package com.othelloai.aiapi;

import com.othelloai.aiapi.controller.OthelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Stage othelloStage = new Stage(); //Must not be primary stage
        othelloStage.setTitle("Othello AI");
        boolean running = true;
        while (running){
            OthelloController controller = new OthelloController(othelloStage, null); //ToDo// Implement a loader and saver of previous games. feeding an ArrayList<int[][]> here will display a previous game.
            running = controller.show(othelloStage);
        }

    }

    public static void main(String[] args) {
        launchApplication(args);
    }

    public static void launchApplication(String[] args) {
        launch(args);
    }
}