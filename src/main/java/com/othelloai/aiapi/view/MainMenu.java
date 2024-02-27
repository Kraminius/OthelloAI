package com.othelloai.aiapi.view;

import com.othelloai.aiapi.controller.MenuController;
import com.othelloai.aiapi.model.Config;
import com.othelloai.aiapi.model.GameType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenu extends VBox {
    private MenuController controller;
    private HowToPlay howToPlayScreen;
    public MainMenu(MenuController controller){
        this.controller = controller;
        setStyle("-fx-background-color: " + Colors.DARK.getValue() + "; -fx-alignment: center");

        getChildren().add(createButtons());
        howToPlayScreen = new HowToPlay();
    }
    private VBox createButtons(){
        VBox vBox = new VBox();
        vBox.setStyle("-fx-alignment: center; -fx-spacing: 20");
        Label playersLabel = new Label("Player VS Player");
        VBox playersButton = new VBox(playersLabel);
        Label aiLabel = new Label("Player VS AI");
        VBox aiButton = new VBox(aiLabel);
        Label totalAiLabel = new Label("AI VS AI");
        VBox totalAiButton = new VBox(totalAiLabel);
        Label howToPlay = new Label("How To Play");
        VBox howToPlayButton = new VBox(howToPlay);
        vBox.getChildren().addAll(playersButton, aiButton, totalAiButton, howToPlayButton);
        for(Node box : vBox.getChildren()){

            box.setStyle("-fx-background-color: " + Colors.DARK.getValue() + ";-fx-border-color: " + Colors.WHITE.getValue() + "; -fx-border-width: 2; -fx-alignment: center");
            (box).setOnMouseEntered(e->{
                box.setStyle("-fx-background-color: " + Colors.DARKER.getValue() + ";-fx-border-color: " + Colors.WHITE + "; -fx-border-width: 2;-fx-alignment: center");
            });
            (box).setOnMouseExited(e->{
                box.setStyle("-fx-background-color: " + Colors.DARK.getValue() + ";-fx-border-color: " + Colors.WHITE.getValue() + "; -fx-border-width: 2;-fx-alignment: center");
            });
            ((VBox)box).setMaxWidth(300);
            ((VBox)box).getChildren().get(0).setStyle("-fx-text-fill: " + Colors.WHITE.getValue() + "; -fx-font-weight: bold; -fx-font-size: 24;-fx-alignment: center");
        }
        playersButton.setOnMousePressed(e->{
            Config.setGameType(GameType.PLAYER_VS_PLAYER);
            controller.close();
        });
        aiButton.setOnMousePressed(e->{
            Config.setGameType(GameType.PLAYER_VS_AI);
            controller.close();
        });
        totalAiButton.setOnMousePressed(e->{
            Config.setGameType(GameType.AI_VS_AI);
            controller.close();
        });
        howToPlayButton.setOnMousePressed(e->{
            howToPlayScreen.show();
        });


        return vBox;
    }

}
