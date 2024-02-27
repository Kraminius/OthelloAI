package com.othelloai.aiapi.controller;

import com.othelloai.aiapi.view.ButtonBar;
import com.othelloai.aiapi.view.Colors;
import com.othelloai.aiapi.view.MainMenu;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController {

    private Stage stage;

    public MenuController(Stage stage){
        Scene scene = new Scene(new MainMenu(this), 600, 600);
        stage.setTitle("Othello Menu");
        stage.setScene(scene);
        this.stage = stage;
        stage.setOnCloseRequest(e->System.exit(0));
        stage.showAndWait();
    }
    public void close(){
        stage.close();
    }

}
