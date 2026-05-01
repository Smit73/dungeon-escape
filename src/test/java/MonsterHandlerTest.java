import ca.sfu.cmpt276.dungeon.*;
import ca.sfu.cmpt276.dungeon.gameobject.Monster;
import ca.sfu.cmpt276.dungeon.gameobject.Player;
import ca.sfu.cmpt276.dungeon.gameobjecthandlers.MonsterHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MonsterHandlerTest {

    @Test
    void SpawnZero() {
        Round round = new Round();
        round.startRound();

        MonsterHandler monsterHandler = new MonsterHandler(round);
        monsterHandler.spawn(0);

        assertTrue(round.chests.isEmpty());
    }

    @Test
    void SpawnOneNoObstacle() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 0);
        MonsterHandler monsterHandler = new MonsterHandler(round, fake);
        monsterHandler.spawn(1);

        assertTrue(round.monsters.size() == 1);
        assertTrue(round.monsters.get(0).getX() == 11);
        assertTrue(round.monsters.get(0).getY() == 10);
    }

    @Test
    void SpawnTwoNoObstacle() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(0, 0, 1, 0);
        MonsterHandler monsterHandler = new MonsterHandler(round, fake);
        monsterHandler.spawn(2);

        assertTrue(round.monsters.size() == 2);
        assertTrue(round.monsters.get(0).getX() == 10);
        assertTrue(round.monsters.get(0).getY() == 10);
        assertTrue(round.monsters.get(1).getX() == 11);
        assertTrue(round.monsters.get(1).getY() == 10);
    }

    @Test
    void SpawnOneWall() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 1, 1, 0);
        MonsterHandler monsterHandler = new MonsterHandler(round, fake);
        monsterHandler.spawn(1);

        assertTrue(round.monsters.size() == 1);
        assertTrue(round.monsters.get(0).getX() == 11);
        assertTrue(round.monsters.get(0).getY() == 10);
    }

    @Test
    void SpawnSecondOnFirst() {
        Round round = new Round();
        round.startRound();

        Random fake = new FakeRandom(1, 0, 1, 0, 0, 0);
        MonsterHandler monsterHandler = new MonsterHandler(round, fake);
        monsterHandler.spawn(2);

        assertTrue(round.monsters.size() == 2);
        assertTrue(round.monsters.get(0).getX() == 11);
        assertTrue(round.monsters.get(0).getY() == 10);
        assertTrue(round.monsters.get(1).getX() == 10);
        assertTrue(round.monsters.get(1).getY() == 10);
    }

    @Test
    void UpdateEndedRound() {
        Round round = new Round();
        round.startRound();

        MonsterHandler monsterHandler = new MonsterHandler(round);
        monsterHandler.spawn(1);

        round.endRound();

        assertTrue(!monsterHandler.update());
    }

    @Test
    void UpdateNoMonsters() {
        Round round = new Round();
        round.startRound();

        MonsterHandler monsterHandler = new MonsterHandler(round);

        assertTrue(!monsterHandler.update());
    }

    @Test
    void MonstersMovementIfMoveCap() {
        Round round = new Round();
        round.startRound();

        MonsterHandler monsterHandler = new MonsterHandler(round);
        monsterHandler.spawn(2);

        round.player = new Player(1, 1, round);

        round.monsters.get(0).setMoveCap(true);
        round.monsters.get(1).setMoveCap(false);

        monsterHandler.update();

        assertTrue(round.monsters.get(0).getMoveCap() == false);
        assertTrue(round.monsters.get(1).getMoveCap() == true);
    }
}