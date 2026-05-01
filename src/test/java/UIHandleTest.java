

import ca.sfu.cmpt276.dungeon.Direction;
import ca.sfu.cmpt276.dungeon.Round;
import ca.sfu.cmpt276.dungeon.UIHandle;
import ca.sfu.cmpt276.dungeon.gameobject.Player;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link UIHandle} that focus on pure logic:
 * - keyboard polling behaviour
 * - HUD score text generation.
 *
 * These tests do not start JavaFX or call {@code setup(Stage)}.
 */
class UIHandleTest {

    /**
     * Uses reflection to set the private lastInput field on UIHandle.
     */
    private void setLastInput(UIHandle ui, Direction dir) throws Exception {
        Field f = UIHandle.class.getDeclaredField("lastInput");
        f.setAccessible(true);
        f.set(ui, dir);
    }

    /**
     * Verifies that {@link UIHandle#pollInput()} returns the last
     * direction and clears it so the next call returns null.
     */
    @Test
    void pollInput_returnsAndClearsLastInput() throws Exception {
        UIHandle ui = new UIHandle();

        // Pretend the user pressed UP
        setLastInput(ui, Direction.UP);

        // First poll returns UP
        Direction first = ui.pollInput();
        assertEquals(Direction.UP, first, "pollInput() should return last direction");

        // Second poll returns null (cleared)
        Direction second = ui.pollInput();
        assertNull(second, "pollInput() should clear lastInput after returning it");
    }

    /**
     * Verifies that HUD score text reflects the player's score.
     */
    @Test
    void buildScoreText_reflectsPlayerScore() {
        UIHandle ui = new UIHandle();
        Round r = new Round();
        Player p = new Player(2, 3, r);
        p.addScore(150);

        String text = ui.buildScoreText(p);
        assertEquals("Score: 150", text,
                "HUD score text should match the player's current score");
    }

    /**
     * Verifies that the pause hint text is the expected static string.
     */
    @Test
    void buildPauseHintText_isStaticMessage() {
        UIHandle ui = new UIHandle();
        assertEquals("Press ESC to pause", ui.buildPauseHintText());
    }
}
