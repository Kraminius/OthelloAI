package com.othelloai.aiapi.view;

import com.othelloai.aiapi.controller.OthelloController;
import com.othelloai.aiapi.view.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PieceKeeper extends HBox {
    private int amount;
    private boolean color;
    private double pieceSize;
    private StackPane pane;
    private OthelloController controller;
    private Button skip;
    private VBox holder;

    public PieceKeeper(boolean color, int amount, double pieceSize, OthelloController controller){
        this.controller = controller;
        this.pieceSize = pieceSize;
        setStyle("-fx-content-display: top;-fx-border-width: 2; -fx-border-color: "+Colors.DARKER.getValue()+"; -fx-background-radius: 0;-fx-background-color:" + Colors.DARKER.getValue());
        this.color = color;
        holder = new VBox();
        skip = new Button();
        Label label = new Label("Skip");
        skip.setGraphic(label);
        label.setStyle("-fx-text-fill: " + Colors.WHITE.getValue() + "; -fx-font-weight: bold; -fx-font-size: 8");
        label.setMaxHeight(20);
        label.setScaleX(2);
        label.setScaleY(2);
        skip.setPrefWidth(100);
        skip.setMaxHeight(20);
        holder.getChildren().add(skip);
        holder.setFillWidth(true);
        skip.setTranslateY(10);
        skip.setStyle("-fx-background-color: " + Colors.DARK.getValue() + "; -fx-border-color: " + Colors.DARKER.getValue());
        skip.setOnAction(e->skipPressed());
        getChildren().add(holder);
        setAmount(amount);
        setPrefWidth(180);
        setPrefHeight(600);
        setAlignment(Pos.CENTER);
        setOnMouseClicked(e -> clicked());



    }
    public void setTurn(boolean turn){
        if(turn){
            setStyle(getStyle()+ ";-fx-border-color: " + Colors.WHITE.getValue());
            skip.setStyle(getStyle()+ ";-fx-border-color: " + Colors.WHITE.getValue());
            skip.setDisable(false);
        }
        else{
            setStyle(getStyle()+ ";-fx-border-color: " + Colors.DARKER.getValue());
            skip.setStyle(getStyle()+ ";-fx-border-color: " + Colors.DARKER.getValue());
            skip.setDisable(true);
        }
    }
    private void skipPressed(){
        Popup popup = new Popup(600, 200);
        popup.setTitle("Skip?");
        popup.setHeading("Sure you want to skip?");
        popup.setText("You can only skip your turn, by the rules of Othello if you can't place any pieces.");
        popup.setChoices(new String[]{"Skip", "Cancel"});
        if(popup.showAndAwaitAnswer().equals("Skip")) controller.skip();
    }

    public void clicked(){
        controller.pieceKeeperClicked(color);
    }
    public void setAmount(int amount){
        this.amount = amount;
        holder.getChildren().remove(pane);
        pane = new StackPane();
        pane.setTranslateY(220);
        holder.getChildren().add(pane);

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
