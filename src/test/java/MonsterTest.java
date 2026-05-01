import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ca.sfu.cmpt276.dungeon.Direction;
import ca.sfu.cmpt276.dungeon.gameobject.Monster;

@SuppressWarnings({"unchecked"})
@TestInstance(Lifecycle.PER_CLASS)
public class MonsterTest extends TestEnvironment {
    public MonsterTest() {
        super();
    }

    Stream<Object[]> availableDirectionsTestProvider() {
        return Stream.of(
            new Object[]{new Monster(7, 9, round), Direction.UP, new Direction[]{Direction.UP, Direction.LEFT, Direction.RIGHT}},
            new Object[]{new Monster(7, 9, round), Direction.DOWN, new Direction[]{Direction.DOWN, Direction.RIGHT, Direction.LEFT}},
            new Object[]{new Monster(7, 9, round), Direction.LEFT, new Direction[]{Direction.LEFT, Direction.DOWN, Direction.UP}},
            new Object[]{new Monster(7, 9, round), Direction.RIGHT, new Direction[]{Direction.RIGHT, Direction.UP, Direction.DOWN}},
            new Object[]{new Monster(13, 8, round), Direction.RIGHT, new Direction[]{Direction.LEFT}},
            new Object[]{new Monster(16, 3, round), Direction.RIGHT, new Direction[]{Direction.RIGHT,Direction.DOWN}}
        );
    }

    @ParameterizedTest
    @MethodSource("availableDirectionsTestProvider")
    public void availableDirections_returnsArray(Object monster, Direction direction, Direction[] expected) {
        Method availableDirections = getPrivateMethod(monster, "getAvailableDirections", Direction.class);
        try {    
            ArrayList<Direction> directions = (ArrayList<Direction>) availableDirections.invoke(monster, direction);
            Object[] actual = directions.toArray();
            System.out.println(Arrays.toString(actual));
            assertArrayEquals(expected, actual);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            System.out.println("An exception has occured");
        }
    }

    @Test
    public void MonsterMoveDownTest() {
        Monster monster = new Monster(1, 1, round);
        monster.move(1, 2);
        assertTrue(monster.getX() == 1);
        assertTrue(monster.getY() == 2);
    }

    @Test
    public void MonsterMoveRightTest() {
        Monster monster = new Monster(1, 1, round);
        monster.move(2, 1);
        assertTrue(monster.getX() == 2);
        assertTrue(monster.getY() == 1);
    }

    @Test
    public void MonsterMoveUpTest() {
        Monster monster = new Monster(1, 2, round);
        monster.move(1, 1);
        assertTrue(monster.getX() == 1);
        assertTrue(monster.getY() == 1);
    }

    @Test
    public void MonsterMoveLeftIntoWallTest() {
        Monster monster = new Monster(1, 1, round);
        monster.move(Direction.LEFT);
        assertTrue(monster.getX() == 1);
        assertTrue(monster.getY() == 1);
    }

    @Test
    public void MonsterDontMoveTest() {
        Monster monster = new Monster(1, 1, round);
        monster.move(1, 1);
        assertTrue(monster.getX() == 1);
        assertTrue(monster.getY() == 1);
    }
}