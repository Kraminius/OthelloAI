package com.othelloai.aiapi.view;

import com.othelloai.aiapi.controller.OthelloController;
import com.othelloai.aiapi.model.Config;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.net.URL;


public class PieceKeeper extends HBox {
    Label AIProgressLabel = new Label();

    private final String aiStyle = "-fx-content-display: top;-fx-border-width: 2; -fx-border-color: "+Colors.DARKER.getValue()+"; -fx-background-radius: 0;-fx-background-image: url('../../../../../resources/images/circuitBoard.png'); -fx-background-repeat: stretch; -fx-background-size: 1000 700; -fx-background-position: center center;";
    private final String defaultStyle = "-fx-content-display: top;-fx-border-width: 2; -fx-border-color: "+Colors.DARKER.getValue()+"; -fx-background-radius: 0;-fx-background-color:" + Colors.DARKER.getValue();

    private int amount;
    private boolean color;
    private double pieceSize;
    private StackPane pane;
    private OthelloController controller;
    private Button skip;
    private Button forfeit;

    private Label progress;
    private VBox holder;
    private StackPane stackPane;

    public PieceKeeper(boolean color, int amount, double pieceSize, OthelloController controller){
        stackPane = new StackPane();
        HBox background = new HBox();
        getChildren().add(stackPane);
        StackPane buttonLayering = new StackPane();
        this.controller = controller;
        this.pieceSize = pieceSize;
        this.color = color;
        HBox buttons = new HBox();
        holder = new VBox();
        skip = new Button();
        forfeit = new Button();
        Label labelSkip = new Label("Skip");
        Label labelForfeit = new Label("Forfeit");
        progress = new Label("Progress");
        skip.setGraphic(labelSkip);
        forfeit.setGraphic(labelForfeit);
        labelSkip.setStyle("-fx-text-fill: " + Colors.WHITE.getValue() + "; -fx-font-weight: bold; -fx-font-size: 6");
        progress.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 11");
        progress.setScaleX(2);
        progress.setScaleY(2);
        progress.setTranslateY(20);
        progress.setMaxHeight(20);
        progress.setPrefHeight(20);
        progress.setMaxWidth(800);
        progress.setAlignment(Pos.CENTER);
        buttons.setTranslateX(5);
        buttons.setTranslateY(10);
        buttonLayering.getChildren().addAll(progress, buttons);
        labelSkip.setMaxHeight(20);
        labelSkip.setScaleX(2);
        labelSkip.setScaleY(2);
        labelForfeit.setStyle("-fx-text-fill: " + Colors.WHITE.getValue() + "; -fx-font-weight: bold; -fx-font-size: 6");
        labelForfeit.setMaxHeight(20);
        labelForfeit.setScaleX(2);
        labelForfeit.setScaleY(2);
        skip.setPrefWidth(100);
        skip.setMaxHeight(20);
        forfeit.setPrefWidth(100);
        forfeit.setMaxHeight(20);
        buttons.getChildren().addAll(skip, forfeit);
        buttons.setSpacing(5);
        holder.getChildren().add(buttonLayering);
        holder.setFillWidth(true);
        skip.setStyle("-fx-background-color: " + Colors.DARK.getValue() + "; -fx-border-color: " + Colors.DARKER.getValue());
        forfeit.setStyle("-fx-background-color: " + Colors.DARK.getValue() + "; -fx-border-color: " + Colors.DARKER.getValue());
        skip.setOnAction(e->skipPressed());
        forfeit.setOnAction(e->forfeitPressed());
        background.getChildren().add(holder);
        setAmount(amount);
        background.setPrefWidth(220);
        setPrefWidth(220);
        background.setPrefHeight(600);
        setPrefHeight(600);
        setAlignment(Pos.CENTER);
        background.setPrefWidth(220);
        setAlignment(Pos.CENTER);
        setOnMouseClicked(e -> clicked());

        background.setPrefWidth(220);
        setOnMouseClicked(e -> clicked());
        switch (Config.getGameType()){
            case AI_VS_AI -> setDisable(true);
            case PLAYER_VS_AI -> {
                if(color) setDisable(true);
                else buttonLayering.getChildren().remove(progress);
            }
            case PLAYER_VS_PLAYER -> buttonLayering.getChildren().remove(progress);
        }

        setStyle(defaultStyle);

        setAIBackground();
        stackPane.getChildren().add(background);

    }
    private void setAIBackground(){

        switch(Config.getGameType()){
            case PLAYER_VS_PLAYER -> {return;}
            case PLAYER_VS_AI -> {if(!color) return;}
        }

        try{
            Image image = new Image(getClass().getResource("/images/circuitBoard.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(560);
            imageView.setFitWidth(220);
            imageView.setOpacity(0.15);
            stackPane.getChildren().addAll(imageView, AIProgressLabel);
            imageView.setPreserveRatio(true);
            forfeit.setOpacity(0);
            skip.setOpacity(0);
        }catch (Exception e){
            System.out.println("AI image error");

        }
    }
    public void setTurn(boolean turn){
        if(turn){
            setStyle(getStyle()+ ";-fx-border-color: " + Colors.WHITE.getValue());
            skip.setStyle(getStyle()+ ";-fx-border-color: " + Colors.WHITE.getValue());
            skip.setDisable(false);
            forfeit.setStyle(getStyle()+ ";-fx-border-color: " + Colors.WHITE.getValue());
            forfeit.setDisable(false);
        }
        else{
            setStyle(getStyle()+ ";-fx-border-color: " + Colors.DARKER.getValue());
            skip.setStyle(getStyle()+ ";-fx-border-color: " + Colors.DARKER.getValue());
            skip.setDisable(true);
            forfeit.setStyle(getStyle()+ ";-fx-border-color: " + Colors.DARKER.getValue());
            forfeit.setDisable(true);
        }
    }
    private void skipPressed(){
        Popup popup = new Popup(600, 200);
        popup.setTitle("Skip?");
        popup.setHeading("Are you sure you want to skip?");
        popup.setText("You can only skip your turn, by the rules of Othello if you can't place any pieces.");
        popup.setChoices(new String[]{"Skip", "Cancel"});
        if(popup.showAndAwaitAnswer().equals("Skip")) controller.skip();
    }
    private void forfeitPressed(){
        Popup popup = new Popup(600, 200);
        popup.setTitle("Forfeit?");
        popup.setHeading("Are you sure you want to forfeit?");
        popup.setText("If you forfeit, the game ends and you loose.");
        popup.setChoices(new String[]{"Forfeit", "Cancel"});
        if(popup.showAndAwaitAnswer().equals("Forfeit")) controller.Forfeit();
    }

    public void clicked(){
        controller.pieceKeeperClicked(color);
    }
    public void setAmount(int amount){
        this.amount = amount;
        holder.getChildren().remove(pane);
        pane = new StackPane();
        pane.setTranslateY(220);
        holder.getChildren().add(pane);

        for(int i = 0; i< amount; i++){
            addOne();
        }

    }
    public void removeOne(){
        if(pane.getChildren().isEmpty()) return;
        pane.getChildren().remove(pane.getChildren().size()-1);
    }
    public void addOne(){
        Round round = new Round(pieceSize*0.9, color);
        pane.getChildren().add(round);
        round.setTranslateY(pane.getChildren().size()*(420.0 /amount)-420.0/2);
    }

    public void setAIProgressLabel(String text){
        Platform.runLater(() -> progress.setText(text));
    }


}
