package com.othelloai.aiapi.view;

import com.othelloai.aiapi.controller.OthelloController;
import com.othelloai.aiapi.view.*;
import javafx.event.Event;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Piece extends StackPane {
    private final String
            DEFAULT = "-fx-background-radius: 3;-fx-background-color: " + Colors.GREEN.getValue() + ";",
            HOVER = "-fx-background-radius: 3; -fx-background-color:" + Colors.DARK_GREEN.getValue();
    private int x;
    private int y;

    private double size;
    private VBox vBox;
    private Round round;
    private OthelloController controller;


    public Piece(int x, int y, double size, OthelloController controller){
        this.controller = controller;
        this.size = size;
        this.x = x;
        this.y = y;
        vBox = new VBox();
        vBox.setStyle(DEFAULT);
        vBox.setMinSize(size, size);
        vBox.setMaxSize(size, size);
        getChildren().add(vBox);
        buttonFunctions();
    }

    private void buttonFunctions(){
        vBox.setOnMouseEntered(e -> vBox.setStyle(HOVER));
        vBox.setOnMouseExited(e -> vBox.setStyle(DEFAULT));
        vBox.setOnMouseClicked(e -> controller.pieceClicked(this));
    }
    private void removeButtonFunctions(){
        vBox.setOnMouseEntered(Event::consume);
        vBox.setOnMouseExited(Event::consume);
        vBox.setOnMouseClicked(Event::consume);
        vBox.setStyle(DEFAULT);
    }

    public int[] getPos(){
        return new int[]{x, y};
    }
    public boolean isColored(){
        return getChildren().contains(round);
    }
    public void clearColor(){
        getChildren().remove(round);
    }
    public void setColor(boolean color){
        clearColor();
        round = new Round(size*0.9, color);
        getChildren().add(round);
        removeButtonFunctions();
    }
    public Round getRound(){
        if(isColored()){
            return round;
        }
        return null;

    }

    @Override
    public String toString() {
        return "Piece{" +
                "DEFAULT='" + DEFAULT + '\'' +
                ", HOVER='" + HOVER + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", vBox=" + vBox +
                ", round=" + round +
                ", controller=" + controller +
                '}';
    }
}
