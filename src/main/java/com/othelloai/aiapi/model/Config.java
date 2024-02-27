package com.othelloai.aiapi.model;

import com.othelloai.aiapi.controller.OthelloController;

import java.util.ArrayList;

public class Config {
    private static GameType gameType;
    private static boolean turn = true;
    private static OthelloController othelloController;
    private static int[] scores;

    public static GameType getGameType(){
        return gameType;
    }
    public static void setGameType(GameType gameType){
        Config.gameType = gameType;
    }

    public static void setTurn(boolean turn){
        Config.turn = turn;
    }
    public static boolean getTurn(){
        return turn;
    }

    public static OthelloController getController(){
        return othelloController;
    }
    public static void setOthelloController(OthelloController controller){
        Config.othelloController = controller;
    }
    public static void setScore(int[] scores){
        Config.scores = scores;
    }
    public static int[] getScores(){
        return scores;
    }

}
