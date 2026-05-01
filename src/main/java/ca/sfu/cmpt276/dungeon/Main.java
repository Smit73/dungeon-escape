package ca.sfu.cmpt276.dungeon;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * CMPT 276 - Dungeon Escape
 * JavaFX entry point (non-modular, Java 23). So use "mvn javafx:run" to run (The run button in your ide will probably not work since you have to set up in ur vm)
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
       showStartMenu(stage);
    }
    /**
     * Starts a new game session.
     */
    public void startGame(Stage stage) {

        StackPane loading = new StackPane(new Text("Loading..."));
        loading.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        stage.setScene(new Scene(loading, 960, 640));

        // Simulate quick load delay
        new Thread(() -> {
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(() -> {
                GameHandler controller = new GameHandler(stage);
                controller.startGame();
            });
        }).start();
    }
    /**
     * Displays the main menu with a professional game-style design.
     *
     * @param stage The primary JavaFX stage.
     */
    void showStartMenu(Stage stage) {
        ButtonMaker btnMk = new ButtonMaker(this);
    //title
        Text title = new Text("DUNGEON ESCAPE");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        title.setFill(Color.GOLD);
        title.setEffect(new DropShadow(20, Color.BLACK));

        Button startButton = btnMk.makeStartButton(stage);
        Button exitButton = btnMk.makeExitButton(stage);

        VBox menuBox = new VBox(25, title, startButton, exitButton);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(40));

        //  Background
        StackPane root = new StackPane(menuBox);
        // gradient background
        root.setStyle("""
        -fx-background-color: linear-gradient(to bottom, #1e1e1e, #000000);
    """);
    
        Scene menuScene = new Scene(root, 960, 640);

        stage.setTitle("Dungeon Escape");
        stage.setScene(menuScene);
        stage.show();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
