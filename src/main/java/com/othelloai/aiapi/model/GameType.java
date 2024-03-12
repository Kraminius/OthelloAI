package com.othelloai.aiapi.model;

public enum GameType {
    PLAYER_VS_PLAYER("p_vs_p"),
    PLAYER_VS_AI("p_vs_ai"),
    AI_VS_AI("ai_vs_ai");

    private final String type;

    GameType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
