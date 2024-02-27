package com.othelloai.aiapi.view;

import com.othelloai.aiapi.controller.OthelloController;
import com.othelloai.aiapi.view.*;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class Board extends GridPane {
    private Piece[][] pieces;
    private OthelloController controller;
    public Board(int width, int height, double pieceSize, OthelloController controller){
        this.controller = controller;
        setHgap(5);
        setVgap(5);
        setStyle("-fx-background-color:"+ Colors.DARK.getValue() +"; -fx-padding: 5");
        setAlignment(Pos.CENTER);
        pieces = new Piece[width][];
        for(int i = 0; i < width; i++){
            pieces[i] = new Piece[height];
            for(int j = 0; j < height; j++){
                Piece piece = new Piece(i, j, pieceSize, controller);
                pieces[i][j] = piece;
                add(piece, i, j);
            }
        }
        prePlacedPieces();
    }
    public void prePlacedPieces(){
        int middleX = pieces.length/2-1;
        int middleY = pieces[0].length/2-1;

        pieces[middleX][middleY].setColor(true);
        pieces[middleX+1][middleY].setColor(false);
        pieces[middleX][middleY+1].setColor(false);
        pieces[middleX+1][middleY+1].setColor(true);

    }
    public Piece[][] getPieces(){
        return pieces;
    }
    public void setPieces(Piece[][] pieces){
        this.pieces = pieces;
        getChildren().clear();
        for(int i = 0; i < pieces.length; i++){
            for(int j = 0; j < pieces[i].length; j++){
                add(pieces[i][j], i, j);
            }
        }
    }
    public Piece getPiece(int x, int y){
        return pieces[x][y];
    }
    public void setPiece(int x, int y, Piece piece){
        pieces[x][y] = piece;
        getChildren().removeIf(node ->
                GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y);
        add(piece, x, y);
    }
}
