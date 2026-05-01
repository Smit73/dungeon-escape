package ca.sfu.cmpt276.dungeon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ButtonMaker {
    private final Main main;
    private final ButtonStyler sty = new ButtonStyler();

    public ButtonMaker(Main main) {
        this.main = main;
    }


    public Button makeStartButton(Stage stage) {
// start button
        Button startButton = new Button("▶  Start Game");
        sty.styleButton(startButton);
       startButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               main.startGame(stage);
           }
       });
       return startButton;
    }

    public Button makeExitButton(Stage stage) {
    //exit button
        Button exitButton = new Button("✖  Exit");
        sty.styleButton(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        return exitButton;
    }


}