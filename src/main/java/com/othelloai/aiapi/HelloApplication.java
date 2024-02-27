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
        stage.setTitle("Othello AI");
        OthelloController controller = new OthelloController();
        controller.launch(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}