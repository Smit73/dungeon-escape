

import ca.sfu.cmpt276.dungeon.Map;
import ca.sfu.cmpt276.dungeon.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Map}. These tests cover:
 * - constructor initialization
 * - default floor fill
 * - boundary behavior in getTile() and isWall()
 * - behavior of setWall()
 * - correctness of walls created in createLevel1()
 */
class MapTest {

    @Test
    void constructor_initializesFloorTiles() {
        Map map = new Map(5, 4);

        assertEquals(5, map.getWidth());
        assertEquals(4, map.getHeight());

        // All tiles should be FLOOR initially
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                assertEquals(Tile.FLOOR, map.getTile(x, y),
                        "Constructor should fill all tiles with FLOOR");
            }
        }
    }

    @Test
    void setWall_changesTile_toWall() {
        Map map = new Map(5, 4);
        assertEquals(Tile.FLOOR, map.getTile(2, 2));

        map.setWall(2, 2);
        assertEquals(Tile.WALL, map.getTile(2, 2),
                "setWall(x,y) should change that tile to WALL");
    }

    @Test
    void setWall_outOfBounds_doesNothing() {
        Map map = new Map(5, 4);

        // These should not throw and should not change anything
        map.setWall(-1, 0);
        map.setWall(5, 0);
        map.setWall(0, -1);
        map.setWall(0, 4);

        // Verify nearby tile still FLOOR (unchanged)
        assertEquals(Tile.FLOOR, map.getTile(0, 0));
    }

    @Test
    void getTile_returnsWall_whenOutOfBounds() {
        Map map = new Map(5, 4);

        assertEquals(Tile.WALL, map.getTile(-1, 0));
        assertEquals(Tile.WALL, map.getTile(5, 0));
        assertEquals(Tile.WALL, map.getTile(0, -1));
        assertEquals(Tile.WALL, map.getTile(0, 4));
    }

    @Test
    void isWall_trueForOutOfBounds() {
        Map map = new Map(5, 4);
        assertTrue(map.isWall(-1, 0));
        assertTrue(map.isWall(5, 0));
        assertTrue(map.isWall(0, -1));
        assertTrue(map.isWall(0, 4));
    }

    @Test
    void isWall_falseForFloorTiles_insideBounds() {
        Map map = new Map(5, 4);
        assertFalse(map.isWall(2, 2));
    }

    @Test
    void createLevel1_setsBoundaryWalls() {
        Map map = new Map(10, 8);
        map.createLevel1();

        // top & bottom walls
        for (int x = 0; x < map.getWidth(); x++) {
            assertTrue(map.isWall(x, 0), "Top wall missing at x=" + x);
            assertTrue(map.isWall(x, map.getHeight() - 1),
                    "Bottom wall missing at x=" + x);
        }

        // left & right walls
        for (int y = 0; y < map.getHeight(); y++) {
            assertTrue(map.isWall(0, y), "Left wall missing at y=" + y);
            assertTrue(map.isWall(map.getWidth() - 1, y),
                    "Right wall missing at y=" + y);
        }
    }

    @Test
    void createLevel1_setsInteriorWalls_correctlyInKnownPositions() {
        Map map = new Map(25, 15);
        map.createLevel1();


        assertEquals(Tile.WALL, map.getTile(2, 2));
        assertEquals(Tile.WALL, map.getTile(8, 2));
        assertEquals(Tile.WALL, map.getTile(4, 4));
        assertEquals(Tile.WALL, map.getTile(17, 4));
        assertEquals(Tile.WALL, map.getTile(10, 6));
        assertEquals(Tile.WALL, map.getTile(10, 9));
    }
}
