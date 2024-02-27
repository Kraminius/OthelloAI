package com.othelloai.aiapi.view;

import javafx.scene.layout.Pane;

public class Filler extends Pane {
    public Filler(double width, double height){
        setPrefSize(width, height);
        setStyle("-fx-background-color: " + Colors.DARK.getValue() + ";");
    }
}
