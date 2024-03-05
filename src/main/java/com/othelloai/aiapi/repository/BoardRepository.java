package com.othelloai.aiapi.repository;

import com.othelloai.aiapi.model.Config;
import org.springframework.stereotype.Service;

@Service
public class BoardRepository {

    public BoardRepository() {
    }
    public int[][] getBoard() {
        return Config.getController().getBoard();
    }
    public int[] getScore() {
        return Config.getScores();
    }
    public void forfeit(){
        Config.getController().aiForfeit();
    }
    public String getGameType(){
        return Config.getGameType().toString();
    }
    public boolean getTurn() {
        return Config.getTurn();
    }
    public boolean setChoice(int x, int y) {
        return Config.getController().aiPlace(x, y);
    }

}
