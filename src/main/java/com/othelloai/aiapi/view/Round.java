package com.othelloai.aiapi.view;

import javafx.scene.layout.VBox;

public class Round extends VBox {
    private boolean color;
    public Round(double size, boolean color){
        this.color = color;
        VBox edge = new VBox();
        getChildren().add(edge);
        setMinSize(size, size);
        edge.setMinSize(size, size);
        setMaxSize(size, size);
        edge.setMaxSize(size, size);
        edge.setScaleX(1.05);
        edge.setScaleY(1.05);

        String background = "";
        String border = "";
        if(color){
            background = Colors.BLACK.getValue();
            border = Colors.WHITE.getValue();
        }
        else{
            background = Colors.WHITE.getValue();
            border = Colors.BLACK.getValue();
        }
        setStyle("-fx-background-color:" + background + ";-fx-background-radius: 30");
        edge.setStyle("-fx-border-color:" + border + ";-fx-border-radius: 30; -fx-border-width: 3;");

    }
    public boolean getColor(){
        return color;
    }
}
