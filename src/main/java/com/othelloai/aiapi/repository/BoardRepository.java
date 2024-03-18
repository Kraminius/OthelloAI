package com.othelloai.aiapi.repository;

import com.othelloai.aiapi.controller.Callback;
import com.othelloai.aiapi.model.Config;
import javafx.application.Platform;
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
    public void skipTurn(boolean player, Callback callback) {Config.getController().skipTurnAI(player, callback);}
    public String getGameType(){
        return Config.getGameType().toString();
    }
    public boolean getTurn() {
        return Config.getTurn();
    }
    public void setChoice(int x, int y, boolean player, Callback callback) {
        Config.getController().setChoice(x, y, player, callback);
    }

    public void setAIProgressText(int progress, int end, boolean player) {Config.getController().updateAIProgress(progress, end, player);}
}
