package com.othelloai.aiapi.controller;

import com.othelloai.aiapi.model.Config;
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
    private int[] scores;

    private Round inHand;
    private StackPane layers;

    public OthelloController(Stage stage){
        Config.setOthelloController(this);
        mainMenu(new Stage());
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


        scene.setOnMouseMoved(this::moveNodeInHand);
        stage.setOnCloseRequest(e->{
            Popup popup = new Popup(500, 250);
            popup.setTitle("Close?");
            popup.setHeading("Are you sure you want to Exit?");
            popup.setText("The game will not be saved!");
            popup.setChoices(new String[]{"Exit to Menu", "Exit Program", "Cancel"});
            String answer = popup.showAndAwaitAnswer();
            switch (answer){
                case "Exit to Menu" -> stage.close();
                case "Exit Program" -> System.exit(0);
                default -> e.consume();
            }

        });
        updateTurn();

    }

    public boolean show(Stage stage){
        stage.showAndWait();
        return true;
    }
    private void mainMenu(Stage stage){
        MenuController menu = new MenuController(stage);

    }
    private void moveNodeInHand(MouseEvent event){
        if(inHand == null) return;
        inHand.setTranslateX(event.getX()-SCREEN_WIDTH/2);
        inHand.setTranslateY(event.getY()-SCREEN_HEIGHT/2);
    }
    private void updateTurn(){
        Config.setTurn(!Config.getTurn());
        left.setTurn(!Config.getTurn());
        right.setTurn(Config.getTurn());
    }

    public boolean pieceClicked(Piece piece){
        if(piece.isColored()) return false;
        if(inHand == null) return false;
        if(!OthelloLogic.canPlaceAt(board.getPieces(), piece, inHand.getColor())) return false; //Must be the last check, as it turns the other ones.
        piece.setColor(inHand.getColor());
        removeHand(true);
        updateScore();
        updateTurn();
        return true;
    }
    private void updateScore(){
        scores = OthelloLogic.getScore(board.getPieces());
        buttonBar.setScore(scores);
        Config.setScore(scores);
    }
    public void pieceKeeperClicked(boolean color){
        if(Config.getTurn() != color) return;
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
    public boolean aiPlace(int x, int y){
        return pieceClicked(board.getPieces()[x][y]);
    }
    public int[][] getBoard(){
        Piece[][] pieces = board.getPieces();
        int[][] toReturn = new int[pieces.length][];
        for(int i = 0; i < pieces.length; i++){
            toReturn[i] = new int[pieces[i].length];
            for(int j = 0; j < pieces[i].length; j++){
                if(pieces[i][j].isColored()){
                    if(pieces[i][j].getRound().getColor()) toReturn[i][j] = 1;
                    else toReturn[i][j] = 0;
                }
                else toReturn[i][j] = -1;
            }
        }
        return toReturn;
    }

    public void aiForfeit(){
        //TODO// Current turn should forfeit instead of making a move.
    }
}
