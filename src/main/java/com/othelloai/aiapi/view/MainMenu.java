package com.othelloai.aiapi.view;

import com.othelloai.aiapi.controller.MenuController;
import com.othelloai.aiapi.model.Config;
import com.othelloai.aiapi.model.GameType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainMenu extends VBox {
    private MenuController controller;
    private Popup howToPlayScreen;

    private Process[] processes;
    public MainMenu(MenuController controller){
        this.controller = controller;
        setStyle("-fx-background-color: " + Colors.DARK.getValue() + "; -fx-alignment: center");

        getChildren().add(createButtons());
        howToPlayScreen = createHowToPlayScreen();
    }

    private Popup createHowToPlayScreen(){
        howToPlayScreen = new Popup(600, 800);
        howToPlayScreen.setTitle("How to play");
        howToPlayScreen.setHeading("How to Play");
        howToPlayScreen.setText("Bla bla bla bla bla bla bla bla bla bla \nbla bla bla bla\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nBla");
        howToPlayScreen.setChoices(new String[]{"Return To Menu"});
        return howToPlayScreen;
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
            String rustFilePath = "src/main/java/com/othelloai/aiapi/rusty_othello_ai.exe";
            String argument = "false";
            processes =new Process[1];
            ProcessBuilder process = new ProcessBuilder(rustFilePath, argument);
            new Thread(() -> { try {
                processes[0] = process.start();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }}).start();


            controller.close();
        });
        totalAiButton.setOnMousePressed(e->{
            Config.setGameType(GameType.AI_VS_AI);

            String rustAI = "src/main/java/com/othelloai/aiapi/rusty_othello_ai.exe";
            String argumentFalse = "false";
            String argumentTrue = "true";
            processes =new Process[2];
            ProcessBuilder processFalse = new ProcessBuilder(rustAI, argumentFalse);
            ProcessBuilder processTrue = new ProcessBuilder(rustAI, argumentTrue);


            new Thread(() -> { try {
                processes[0] = processFalse.start();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }}).start();
            new Thread(() -> { try {
                processes[1] = processTrue.start();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }}).start();

            controller.close();
        });
        howToPlayButton.setOnMousePressed(e->{
            howToPlayScreen.showAndAwaitAnswer();
        });


        return vBox;
    }
    public Process[] getProcesses(){
        return processes;
    }

}
