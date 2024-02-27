package com.othelloai.aiapi.model;

public class Config {
    private static GameType gameType;

    public static GameType getGameType(){
        return gameType;
    }
    public static void setGameType(GameType gameType){
        Config.gameType = gameType;
    }

}
