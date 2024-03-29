package com.othelloai.aiapi.controller;

import com.othelloai.aiapi.model.Config;
import com.othelloai.aiapi.model.GameType;
import com.othelloai.aiapi.model.OthelloLogic;
import com.othelloai.aiapi.view.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Arrays;

public class OthelloController {
    private final double SCREEN_WIDTH = 1000, SCREEN_HEIGHT = 600;
    private final double PIECE_SIZE = 50;
    private final int BOARD_WIDTH = 8, BOARD_HEIGHT = 8;
    private ArrayList<int[][]> history; //ToDo// Save this list to save a game.
    private int historyStep = 0;
    private Stage stage;
    private Piece lastPlaced = null;



    private BorderPane screen;
    private Board board;
    private ButtonBar buttonBar;
    private PieceKeeper left;
    private PieceKeeper right;
    private int[] scores;

    private Round inHand;
    private StackPane layers;
    private Process[] processes;

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
                case "Exit to Menu" -> {
                    stage.close();
                    closeThreads();
                }
                case "Exit Program" -> {
                    closeThreads();
                    System.exit(0);
                }
                default -> e.consume();
            }

        });
        Config.setTurn(false);
        left.setTurn(!Config.getTurn());
        right.setTurn(Config.getTurn());
    }
    private void closeThreads(){
        try{
            if(processes != null) {
                for(Process process : processes){
                    process.destroy();
                    process.waitFor();
                }
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public boolean show(Stage stage){
        stage.showAndWait();
        return true;
    }
    private void mainMenu(Stage stage){
        MenuController menu = new MenuController(stage);
        processes = menu.getThreads();
    }
    private void moveNodeInHand(MouseEvent event){
        if(inHand == null) return;
        inHand.setTranslateX(event.getX()-SCREEN_WIDTH/2);
        inHand.setTranslateY(event.getY()-SCREEN_HEIGHT/2);
    }
    private void updateLast(Piece piece){
        if(lastPlaced != null) lastPlaced.mark(false);
        lastPlaced = piece;
        lastPlaced.mark(true);
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
        updateLast(piece);
        removeFutureHistory();
        checkForWin();
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
    public void skip(){
        history.add(getBoard());
        historyStep++;
        buttonBar.showForward(false);
        buttonBar.showBackward(true);
        removeFutureHistory();
        updateTurn();
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

    /**
     * Specific method for skipping turn as AI, as we need to do callback in async response when updating JavaFX, as it can't
     * be done from a non JavaFX thread.
     * @param player
     * @param callback
     */
    public void skipTurnAI(boolean player, Callback callback){
        Platform.runLater(() -> {
            try{
                if(Config.getTurn() == player) return;
                history.add(getBoard());
                historyStep++;
                buttonBar.showForward(false);
                buttonBar.showBackward(true);
                removeFutureHistory();
                updateTurn();
                callback.onSuccess();
            } catch (Exception e){
                callback.onError(e);

            }
        });
    }

    public void setChoice(int x, int y, boolean player,  Callback callback) {
        Platform.runLater(() -> {
            try {
                if(Config.getTurn() == player) return;
                Piece piece = board.getPieces()[x][y];
                if (piece.isColored() || !OthelloLogic.canPlaceAt(board.getPieces(), piece, Config.getTurn())) {
                    callback.onError(new IllegalStateException("Invalid move"));
                    return;
                }

                piece.setColor(Config.getTurn());
                updateScore();
                updateTurn();
                updateLast(piece);
                history.add(getBoard());
                historyStep++;
                buttonBar.showForward(false);
                buttonBar.showBackward(true);
                removeFutureHistory();
                checkForWin();

                callback.onSuccess();
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public int[][] getBoard(){
        Piece[][] pieces = board.getPieces();
        int[][] toReturn = new int[pieces.length][];
        for(int i = 0; i < pieces.length; i++){
            toReturn[i] = new int[pieces[i].length];
            for(int j = 0; j < pieces[i].length; j++){
                if(pieces[i][j].isColored()){
                    if(pieces[i][j].getRound().getColor()) toReturn[i][j]   = 1;
                    else toReturn[i][j] = 0;
                }
                else toReturn[i][j] = -1;
            }
        }

        System.out.println("ge");
        System.out.println(Arrays.deepToString(toReturn));
        System.out.println("he");
        return toReturn;
    }

    public void updateAIProgress(int progress, int end, boolean player) {
        String text = progress + " of " + end;
        System.out.println(text);
        if (Config.getTurn() && (Config.getGameType().equals(GameType.PLAYER_VS_AI))) {right.setAIProgressLabel(text); right.setAIProgressLabel("Waiting...");}
        else if (!Config.getTurn() && !player && (Config.getGameType().equals(GameType.AI_VS_AI))) left.setAIProgressLabel(text);
        else if (Config.getTurn() && player && (Config.getGameType().equals(GameType.AI_VS_AI))) right.setAIProgressLabel(text);

        if(progress >= (0.9*end) && (!player)) {
            left.setAIProgressLabel("Waiting...");
        }
        else if (progress >= (0.9*end) && player){
            right.setAIProgressLabel("Waiting...");
        }
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
    public void Forfeit(){
        Popup popup = new Popup(600, 300);
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
        popup.setChoices(new String[]{"Return to Menu", "Study Game Instead", "Restart Game"});
        switch (popup.showAndAwaitAnswer()) {
            case "Return to Menu" -> {
                stage.close();
                closeThreads();
            }
            case "Restart Game" -> initiateBoard(null);
        }
    }
    public void checkForWin(){
        scores = OthelloLogic.getScore(board.getPieces());
        if(scores[0] + scores[1] != 64) return;
        String winnerText = "";
        if(scores[0] > scores[1]) winnerText = "White Wins!";
        else if(scores[0] < scores[1]) winnerText = "Black Wins!";
        else winnerText = "It's a Tie!";
        Popup popup = new Popup(600, 300);
        popup.setTitle(winnerText);
        popup.setHeading(winnerText);
        popup.setText("Congratulations " + winnerText + " The Game Is Now Over!");
        popup.setChoices(new String[]{"Return to Menu", "Study Game Instead", "Restart Game"});
        switch (popup.showAndAwaitAnswer()) {
            case "Return to Menu" -> {
                stage.close();
                closeThreads();
            }
            case "Restart Game" -> initiateBoard(null);
        }
    }
    public void aiForfeit(){
        initiateBoard(null);
    }

    @Override
    public String toString() {
        return "OthelloController{" +
                "SCREEN_WIDTH=" + SCREEN_WIDTH +
                ", SCREEN_HEIGHT=" + SCREEN_HEIGHT +
                ", PIECE_SIZE=" + PIECE_SIZE +
                ", BOARD_WIDTH=" + BOARD_WIDTH +
                ", BOARD_HEIGHT=" + BOARD_HEIGHT +
                ", history=" + history +
                ", historyStep=" + historyStep +
                ", stage=" + stage +
                ", screen=" + screen +
                ", board=" + board +
                ", buttonBar=" + buttonBar +
                ", left=" + left +
                ", right=" + right +
                ", scores=" + Arrays.toString(scores) +
                ", inHand=" + inHand +
                ", layers=" + layers +
                '}';
    }
}
