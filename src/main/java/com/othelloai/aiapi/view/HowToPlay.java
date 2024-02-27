package com.othelloai.aiapi.view;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HowToPlay extends Stage {
    public HowToPlay(){
        setScene(new Scene(createView(), 600, 600));
    }
    private VBox createView(){
        VBox background = new VBox();

        return background;
    }
}
