
import ca.sfu.cmpt276.dungeon.Round;
import ca.sfu.cmpt276.dungeon.gameobject.Exit;
import ca.sfu.cmpt276.dungeon.gameobject.Player;
import ca.sfu.cmpt276.dungeon.gameobjecthandlers.ExitHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ExitHandlerTest {

    @Test
    void reachingExit_returnsTrue() {
        Round r = new Round();
        r.player = new Player(1, 1, r);
        r.exits = new ArrayList<>();
        r.exits.add(new Exit(1,1,r));
        r.startRound();
        ExitHandler eh = new ExitHandler(r);
        assertTrue(eh.update()); // designed update() to return true when on exit
    }

    @Test
    void notReachingExit_notReturnsTrue() {
        Round r = new Round();
        r.player = new Player(1, 1, r);
        r.exits = new ArrayList<>();
        r.exits.add(new Exit(1,2,r));
        r.startRound();
        ExitHandler eh = new ExitHandler(r);
        assertTrue(!eh.update()); // designed update() to return true when on exit
    }

    @Test
    void roundEnded() {
        Round r = new Round();
        r.player = new Player(1, 1, r);
        r.exits = new ArrayList<>();
        r.exits.add(new Exit(1,1,r));
        r.startRound();
        ExitHandler eh = new ExitHandler(r);
        r.endRound();
        assertTrue(!eh.update()); 
    }

    @Test
    void noPlayer() {
        Round r = new Round();
        r.exits = new ArrayList<>();
        r.exits.add(new Exit(1,1,r));
        r.startRound();
        ExitHandler eh = new ExitHandler(r);
        assertTrue(!eh.update()); 
    }

    @Test
    void noExits() {
        Round r = new Round();
        r.player = new Player(1, 1, r);
        r.startRound();
        r.exits = null;
        ExitHandler eh = new ExitHandler(r);
        assertTrue(!eh.update()); 
    }

    @Test
    void SpawnZero() {
        Round round = new Round();
        round.startRound();

        ExitHandler exitHandler = new ExitHandler(round);
        exitHandler.spawn(0);

        assertTrue(round.exits.isEmpty());
    }

    @Test
    void SpawnOne() {
        Round round = new Round();
        round.startRound();

        ExitHandler exitHandler = new ExitHandler(round);
        exitHandler.spawn(1);

        assertTrue(round.exits.size() == 1);
        assertTrue(round.exits.get(0).getX() == 15);
        assertTrue(round.exits.get(0).getY() == 7);
    }

    @Test
    void SpawnTwo() {
        Round round = new Round();
        round.startRound();

        ExitHandler exitHandler = new ExitHandler(round);
        exitHandler.spawn(2);

        assertTrue(round.exits.size() == 2);
        assertTrue(round.exits.get(0).getX() == 15);
        assertTrue(round.exits.get(0).getY() == 7);
        assertTrue(round.exits.get(1).getX() == 16);
        assertTrue(round.exits.get(1).getY() == 8);
    }

}