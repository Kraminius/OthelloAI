package com.othelloai.aiapi.repository;

import com.othelloai.aiapi.model.Config;
import org.springframework.stereotype.Service;

@Service
public class BoardRepository {

    public BoardRepository() {
    }

    public void saveBoard() {
    }

    public void getBoard() {
    }

    public void getValidMoves() {
    }

    public int[] getScore() {
        return Config.getScores();
    }
    public void forfeit(){
        Config.getController().aiForfeit();
    }

    public boolean getTurn() {
        return Config.getTurn();
    }


    public boolean setChoice(int x, int y) {
        return Config.getController().aiPlace(x, y);
    }

}
