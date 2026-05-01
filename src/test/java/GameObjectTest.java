import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Random;

import ca.sfu.cmpt276.dungeon.*;
import ca.sfu.cmpt276.dungeon.gameobject.*;
import ca.sfu.cmpt276.dungeon.gameobjecthandlers.*;

public class GameObjectTest {

    @Test
    public void testChestInteract() {
        Round round = new Round();

        Player playa = new Player(1, 1, round);
        Chest chest = new Chest(1, 1, round);

        int initScore = playa.getScore();
        chest.interact(playa);

        assertTrue((playa.getScore() - initScore) == Rules.CHEST_SCORE);
    }

    @Test
    public void testExitInteract() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        Exit exit = new Exit(1, 1, round);

        exit.interact(playa);

        assertTrue(!round.roundActive);
    }

    @Test
    public void testGemRespawnNoFail() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        Random fake = new FakeRandom(1, 2);
        Gem gem = new Gem(1, 1, round, fake);

        gem.interact(playa);

        assertTrue(gem.getX() == 1);
        assertTrue(gem.getY() == 2);
    }

    @Test
    public void testGemRespawnFailWall() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        Random fake = new FakeRandom(6, 7, 1, 2);
        Gem gem = new Gem(1, 1, round, fake);

        gem.interact(playa);

        assertTrue(gem.getX() == 1);
        assertTrue(gem.getY() == 2);
    }

    @Test
    public void testGemRespawnFailEntity() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        Random fake = new FakeRandom(15, 7, 1, 2);
        Gem gem = new Gem(1, 1, round, fake);
        ExitHandler exitHandler = new ExitHandler(round);
        exitHandler.spawn(1);

        gem.interact(playa);

        assertTrue(gem.getX() == 1);
        assertTrue(gem.getY() == 2);
    }

    // test moving up down left right, and running into wall

    @Test
    public void testPlayerMoveUp() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 2, round);
        playa.move(Direction.UP);

        assertTrue(playa.getX() == 1);
        assertTrue(playa.getY() == 1);
    }
    @Test
    public void testPlayerMoveUpWALL() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        playa.move(Direction.UP);

        assertTrue(playa.getX() == 1);
        assertTrue(playa.getY() == 1);
    }

    @Test
    public void testPlayerMoveDown() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 2, round);
        playa.move(Direction.DOWN);

        assertTrue(playa.getX() == 1);
        assertTrue(playa.getY() == 3);
    }
    @Test
    public void testPlayerMoveDownWALL() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(2, 1, round);
        playa.move(Direction.DOWN);

        assertTrue(playa.getX() == 2);
        assertTrue(playa.getY() == 1);
    }

    @Test
    public void testPlayerMoveRight() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        playa.move(Direction.RIGHT);

        assertTrue(playa.getX() == 2);
        assertTrue(playa.getY() == 1);
    }
    @Test
    public void testPlayerMoveRightWALL() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 2, round);
        playa.move(Direction.RIGHT);

        assertTrue(playa.getX() == 1);
        assertTrue(playa.getY() == 2);
    }

    @Test
    public void testPlayerMoveLeft() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(2, 1, round);
        playa.move(Direction.LEFT);

        assertTrue(playa.getX() == 1);
        assertTrue(playa.getY() == 1);
    }
    @Test
    public void testPlayerMoveLeftWALL() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        playa.move(Direction.LEFT);

        assertTrue(playa.getX() == 1);
        assertTrue(playa.getY() == 1);
    }

    @Test
    public void testTrapRespawnNoFail() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        Random fake = new FakeRandom(1, 2);
        Trap trap = new Trap(1, 1, round, fake);

        trap.interact(playa);

        assertTrue(trap.getX() == 1);
        assertTrue(trap.getY() == 2);
    }

    @Test
    public void testTrapRespawnFailWall() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        Random fake = new FakeRandom(6, 7, 1, 2);
        Trap trap = new Trap(1, 1, round, fake);

        trap.interact(playa);

        assertTrue(trap.getX() == 1);
        assertTrue(trap.getY() == 2);
    }

    @Test
    public void testTrapRespawnFailEntity() {
        Round round = new Round();
        round.startRound();

        Player playa = new Player(1, 1, round);
        Random fake = new FakeRandom(15, 7, 1, 2);
        Trap trap = new Trap(1, 1, round, fake);
        ExitHandler exitHandler = new ExitHandler(round);
        exitHandler.spawn(1);

        trap.interact(playa);

        assertTrue(trap.getX() == 1);
        assertTrue(trap.getY() == 2);
    }
}