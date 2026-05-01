

import ca.sfu.cmpt276.dungeon.GameHandler;
import ca.sfu.cmpt276.dungeon.Round;
import ca.sfu.cmpt276.dungeon.SpawnBounds;
import ca.sfu.cmpt276.dungeon.gameobject.Chest;
import ca.sfu.cmpt276.dungeon.gameobject.Exit;
import ca.sfu.cmpt276.dungeon.gameobject.Gem;
import ca.sfu.cmpt276.dungeon.gameobject.Monster;
import ca.sfu.cmpt276.dungeon.gameobject.Trap;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link GameHandler} that focus on its
 * initialization behaviour.
 *
 * <p>
 * These tests avoid starting JavaFX or creating real scenes.
 * We construct a {@code GameHandler} with a {@code null} stage
 * and inspect its private {@link Round} state via reflection.
 * </p>
 */
class GameHandlerTest {

    /**
     * Helper to access the private {@code round} field from GameHandler.
     */
    private Round extractRound(GameHandler handler) throws Exception {
        Field roundField = GameHandler.class.getDeclaredField("round");
        roundField.setAccessible(true);
        return (Round) roundField.get(handler);
    }

    /**
     * Helper to read a private boolean flag from GameHandler.
     */
    private boolean getBooleanFlag(GameHandler handler, String name) throws Exception {
        Field f = GameHandler.class.getDeclaredField(name);
        f.setAccessible(true);
        return f.getBoolean(handler);
    }

    /**
     * Verifies that constructing a {@link GameHandler} correctly
     * initializes the round, creates a player, and spawns entities
     * according to the {@link SpawnBounds} configuration.
     */
//    @Test
//    void constructor_initializesRoundAndSpawnsEntities() throws Exception {
//        // We pass null Stage: startGame() is never called in this test,
//        // so no JavaFX scene is created.
//        GameHandler handler = new GameHandler(null);
//
//        Round round = extractRound(handler);
//
//        // Round should be active after initializeRound()
//        assertNotNull(round.player, "Player should be created");
//        assertTrue(round.roundActive, "Round should be active after startRound()");
//
//        // Player spawn position should match SpawnBounds
//        assertEquals(SpawnBounds.PLAYER_X, round.player.getX(), "Player X spawn mismatch");
//        assertEquals(SpawnBounds.PLAYER_Y, round.player.getY(), "Player Y spawn mismatch");
//
//        // Monsters
//        List<Monster> monsters = round.monsters;
//        assertEquals(SpawnBounds.MONSTER_AMOUNT, monsters.size(),
//                "Unexpected number of spawned monsters");
//
//        // Gems
//        List<Gem> gems = round.gems;
//        assertEquals(SpawnBounds.GEM_AMOUNT, gems.size(),
//                "Unexpected number of spawned gems");
//
//        // Traps
//        List<Trap> traps = round.traps;
//        assertEquals(SpawnBounds.TRAP_AMOUNT, traps.size(),
//                "Unexpected number of spawned traps");
//
//        // Exits
//        List<Exit> exits = round.exits;
//        assertEquals(SpawnBounds.EXIT_AMOUNT, exits.size(),
//                "Unexpected number of spawned exits");
//
//        // Chests
//        List<Chest> chests = round.chests;
//        assertEquals(SpawnBounds.CHEST_AMOUNT, chests.size(),
//                "Unexpected number of spawned chests");
//    }

    /**
     * Ensures that control flags inside {@link GameHandler}
     * start in the expected state before the main loop begins.
     */
//    @Test
//    void flags_startInExpectedState() throws Exception {
//        GameHandler handler = new GameHandler(null);
//
//        boolean running = getBooleanFlag(handler, "running");
//        boolean paused = getBooleanFlag(handler, "paused");
//
//        assertFalse(running, "Game should not be running before startGame() is called");
//        assertFalse(paused, "Game should not start in a paused state");
//    }
}
