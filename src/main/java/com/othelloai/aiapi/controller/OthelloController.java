package com.othelloai.aiapi.controller;

import com.othelloai.aiapi.model.Config;
import com.othelloai.aiapi.model.OthelloLogic;
import com.othelloai.aiapi.view.*;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class OthelloController {
    private final double SCREEN_WIDTH = 1000, SCREEN_HEIGHT = 600;
    private final double PIECE_SIZE = 50;
    private final int BOARD_WIDTH = 10, BOARD_HEIGHT = 10;
    private ArrayList<int[][]> history; //ToDo// Save this list to save a game.
    private int historyStep = 0;
    private Stage stage;

    private BorderPane screen;
    private Board board;
    private ButtonBar buttonBar;
    private PieceKeeper left;
    private PieceKeeper right;
    private int[] scores;

    private Round inHand;
    private StackPane layers;

    public OthelloController(Stage stage, ArrayList<int[][]> game){
        this.stage = stage;
        Config.setOthelloController(this);
        mainMenu(new Stage());
        initiateBoard(game);

    }
    private void initiateBoard(ArrayList<int[][]> game){
        layers = new StackPane();
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, PIECE_SIZE, this);
        buttonBar = new ButtonBar(this);
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

        if(game != null){
            history = game;
            goForward();
        }
        else {
            history = new ArrayList<>();
            history.add(getBoard());
        }

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
        history.add(getBoard());
        historyStep++;
        buttonBar.showForward(false);
        buttonBar.showBackward(true);
        removeFutureHistory();
        return true;
    }
    private void removeFutureHistory(){
        while(historyStep < history.size()-1){
            history.remove(history.size()-1);
        }
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
    private Piece[][] getPiecesFromIntArr(int[][] array){
        int width = array.length;
        int height = array[0].length;
        Piece[][] pieces = new Piece[width][];
        for(int i = 0; i < width; i++){
            pieces[i] = new Piece[height];
            for(int j = 0; j < height; j++){
                Piece piece = new Piece(i, j, PIECE_SIZE, this);
                pieces[i][j] = piece;
                if(array[i][j] == 1) piece.setColor(true);
                else if(array[i][j] == 0) piece.setColor(false);
            }
        }
        return pieces;
    }

    public void goBack(){
        if(historyStep <= 0) return; //Only run if there are previous spaces.
        Piece[][] previous = getPiecesFromIntArr(history.get(--historyStep));
        board.setPieces(previous);
        updateTurn();
        updateScore();
        buttonBar.showForward(true);
        if(historyStep <= 0) buttonBar.showBackward(false);
    }
    public void goForward(){
        if(historyStep >= history.size()-1) return; //Only run if there are forward spaces.
        Piece[][] next = getPiecesFromIntArr(history.get(++historyStep));
        board.setPieces(next);
        updateTurn();
        updateScore();
        buttonBar.showBackward(true);
        if(historyStep >= history.size()-1) buttonBar.showForward(false); //Remove after
    }
    public void aiForfeit(){
        Popup popup = new Popup(600, 400);
        String winner = "";
        String looser = "";
        if(Config.getTurn()){
            winner = "White";
            looser = "Black";
        }
        else{
            winner = "Black";
            looser = "White";
        }

        popup.setTitle(winner + " Wins!");
        popup.setHeading(winner + " Wins!");
        popup.setText("Congratulations " + winner + " Wins!\n" + looser + " has forfeit.");
        popup.setChoices(new String[]{"Return to Menu", "Study Game Instead"});
        if(popup.showAndAwaitAnswer().equals("Return to Menu")) stage.close();
    }
}
