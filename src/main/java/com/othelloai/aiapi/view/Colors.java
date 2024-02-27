package com.othelloai.aiapi.view;

public enum Colors {
    GREEN("#2d793c"),
    DARK_GREEN("#265b30"),
    DARK("#404040"),
    DARKER("#2a3535"),
    BLACK("#202020"),
    WHITE("#eeeeee"),
    ;

    private final String value;

    Colors(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
