import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ca.sfu.cmpt276.dungeon.gameobject.Monster;
import ca.sfu.cmpt276.dungeon.gameobject.Player;

public class CollisionHandlerTest extends TestEnvironment {
    public CollisionHandlerTest() {
        super();
    }

    @Test
    public void rayCastWithNullArgument_throwsException() {
        Player player = new Player(1,1, round);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            collisionHandler.rayCast(null, player);
        });
    }

    @Test 
    public void collisionCheckWithNullArguments_throwsException() {
        Player player = new Player(1,1, round);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            collisionHandler.isColliding(null, player);
        });
    }

    @Test
    public void verticalRayCastReachesTarget_returnsTrue() {
        Monster monster = new Monster(1,3, round);
        Player player = new Player(1,1, round);
        boolean isVisible = !collisionHandler.rayCast(monster, player);
        assertTrue(isVisible);
    }

    @Test
    public void verticalRayCastDoesNotReachTarget_returnsFalse() {
        Monster monster = new Monster(2,3, round);
        Player player = new Player(2,1, round);
        boolean isVisible = !collisionHandler.rayCast(monster, player);
        assertFalse(isVisible);
    }

    @Test
    public void negativeDxRayCastReachesTarget_returnsTrue() {
        Monster monster = new Monster(9,6, round);
        Player player = new Player(3,5, round);
        boolean isVisible = !collisionHandler.rayCast(monster, player);
        assertTrue(isVisible);
    }

    @Test
    public void negativeDxRayCastDoesNotReachTarget_returnsFalse() {
        Monster monster = new Monster(8,1, round);
        Player player = new Player(3,5, round);
        boolean isVisible = !collisionHandler.rayCast(monster, player);
        assertFalse(isVisible);
    }

    @Test
    public void positiveDxRayCastReachesTarget_returnsTrue() {
        Monster monster = new Monster(3,10, round);
        Player player = new Player(7,9, round);
        boolean isVisible = !collisionHandler.rayCast(monster, player);
        assertTrue(isVisible);
    }

    @Test
    public void positiveDxRayCastDoesNotReachTarget_returnsFalse() {
        Player player = new Player(9,12, round);
        Monster monster = new Monster(3,10, round);
        boolean isVisible = !collisionHandler.rayCast(monster, player);
        assertFalse(isVisible);
    }

    @Test
    public void objectsAreColliding_returnsTrue() { 
        Monster monster = new Monster(1,1, round);
        Player player = new Player(1,1, round);
        boolean isColliding = collisionHandler.isColliding(monster, player);
        assertTrue(isColliding);
    }

    @Test
    public void objectsAreNotColliding_returnsFalse() {
        Monster monster = new Monster(2,1, round);
        Player player = new Player(1,1, round);
        boolean isColliding = collisionHandler.isColliding(monster, player);
        assertFalse(isColliding);
    }
}
