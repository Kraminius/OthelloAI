package com.othelloai.aiapi.view;

import com.othelloai.aiapi.controller.OthelloController;
import com.othelloai.aiapi.view.*;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class PieceKeeper extends Button {
    private int amount;
    private boolean color;
    private double pieceSize;
    private StackPane pane;
    private OthelloController controller;

    public PieceKeeper(boolean color, int amount, double pieceSize, OthelloController controller){
        this.controller = controller;
        this.pieceSize = pieceSize;
        setStyle("-fx-content-display: top; -fx-border-radius: 0; -fx-background-radius: 0;-fx-background-color:" + Colors.DARKER.getValue());
        this.color = color;
        setAmount(amount);
        setPrefWidth(180);
        setPrefHeight(600);
        setOnAction(e -> clicked());
    }

    public void clicked(){
        controller.pieceKeeperClicked(color);
    }
    public void setAmount(int amount){
        this.amount = amount;
        pane = new StackPane();
        setGraphic(pane);

        for(int i = 0; i< amount; i++){
            addOne();
        }

    }
    public void removeOne(){
        if(pane.getChildren().isEmpty()) return;
        pane.getChildren().remove(pane.getChildren().size()-1);
    }
    public void addOne(){
        Round round = new Round(pieceSize*0.9, color);
        pane.getChildren().add(round);
        round.setTranslateY(pane.getChildren().size()*(420.0 /amount)-420.0/2);
    }

}
