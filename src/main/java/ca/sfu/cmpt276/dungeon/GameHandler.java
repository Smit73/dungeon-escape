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



/**
 * Central class controlling one active play session.
 * Handles the game loop, input, updates, and win/lose transitions.
 *
 * <p>Architecture:</p>
 * <ul>
 * <li>{@link Round} holds all game entities and state.</li>
 * <li>{@link MonsterHandler} handles enemy AI updates.</li>
 * <li>{@link UIHandle} handles rendering and input.</li>
 * </ul>
 */
public class GameHandler {

    private final Stage stage;
    private final Round round;
    private final UIHandle ui;
    private final MonsterHandler monsterHandler;
    private final GemHandler gemHandler;
    private final TrapHandler trapHandler;
    private final ExitHandler exitHandler;
    private final ChestHandler chestHandler;

    private boolean running = false;
    private AnimationTimer loop;
    private boolean exitAvail = false;

    private final PauseOverlay po;

    /**
     * Constructs the controller for a new game.
     *
     * @param stage The JavaFX window used for display.
     */
    public GameHandler(Stage stage) {
        this.stage = stage;
        this.round = new Round();
        this.ui = new UIHandle();
        this.monsterHandler = new MonsterHandler(round);
        this.gemHandler = new GemHandler(round);
        this.trapHandler = new TrapHandler(round);
        this.exitHandler = new ExitHandler(round);
        this.chestHandler = new ChestHandler(round);

        this.po = new PauseOverlay(this, stage);

        initializeRound();
    }

    /**
     * Sets up all initial round entities and starts the game.
     */
    private void initializeRound() {
        round.player = new Player(SpawnBounds.PLAYER_X, SpawnBounds.PLAYER_Y, round);
        round.startRound();
        
        monsterHandler.spawn(SpawnBounds.MONSTER_AMOUNT);
        gemHandler.spawn(SpawnBounds.GEM_AMOUNT);
        trapHandler.spawn(SpawnBounds.TRAP_AMOUNT);
        //exitHandler.spawn(SpawnBounds.EXIT_AMOUNT);
        
        SpawnBounds.chestLock.lock();
        try {
            chestHandler.spawn(SpawnBounds.CHEST_AMOUNT);
        } finally {
            SpawnBounds.chestLock.unlock();
        }    
    }

    /**
     * Starts the JavaFX-based main loop.
     */
    public void startGame() {
        ui.setup(stage);
        //createPauseOverlay();
        po.createPauseOverlay();
        // add overlay to the existing scene graph
        ((StackPane) stage.getScene().getRoot()).getChildren().add(po.getPauseOverlay()); // pauseOverlay;
        Scene scene = stage.getScene();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                po.togglePause();
                e.consume();
            }
        });
        running = true;

        loop = new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                if (!running) return;
                if (now - last < 120_000_000) return; // 8 ticks/sec
                last = now;
                tick();
            }
        };
        loop.start();
    }

    /**
     * Runs one tick of the game logic.
     */
    private void tick() {

        // round actually ended  game over
        if (!round.roundActive) {
            endGame(false);
            return;
        }

        // paused  don't update logic; just redraw frame
        if (po.getPaused()) {
            ui.render(round, round.player,
                    monsterHandler.get(), gemHandler.get(), trapHandler.get(),
                    exitHandler.get(), chestHandler.get());
            return;
        }


        // Handle player input
        Direction input = ui.pollInput();
        if (input != null && round.roundActive) {
            round.player.move(input); //  integrate map collision later
        }

        if (exitHandler.update()) {
            endGame(true);
            return;
        }

        //  Update monsters
        if (monsterHandler.update()) {
            endGame(false);
            return;
        }

        gemHandler.update();

        trapHandler.update();

        chestHandler.update();


        //  Lose condition (player score below 0)
        if (round.player.getScore() < 0) {
            round.endRound(); // mark the round inactive
            endGame(false);   // trigger defeat screen
            return;
        }
        //Win condition
        if (round.player.getScore() >= 300 && !this.exitAvail) {
            exitHandler.spawn(SpawnBounds.EXIT_AMOUNT);
            this.exitAvail = true;
            //round.endRound(); // mark the round inactive
            //endGame(true);   // trigger win screen
            return;
        }

        //  Draw everything
        ui.render(round, round.player, monsterHandler.get(), gemHandler.get(), trapHandler.get(), exitHandler.get(), chestHandler.get());
    }

    /**
     * Ends the game and shows a victory or defeat screen.
     *
     * @param victory True if the player won, false if lost.
     */
    private void endGame(boolean victory) {
        running = false;
        loop.stop();
        round.endRound();

        String message = victory ? "🏆 YOU ESCAPED!" : "💀 YOU WERE CAUGHT!";
        String ScoreMessage = "SCORE: " + round.player.getScore();

        javafx.scene.text.Text endText = new javafx.scene.text.Text(message);
        javafx.scene.text.Text scoreText = new javafx.scene.text.Text(ScoreMessage);

        endText.setFill(victory ? Color.LIMEGREEN : Color.CRIMSON);
        scoreText.setFill(Color.LIMEGREEN);

        endText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        scoreText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");


        Button backToMenu = new Button("Return to Main Menu");
        backToMenu.setStyle("""
            -fx-font-size: 18px;
            -fx-background-color: #333;
            -fx-text-fill: white;
            -fx-border-color: gold;
            -fx-border-width: 2;
            -fx-background-radius: 8;
            -fx-border-radius: 8;
        """);
        backToMenu.setOnAction(e -> new Main().showStartMenu(stage));

        StackPane root = new StackPane(endText, backToMenu);
        StackPane.setAlignment(endText, javafx.geometry.Pos.TOP_CENTER);

        if (victory) {
            root.getChildren().add(scoreText);
            StackPane.setAlignment(scoreText, javafx.geometry.Pos.CENTER);
        }

        StackPane.setAlignment(backToMenu, javafx.geometry.Pos.BOTTOM_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a1a, #000000);");

        stage.setScene(new Scene(root, 960, 640));
    }

    public AnimationTimer getLoop() {
        return this.loop;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
