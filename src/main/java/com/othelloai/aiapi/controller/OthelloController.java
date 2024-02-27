package com.othelloai.aiapi.controller;

import com.othelloai.aiapi.model.OthelloLogic;
import com.othelloai.aiapi.view.*;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class OthelloController {
    private final double SCREEN_WIDTH = 1000, SCREEN_HEIGHT = 600;
    private final double PIECE_SIZE = 50;
    private final int BOARD_WIDTH = 10, BOARD_HEIGHT = 10;

    private BorderPane screen;
    private Board board;
    private ButtonBar buttonBar;
    private PieceKeeper left;
    private PieceKeeper right;

    private Round inHand;
    private StackPane layers;

    public void launch(Stage stage){
        layers = new StackPane();
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, PIECE_SIZE, this);
        buttonBar = new ButtonBar();
        left = new PieceKeeper(false, BOARD_WIDTH*BOARD_HEIGHT/2-2, PIECE_SIZE, this);
        right = new PieceKeeper(true, BOARD_WIDTH*BOARD_HEIGHT/2-2, PIECE_SIZE, this);
        screen = new BorderPane();
        screen.setCenter(board);
        screen.setBottom(buttonBar);
        screen.setLeft(left);
        screen.setRight(right);
        layers.getChildren().add(screen);
        Scene scene = new Scene(layers, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();

        scene.setOnMouseMoved(this::moveNodeInHand);

    }
    private void moveNodeInHand(MouseEvent event){
        if(inHand == null) return;
        inHand.setTranslateX(event.getX()-SCREEN_WIDTH/2);
        inHand.setTranslateY(event.getY()-SCREEN_HEIGHT/2);
    }

    public void pieceClicked(Piece piece){
        if(piece.isColored()) return;
        if(inHand == null) return;
        if(!OthelloLogic.canPlaceAt(board.getPieces(), piece, inHand.getColor())) return; //Must be the last check, as it turns the other ones.
        piece.setColor(inHand.getColor());
        removeHand(true);
        updateScore();
    }
    private void updateScore(){
        int[] scores = OthelloLogic.getScore(board.getPieces());
        buttonBar.setScore(scores);
    }
    public void pieceKeeperClicked(boolean color){
        if(inHand == null){
            inHand = new Round(PIECE_SIZE*0.9, color);
            inHand.setTranslateX(SCREEN_WIDTH*2);
            inHand.setMouseTransparent(true);
            layers.getChildren().add(inHand);
            if(color){
                right.removeOne();
            }
            else{
                left.removeOne();
            }
        }
        else{
            removeHand(false);
        }
    }
    public void removeHand(boolean placed){
        if(!placed){
            if(inHand.getColor()){
                right.addOne();
            }
            else{
                left.addOne();
            }
        }
        layers.getChildren().remove(inHand);
        inHand = null;
    }

}
