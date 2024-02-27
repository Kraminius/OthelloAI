package com.othelloai.aiapi.view;

import com.othelloai.aiapi.view.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ButtonBar extends VBox {
    private HBox borderPane;
    private Label left;
    private Label right;

    public ButtonBar(){
        System.out.println(Colors.DARK_GREEN.getValue());
        setStyle("-fx-background-color: "+ Colors.DARK_GREEN.getValue() +"; -fx-border-color: "+ Colors.BLACK.getValue()+"; -fx-border-width: 0, 5, 0, 0");
        setPrefHeight(50);
        borderPane = new HBox();
        borderPane.setAlignment(Pos.CENTER);
        setFillWidth(true);
        getChildren().add(borderPane);
        left = new Label("2");
        right = new Label("2");
        left.setStyle("-fx-text-fill: " + Colors.WHITE.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
        right.setStyle("-fx-text-fill: " + Colors.BLACK.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
        VBox boxLeft = new VBox(left);
        VBox boxRight = new VBox(right);
        boxLeft.setMinWidth(50);
        boxRight.setMinWidth(50);
        boxLeft.setStyle("-fx-alignment: center; -fx-background-color: " + Colors.DARK.getValue() + ";");
        boxRight.setStyle("-fx-alignment: center; -fx-background-color: " + Colors.DARK.getValue() + ";");
        borderPane.getChildren().add(new Filler(450, 50));
        borderPane.getChildren().add(boxLeft);
        borderPane.getChildren().add(new Filler(50, 50));
        borderPane.getChildren().add(boxRight);
        borderPane.getChildren().add(new Filler(450, 50));
    }
    public void setScore(int[] scores){
        if(scores.length != 2) return;
        left.setText(scores[0] + "");
        right.setText(scores[1] + "");
    }









}
