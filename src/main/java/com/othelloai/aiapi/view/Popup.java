package com.othelloai.aiapi.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup extends Stage {
    private String answer = "";
    private Label heading;
    private Label textLabel;
    private HBox buttons;
    private VBox body;
    public Popup(double width, double height){
        body = new VBox();
        body.setStyle("-fx-background-color: " + Colors.DARK.getValue() + "; -fx-padding: 40; -fx-spacing: 20; ");
        Scene scene = new Scene(body, width, height);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }
    public void setHeading(String headingText){
        body.getChildren().remove(heading);
        heading = new Label(headingText);
        heading.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: " + Colors.WHITE.getValue());
        body.getChildren().add(heading);
    }
    public void setText(String text){
        body.getChildren().remove(textLabel);
        textLabel = new Label(text);
        textLabel.setStyle("-fx-font-size: 14; -fx-text-fill: " + Colors.WHITE.getValue());
        body.getChildren().add(textLabel);
    }
    public void setChoices(String[] choices){
        body.getChildren().remove(buttons);
        buttons = new HBox();
        buttons.setStyle("-fx-spacing: 20; -fx-fill-width: true; -fx-alignment: center");
        for (String choice : choices) {
            Label label = new Label(choice);
            label.setStyle("-fx-text-fill: " + Colors.WHITE.getValue() + "; -fx-font-weight: bold; -fx-font-size: 16");
            VBox button = getButton(choice, label);
            buttons.getChildren().add(button);
        }
        body.getChildren().add(buttons);
    }

    private VBox getButton(String choice, Label label) {
        VBox button = new VBox(label);
        button.setStyle("-fx-background-color: " + Colors.DARK.getValue() + "; -fx-border-color: " + Colors.WHITE.getValue() + ";-fx-border-width: 1; -fx-padding: 5");
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: " + Colors.DARKER.getValue() + "; -fx-border-color: " + Colors.WHITE.getValue() + ";-fx-border-width: 1; -fx-padding: 5");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: " + Colors.DARK.getValue() + "; -fx-border-color: " + Colors.WHITE.getValue() + ";-fx-border-width: 1; -fx-padding: 5");
        });
        button.setOnMousePressed(e -> {
            answer = choice;
            close();
        });
        return button;
    }

    public String showAndAwaitAnswer(){
        showAndWait();
        return answer;
    }
}
