package com.othelloai.aiapi.view;

import com.othelloai.aiapi.controller.OthelloController;
import com.othelloai.aiapi.view.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ButtonBar extends VBox {
    private HBox borderPane;
    private Label left;
    private Label right;
    private Label back;
    private Label forward;
    private OthelloController controller;
    private boolean showingForward = false, showingBackward = false;

    public ButtonBar(OthelloController othelloController){
        this.controller = othelloController;
        System.out.println(Colors.DARK_GREEN.getValue());
        setStyle("-fx-background-color: "+ Colors.DARK_GREEN.getValue() +"; -fx-border-color: "+ Colors.BLACK.getValue()+"; -fx-border-width: 0, 5, 0, 0");
        setPrefHeight(50);
        borderPane = new HBox();
        borderPane.setAlignment(Pos.CENTER);
        setFillWidth(true);
        getChildren().add(borderPane);
        left = new Label("2");
        right = new Label("2");
        back = new Label("<--");
        forward = new Label("-->");
        left.setStyle("-fx-text-fill: " + Colors.WHITE.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
        right.setStyle("-fx-text-fill: " + Colors.BLACK.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
        forward.setStyle("-fx-text-fill: " + Colors.DARK.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
        back.setStyle("-fx-text-fill: " + Colors.DARK.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
        VBox boxLeft = new VBox(left);
        VBox boxRight = new VBox(right);
        VBox boxBack = new VBox(back);
        VBox boxForward = new VBox(forward);
        boxLeft.setMinWidth(50);
        boxRight.setMinWidth(50);
        boxBack.setMinWidth(50);
        boxForward.setMinWidth(50);
        boxLeft.setStyle("-fx-alignment: center; -fx-background-color: " + Colors.DARK.getValue() + ";");
        boxRight.setStyle("-fx-alignment: center; -fx-background-color: " + Colors.DARK.getValue() + ";");
        boxForward.setStyle("-fx-alignment: center; -fx-background-color: " + Colors.DARK.getValue() + ";");
        boxBack.setStyle("-fx-alignment: center; -fx-background-color: " + Colors.DARK.getValue() + ";");
        borderPane.getChildren().add(new Filler(350, 50));
        borderPane.getChildren().add(boxBack);
        borderPane.getChildren().add(new Filler(80, 50));
        borderPane.getChildren().add(boxLeft);
        borderPane.getChildren().add(new Filler(50, 50));
        borderPane.getChildren().add(boxRight);
        borderPane.getChildren().add(new Filler(80, 50));
        borderPane.getChildren().add(boxForward);
        borderPane.getChildren().add(new Filler(350, 50));
        boxBack.setOnMousePressed(e->backPressed());
        boxForward.setOnMousePressed(e->forwardPressed());
        boxBack.setOnMouseEntered(e->highLightBack(true));
        boxBack.setOnMouseExited(e->highLightBack(false));
        boxForward.setOnMouseEntered(e->highLightForward(true));
        boxForward.setOnMouseExited(e->highLightForward(false));
    }
    public void setScore(int[] scores){
        if(scores.length != 2) return;
        left.setText(scores[0] + "");
        right.setText(scores[1] + "");
    }

    private void backPressed(){
        if(showingBackward) controller.goBack();
    }
    private void forwardPressed(){
        if(showingForward) controller.goForward();
    }
    private void highLightBack(boolean highlight){
        if(!showingBackward){
            back.setStyle("-fx-text-fill: " + Colors.DARK.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
            return;
        }
        if(highlight) back.setStyle("-fx-text-fill: " + Colors.WHITE.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
        else back.setStyle("-fx-text-fill: " + Colors.BLACK.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
    }
    private void highLightForward(boolean highlight){
        if(!showingForward){
            forward.setStyle("-fx-text-fill: " + Colors.DARK.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
            return;
        }
        if(highlight) forward.setStyle("-fx-text-fill: " + Colors.WHITE.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
        else forward.setStyle("-fx-text-fill: " + Colors.BLACK.getValue() + "; -fx-font-size: 24; -fx-font-weight: bold");
    }
    public void showForward(boolean show){
        showingForward = show;
        highLightForward(false);
    }
    public void showBackward(boolean show){
        showingBackward = show;
        highLightBack(false);
    }






}
