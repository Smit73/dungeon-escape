import ca.sfu.cmpt276.dungeon.*;
import ca.sfu.cmpt276.dungeon.gameobject.Chest;
import ca.sfu.cmpt276.dungeon.gameobject.Player;
import ca.sfu.cmpt276.dungeon.gameobjecthandlers.GemHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GemHandlerTest {

    @Test
    void SpawnZero() {
        Round round = new Round();
        round.startRound();

        GemHandler gemHandler = new GemHandler(round);
        gemHandler.spawn(0);

        assertTrue(round.gems.isEmpty());
    }

    @Test
    void SpawnOneNoObstacle() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1);
        GemHandler gemHandler = new GemHandler(round, fake);
        gemHandler.spawn(1);

        assertTrue(round.gems.size() == 1);
        assertTrue(round.gems.get(0).getX() == 1);
        assertTrue(round.gems.get(0).getY() == 1);
    }

    @Test
    void SpawnTwoNoObstacle() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1, 1, 2);
        GemHandler gemHandler = new GemHandler(round, fake);
        gemHandler.spawn(2);

        assertTrue(round.gems.size() == 2);
        assertTrue(round.gems.get(0).getX() == 1);
        assertTrue(round.gems.get(0).getY() == 1);
        assertTrue(round.gems.get(1).getX() == 1);
        assertTrue(round.gems.get(1).getY() == 2);
    }

    @Test
    void SpawnOneWall() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(2, 2, 1, 1);
        GemHandler gemHandler = new GemHandler(round, fake);
        gemHandler.spawn(1);

        assertTrue(round.gems.size() == 1);
        assertTrue(round.gems.get(0).getX() == 1);
        assertTrue(round.gems.get(0).getY() == 1);
    }

    @Test
    void SpawnSecondOnFirst() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1, 1, 1, 1, 2);
        GemHandler gemHandler = new GemHandler(round, fake);
        gemHandler.spawn(2);

        assertTrue(round.gems.size() == 2);
        assertTrue(round.gems.get(0).getX() == 1);
        assertTrue(round.gems.get(0).getY() == 1);
        assertTrue(round.gems.get(1).getX() == 1);
        assertTrue(round.gems.get(1).getY() == 2);
    }

    @Test
    void updateNoRound() {
        Round round = new Round();
        round.startRound();

        GemHandler gemHandler = new GemHandler(round);
        gemHandler.spawn(2);

        round.endRound();

        assertTrue(!gemHandler.update());
    }

    @Test
    void updateNoGems() {
        Round round = new Round();
        round.startRound();

        GemHandler gemHandler = new GemHandler(round);

        assertTrue(!gemHandler.update());
    }

    @Test
    void updateNoPlayerOnGems() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 2, 2, 1, 1, 3);
        GemHandler gemHandler = new GemHandler(round, fake);
        gemHandler.spawn(3);

        round.player = new Player(1, 1, round);

        assertTrue(!gemHandler.update());
    }

    @Test
    void updatePlayerOnGem() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 2, 2, 1, 1, 3);
        GemHandler gemHandler = new GemHandler(round, fake);
        gemHandler.spawn(3);

        round.player = new Player(1, 3, round);

        assertTrue(gemHandler.update());
    }

}