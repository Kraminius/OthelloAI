package com.othelloai.aiapi.model;

import com.othelloai.aiapi.view.Piece;

public class OthelloLogic {

    public static boolean canPlaceAt(Piece[][] pieces, Piece piece, boolean color){
        int x = piece.getPos()[0];
        int y = piece.getPos()[1];
        int possible = 0;
        if(checkNext(x, y, 1, 0, color, pieces, 0)) possible++; //Right
        if(checkNext(x, y, -1, 0, color, pieces, 0)) possible++; //Left
        if(checkNext(x, y, 0, 1, color, pieces, 0)) possible++; //Down
        if(checkNext(x, y, 0, -1, color, pieces, 0)) possible++; //Up
        if(checkNext(x, y, 1, 1, color, pieces, 0)) possible++; //Right & Down
        if(checkNext(x, y, 1, -1, color, pieces, 0)) possible++; //Right & Up
        if(checkNext(x, y, -1, 1, color, pieces, 0)) possible++; //Left & Down
        if(checkNext(x, y, -1, -1, color, pieces, 0)) possible++; //Left & Up
        return possible > 0;
    }
    private static boolean checkNext(int x, int y, int xDirection, int yDirection, boolean color, Piece[][] pieces, int depth){
        return switch (getColor(pieces, x + xDirection, y + yDirection)) {
            case 1 -> { //Reached A White
                if (color) yield depth >= 1; //Looking for White, checking if there are Blacks in between (depth)
                //Continue Looking
                if (checkNext(x + xDirection, y + yDirection, xDirection, yDirection, color, pieces, depth + 1)) { //If there is at least one depth it can be placed here
                    turnPiece(x+xDirection, y+yDirection, pieces); //Turns this piece on the way back if it was placed correctly.
                    yield true;
                }
                else yield false;
            }
            case 0 -> { //Reached A Black
                if (!color) yield depth >= 1; //Looking for Black, checking if there are Whites in between (depth)
                //Contiue Looking
                if (checkNext(x + xDirection, y + yDirection, xDirection, yDirection, color, pieces, depth + 1)) { //If there is at least one depth it can be placed here.
                    turnPiece(x+xDirection, y+yDirection, pieces);
                    yield true;
                }
                else yield false;
            }
            case -1 -> false; //Reached an empty field
            case -2 -> false; //Reached the end of the array
            default -> false;
        };


    }

    private static void turnPiece(int x, int y, Piece[][] pieces){
        boolean color = pieces[x][y].getRound().getColor();
        pieces[x][y].setColor(!color);
    }
    /**
     * Checks to see whether or not a position is a specific color,
     * since color is measured in booleans and we require more information and risk,
     * we use integers instead.
     * @param pieces the board
     * @param x the x position to check
     * @param y they y position to check
     * @return 0 black, 1 white, -1 none, -2 out of bounds.
     */
    public static int getColor(Piece[][] pieces, int x, int y){
        try{
            if(!pieces[x][y].isColored()) return -1;
            if( pieces[x][y].getRound().getColor()) return 1;
            else return 0;
        }catch (IndexOutOfBoundsException e){
            return -2;
        }
    }
    public static int[] getScore(Piece[][] pieces){
        int[] scores = {0, 0};
        for (Piece[] piece : pieces) {
            for (Piece value : piece) {
                if (value.isColored()) {
                    if (value.getRound().getColor()) {
                        scores[1]++;
                    } else {
                        scores[0]++;
                    }
                }
            }
        }
        return scores;
    }
}
