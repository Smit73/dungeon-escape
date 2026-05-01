

import ca.sfu.cmpt276.dungeon.Round;
import ca.sfu.cmpt276.dungeon.gameobject.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Round} that verify round lifecycle,
 * entity collections, and the internal entity grid behaviour.
 */
class RoundTest {

    /**
     * Verifies that the constructor initializes all collections,
     * the map, and the collision handler, and that the round
     * starts inactive.
     */
    @Test
    void constructor_initializesCollectionsAndMap() {
        Round r = new Round();

        assertNotNull(r.monsters, "Monsters list should be initialized");
        assertNotNull(r.traps, "Traps list should be initialized");
        assertNotNull(r.gems, "Gems list should be initialized");
        assertNotNull(r.chests, "Chests list should be initialized");
        assertNotNull(r.exits, "Exits list should be initialized");
        assertNotNull(r.map, "Map should be initialized");
        assertNotNull(r.collisionHandler, "CollisionHandler should be initialized");

        assertFalse(r.roundActive, "Round should start as inactive");
    }

    /**
     * startRound() should activate the round and call level creation
     * on the map (we only assert the active flag here).
     */
    @Test
    void startRound_setsRoundActiveTrue() {
        Round r = new Round();
        assertFalse(r.roundActive, "Round should initially be inactive");

        r.startRound();

        assertTrue(r.roundActive, "startRound() should set roundActive = true");
    }

    /**
     * endRound() should deactivate the round and be safe to call multiple times
     * without throwing or flipping state incorrectly.
     */
    @Test
    void endRound_setsRoundActiveFalse_andIsIdempotent() {
        Round r = new Round();
        r.startRound();
        assertTrue(r.roundActive, "Precondition: round should be active");

        r.endRound();
        assertFalse(r.roundActive, "endRound() should set roundActive = false");

        // second call should do nothing (idempotent)
        r.endRound();
        assertFalse(r.roundActive, "Second endRound() call should not re-activate the round");
    }

    /**
     * addEntity() and getEntity() should correctly store and return
     * an entity reference at the given grid coordinates.
     */
    @Test
    void addEntity_and_getEntity_storeAndReturnSameObject() {
        Round r = new Round();
        r.player = new Player(2, 3, r);

        int x = 1;
        int y = 2;
        r.addEntity(x, y, r.player);

        assertSame(r.player, r.getEntity(x, y),
                "getEntity() should return the same object passed to addEntity()");
    }

    /**
     * removeEntity() should clear the grid cell so getEntity()
     * returns null afterwards.
     */
    @Test
    void removeEntity_clearsGridCell() {
        Round r = new Round();
        Player p = new Player(1, 1, r);

        int x = 0;
        int y = 0;
        r.addEntity(x, y, p);
        assertNotNull(r.getEntity(x, y), "Precondition: entity should be present before removal");

        r.removeEntity(x, y);

        assertNull(r.getEntity(x, y),
                "removeEntity() should clear the cell so getEntity() returns null");
    }

    /**
     * moveEntity() should move the same object reference from the
     * source cell to the target cell and clear the original position.
     */
    @Test
    void moveEntity_movesSameReferenceAndClearsOriginalCell() {
        Round r = new Round();
        Player p = new Player(5, 5, r);

        int fromX = 2, fromY = 3;
        int toX = 4, toY = 1;

        r.addEntity(fromX, fromY, p);
        assertSame(p, r.getEntity(fromX, fromY), "Precondition: entity should be at source cell");

        r.moveEntity(fromX, fromY, toX, toY);

        assertNull(r.getEntity(fromX, fromY),
                "Source cell should be cleared after moveEntity()");
        assertSame(p, r.getEntity(toX, toY),
                "Target cell should now contain the moved entity");
    }
}
