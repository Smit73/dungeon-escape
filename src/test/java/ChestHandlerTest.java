import ca.sfu.cmpt276.dungeon.*;
import ca.sfu.cmpt276.dungeon.gameobject.Chest;
import ca.sfu.cmpt276.dungeon.gameobject.Player;
import ca.sfu.cmpt276.dungeon.gameobjecthandlers.ChestHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ChestHandlerTest {

    @Test
    void SpawnZero() {
        Round round = new Round();
        round.startRound();

        ChestHandler chestHandler = new ChestHandler(round);
        chestHandler.spawn(0);

        assertTrue(round.chests.isEmpty());
    }

    @Test
    void SpawnOneNoObstacle() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(1);

        assertTrue(round.chests.size() == 1);
        assertTrue(round.chests.get(0).getX() == 1);
        assertTrue(round.chests.get(0).getY() == 1);
    }

    @Test
    void SpawnTwoNoObstacle() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1, 1, 2);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(2);

        assertTrue(round.chests.size() == 2);
        assertTrue(round.chests.get(0).getX() == 1);
        assertTrue(round.chests.get(0).getY() == 1);
        assertTrue(round.chests.get(1).getX() == 1);
        assertTrue(round.chests.get(1).getY() == 2);
    }

    @Test
    void SpawnOneWall() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(2, 2, 1, 1);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(1);

        assertTrue(round.chests.size() == 1);
        assertTrue(round.chests.get(0).getX() == 1);
        assertTrue(round.chests.get(0).getY() == 1);
    }

    @Test
    void SpawnSecondOnFirst() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1, 1, 1, 1, 2);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(2);

        assertTrue(round.chests.size() == 2);
        assertTrue(round.chests.get(0).getX() == 1);
        assertTrue(round.chests.get(0).getY() == 1);
        assertTrue(round.chests.get(1).getX() == 1);
        assertTrue(round.chests.get(1).getY() == 2);
    }

    @Test
    void UpdateRoundEnded() {
        Round round = new Round();
        round.startRound();
        round.endRound();

        ChestHandler chestHandler = new ChestHandler(round);

        assertTrue(!chestHandler.update());
    }

    @Test
    void UpdateZeroChests() {
        Round round = new Round();
        round.startRound();

        ChestHandler chestHandler = new ChestHandler(round);

        assertTrue(!chestHandler.update());
    }

    @Test
    void UpdateOneChestCollectOne() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(1);

        round.player = new Player(1, 1, round);

        assertTrue(chestHandler.update());
        assertTrue(round.chests.isEmpty());
        assertTrue(round.player.getScore() == 50);

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException ex) {
        }
        assertTrue(round.chests.size() == 1);
    }

    @Test
    void UpdateOneChestCollectZeroYesKill() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 2);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(1);

        round.player = new Player(1, 1, round);

        for (int i = 0; i < 39; i++) {
            chestHandler.update();
        }

        assertTrue(chestHandler.update());
        assertTrue(round.chests.isEmpty());
        assertTrue(round.player.getScore() == 0);
    }

    @Test
    void UpdateOneChestCollectZeroNoKill() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 2);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(1);

        round.player = new Player(1, 1, round);

        assertTrue(!chestHandler.update());
        assertTrue(round.chests.size() == 1);
        assertTrue(round.player.getScore() == 0);
    }

    @Test
    void UpdateTwoChestCollectOne() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1, 1, 5);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(2);

        round.player = new Player(1, 1, round);

        assertTrue(chestHandler.update());
        assertTrue(round.chests.size() == 1);
        assertTrue(round.player.getScore() == 50);
    }

    @Test
    void UpdateTwoChestCollectZero() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(2, 1, 1, 5);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(2);

        round.player = new Player(1, 1, round);

        assertTrue(!chestHandler.update());
        assertTrue(round.chests.size() == 2);
        assertTrue(round.player.getScore() == 0);
    }

    @Test
    void UpdateTwoChestCollectZeroKillBoth() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(2, 1, 1, 5);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(2);

        round.player = new Player(1, 1, round);

        for (int i = 0; i < 39; i++) {
            chestHandler.update();
        }

        assertTrue(chestHandler.update());
        assertTrue(chestHandler.update());
        assertTrue(round.chests.isEmpty());
        assertTrue(round.player.getScore() == 0);
    }

    @Test
    void UpdateTwoChestCollectOneKillOther() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1, 1, 5);
        ChestHandler chestHandler = new ChestHandler(round, fake);
        chestHandler.spawn(2);

        round.player = new Player(1, 2, round);

        for (int i = 0; i < 39; i++) {
            chestHandler.update();
        }
        round.player.move(Direction.UP);

        assertTrue(chestHandler.update());
        assertTrue(chestHandler.update());
        assertTrue(round.chests.isEmpty());
        assertTrue(round.player.getScore() == 50);
    }
}