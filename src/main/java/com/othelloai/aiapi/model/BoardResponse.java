package com.othelloai.aiapi.model;

public class BoardResponse {
    private int[][] board;
    private boolean turn;

    public BoardResponse(int[][] board, boolean turn) {
        this.board = board;
        this.turn = turn;
    }

    // Getter
    public int[][] getBoard() {
        return board;
    }

    // Setter
    public void setBoard(int[][] board) {
        this.board = board;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
