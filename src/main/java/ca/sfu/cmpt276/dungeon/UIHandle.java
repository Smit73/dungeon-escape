package ca.sfu.cmpt276.dungeon;

import ca.sfu.cmpt276.dungeon.gameobject.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.Objects;

/**
 * Handles JavaFX-based drawing and keyboard input for the active game scene.
 * Renders tiles, player, and monsters using a {@link Canvas}.
 *
 * <p>Acts as the view layer in the MVC pattern:
 * - {@link GameHandler} controls logic.
 * - {@link Round} holds game state.
 * - {@link UIHandle} renders it visually.</p>
 */
public class UIHandle {

    /** Canvas resolution. */
    private static final int WIDTH = 960;
    private static final int HEIGHT = 640;
    private static final int TILE_SIZE = 40;

    private final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private Direction lastInput = null;
    private GraphicsContext gc;

    // Layer where animated GIF ImageViews live:
    private final Pane entityLayer = new Pane();

    // Keep an ImageView per Monster
    private final DrawEntity drawMonster = new DrawEntity(entityLayer);
    private final DrawEntity drawGem = new DrawEntity(entityLayer);
    private final DrawEntity drawTrap = new DrawEntity(entityLayer);
    private final DrawEntity drawExit = new DrawEntity(entityLayer);
    private final DrawEntity drawChest = new DrawEntity(entityLayer);
    private final DrawEntity drawPlayer = new DrawEntity(entityLayer);


    /**
     * Sets up the JavaFX scene for gameplay.
     *
     * @param stage the JavaFX window (from {@link GameHandler})
     */
    public void setup(Stage stage) {
        StackPane root = new StackPane(canvas,entityLayer);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        // keep entityLayer sized with the canvas
        entityLayer.prefWidthProperty().bind(canvas.widthProperty());
        entityLayer.prefHeightProperty().bind(canvas.heightProperty());

        stage.setScene(scene);
        stage.setTitle("Dungeon Escape");
        stage.show();

        gc = canvas.getGraphicsContext2D();

        // Keyboard input listener
        scene.setOnKeyPressed(e -> {
            KeyCode k = e.getCode();
            if (k == KeyCode.W || k == KeyCode.UP) lastInput = Direction.UP;
            if (k == KeyCode.S || k == KeyCode.DOWN) lastInput = Direction.DOWN;
            if (k == KeyCode.A || k == KeyCode.LEFT) lastInput = Direction.LEFT;
            if (k == KeyCode.D || k == KeyCode.RIGHT) lastInput = Direction.RIGHT;
        });
    }

    /**
     * Retrieves the most recent keyboard direction input.
     * Resets after each call.
     *
     * @return The direction pressed, or {@code null} if none.
     */
    public Direction pollInput() {
        Direction dir = lastInput;
        lastInput = null;
        return dir;
    }

    /**
     * Draws the map, player, and enemies each frame.
     *
     * @param round     the active game round (for entities and state)
     * @param player    the player character
     * @param monsters  list of monsters to draw
     */
    public void render(Round round, Player player, List<Monster> monsters, List<Gem> gems, List<Trap> traps, List<Exit> exits, List<Chest> chests) {
        // Clear screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        drawMap(round);

        drawAll(player, monsters, gems, traps, exits, chests);

        // Draw HUD (score/time placeholders)
        drawHUD(player);
    }

    /**
     * Draws a single tile based on its {@link Tile} type.
     */
    private void drawTile(Tile tile, int x, int y) {
        Color fillColor = switch (tile) {
            case WALL -> Color.DARKSLATEGRAY;
            case FLOOR -> Color.DIMGRAY;
            default -> Color.DARKRED; // fallback colour for unknown tiles
        };
        gc.setFill(fillColor);
        gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
    }

    private void drawMap(Round round) {
        ca.sfu.cmpt276.dungeon.Map map = round.map;
        if (map != null) {
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    Tile tile = map.getTile(x, y);
                    drawTile(tile, x, y);
                }
            }
        }
    }

    /**
     * Draws the heads-up display (score, timer, etc.) on top of the scene.
     *
     * @param player The player whose stats to display.
     */
    private void drawHUD(Player player) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Consolas", 18));
        gc.fillText("Score: " + player.getScore(), 15, 25);
        gc.fillText("Press ESC to pause", WIDTH - 200, 25);
    }

    /**
     * Builds the score text shown in the HUD.
     *
     * @param player player whose score is shown
     * @return formatted score string
     */
    public String buildScoreText(Player player) {
        return "Score: " + player.getScore();
    }

    /**
     * Builds the pause hint text shown in the HUD.
     *
     * @return static pause hint string
     */
    public String buildPauseHintText() {
        return "Press ESC to pause";
    }

    private void drawAll(Player player, List<Monster> monsters, List<Gem> gems, List<Trap> traps, List<Exit> exits, List<Chest> chests) {
        // --- Draw player ---
        drawPlayer.draw(player, "/images/player.png", TILE_SIZE);

        // --- Draw gems ---
        drawGem.draw(gems, "/images/gem.png", TILE_SIZE);

        // --- Draw chests ---
        //if (!chests.isEmpty()) {
        SpawnBounds.chestLock.lock();
        try {
            drawChest.draw(chests, "/images/chest.png", TILE_SIZE);
        } finally {
            SpawnBounds.chestLock.unlock();
        }
        //}

        // --- Draw traps ---
        drawTrap.draw(traps, "/images/trap.png", TILE_SIZE);

        // --- Draw Exits ---
        drawExit.draw(exits, "/images/exit.png", TILE_SIZE);

        // --- Draw Monsters ---
        drawMonster.draw(monsters, "/images/frame-1.png", TILE_SIZE);
    }
}
