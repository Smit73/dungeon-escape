import ca.sfu.cmpt276.dungeon.*;
import ca.sfu.cmpt276.dungeon.gameobject.Chest;
import ca.sfu.cmpt276.dungeon.gameobject.Player;
import ca.sfu.cmpt276.dungeon.gameobjecthandlers.TrapHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TrapHandlerTest {

    @Test
    void SpawnZero() {
        Round round = new Round();
        round.startRound();

        TrapHandler trapHandler = new TrapHandler(round);
        trapHandler.spawn(0);

        assertTrue(round.traps.isEmpty());
    }

    @Test
    void SpawnOneNoObstacle() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1);
        TrapHandler trapHandler = new TrapHandler(round, fake);
        trapHandler.spawn(1);

        assertTrue(round.traps.size() == 1);
        assertTrue(round.traps.get(0).getX() == 1);
        assertTrue(round.traps.get(0).getY() == 1);
    }

    @Test
    void SpawnTwoNoObstacle() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1, 1, 2);
        TrapHandler trapHandler = new TrapHandler(round, fake);
        trapHandler.spawn(2);

        assertTrue(round.traps.size() == 2);
        assertTrue(round.traps.get(0).getX() == 1);
        assertTrue(round.traps.get(0).getY() == 1);
        assertTrue(round.traps.get(1).getX() == 1);
        assertTrue(round.traps.get(1).getY() == 2);
    }

    @Test
    void SpawnOneWall() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(2, 2, 1, 1);
        TrapHandler trapHandler = new TrapHandler(round, fake);
        trapHandler.spawn(1);

        assertTrue(round.traps.size() == 1);
        assertTrue(round.traps.get(0).getX() == 1);
        assertTrue(round.traps.get(0).getY() == 1);
    }

    @Test
    void SpawnSecondOnFirst() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1, 1, 1, 1, 2);
        TrapHandler trapHandler = new TrapHandler(round, fake);
        trapHandler.spawn(2);

        assertTrue(round.traps.size() == 2);
        assertTrue(round.traps.get(0).getX() == 1);
        assertTrue(round.traps.get(0).getY() == 1);
        assertTrue(round.traps.get(1).getX() == 1);
        assertTrue(round.traps.get(1).getY() == 2);
    }

}