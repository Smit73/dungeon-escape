package ca.sfu.cmpt276.dungeon;

import ca.sfu.cmpt276.dungeon.gameobject.*;
import ca.sfu.cmpt276.dungeon.gameobjecthandlers.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class PauseOverlay {
    private boolean paused = false;

    private StackPane pauseOverlay;

    private final GameHandler gameHandler;

    private final Stage stage;

    public PauseOverlay(GameHandler gameHandler, Stage stage) {
        this.gameHandler = gameHandler;
        this.stage = stage;
    }


    /**
     * Creates the semitransparent pause menu overlay displayed
     * when the player presses the ESC key.
     * <p>
     * The overlay includes options to resume the game or return
     * to the main menu. It is added to the root {@link StackPane}
     * of the active scene and shown/hidden via
     * {@link #pauseGame()} and {@link #resumeGame()}.
     * </p>
     */
    public void createPauseOverlay() {
        javafx.scene.text.Text pausedText = new javafx.scene.text.Text("PAUSED");
        pausedText.setFill(Color.GOLD);
        pausedText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

        Button resumeBtn = new Button("Resume");
        resumeBtn.setOnAction(e -> resumeGame());

        Button quitBtn = new Button("Quit to Menu");
        quitBtn.setOnAction(e -> {
            gameHandler.setRunning(false);
            gameHandler.getLoop().stop();
            new Main().showStartMenu(stage);
        });

        VBox box = new VBox(20, pausedText, resumeBtn, quitBtn);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-padding: 40;");

        pauseOverlay = new StackPane(box);
        pauseOverlay.setVisible(false);
    }

    public void togglePause() {
        if (!paused) pauseGame();
        else resumeGame();
    }

    private void pauseGame() {
        paused = true;
        pauseOverlay.setVisible(true);
    }

    private void resumeGame() {
        paused = false;
        pauseOverlay.setVisible(false);
    }

    public boolean getPaused() {
        return this.paused;
    }

    public StackPane getPauseOverlay() {
        return this.pauseOverlay;
    }
}