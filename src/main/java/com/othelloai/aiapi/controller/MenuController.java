package com.othelloai.aiapi.controller;

import com.othelloai.aiapi.view.MainMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    private Stage stage;
    private MainMenu menu;

    public MenuController(Stage stage){
        menu = new MainMenu(this);
        Scene scene = new Scene(menu, 600, 600);
        stage.setTitle("Othello Menu");
        stage.setScene(scene);
        this.stage = stage;
        stage.setOnCloseRequest(e->System.exit(0));
        stage.showAndWait();
    }
    public Process[] getThreads(){
        return menu.getProcesses();
    }
    public void close(){
        stage.close();
    }

}
