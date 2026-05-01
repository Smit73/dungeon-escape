

import ca.sfu.cmpt276.dungeon.Direction;
import ca.sfu.cmpt276.dungeon.GameHandler;
import ca.sfu.cmpt276.dungeon.Round;
import ca.sfu.cmpt276.dungeon.gameobject.Exit;
import ca.sfu.cmpt276.dungeon.gameobject.Player;
import ca.sfu.cmpt276.dungeon.gameobject.Trap;
import ca.sfu.cmpt276.dungeon.gameobjecthandlers.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameFlow {

    @Test
    void playerCollectsGems_thenHitsTrap_thenWinsExit() {
        Round round = new Round();
        round.player = new Player(1, 1, round);
        round.startRound();

        Random fake = new FakeRandom(2, 1, 3, 1);
        GemHandler gh = new GemHandler(round, fake);
        TrapHandler th = new TrapHandler(round);
        ExitHandler eh = new ExitHandler(round);

        // 2 gems at (2,1) and (3,1); exit at (4,1); trap at (3,1)
        round.gems.clear();
        gh.spawn(2);
        //round.gems.add(new ca.sfu.cmpt276.dungeon.gameobject.Gem(2,1,round));
       // round.gems.add(new ca.sfu.cmpt276.dungeon.gameobject.Gem(3,1,round));
        round.traps.add(new Trap(3,1,round));
        round.exits = new ArrayList<>();
        round.exits.add(new Exit(4,1, round));

        // move to (2,1) - collect gem
        round.player.move(Direction.RIGHT);
        gh.update();
        assertTrue(round.player.getScore() >= 10);

        // move to (3,1)  collect gem and hit trap so net score >= 0
        round.player.move(Direction.RIGHT);
        gh.update();
        th.update();
        assertTrue(round.player.getScore() >= 0);
        assertTrue(round.roundActive);

        // move to (4,1) win
        round.player.move(Direction.RIGHT);
        assertTrue(eh.update());
    }

    @Test
    void playerCollectsGem_thenHitsTrapAndDiesLol() {
        Round round = new Round();
        round.player = new Player(1, 1, round);
        round.startRound();

        Random fake = new FakeRandom(1, 2, 1, 3);
        GemHandler gh = new GemHandler(round, fake);
        TrapHandler th = new TrapHandler(round);

        // 1 gem at (1,2); trap at (1,3)
        gh.spawn(1);
        th.spawn(1);

        // move to (1,2) - collect gem
        round.player.move(Direction.DOWN);
        gh.update();
        assertTrue(round.player.getScore() >= 10);

        // move to (1,3) hit trap so net score < 0
        round.player.move(Direction.DOWN);
        th.update();
        assertTrue(round.player.getScore() < 0);
        assertTrue(!round.roundActive);
    }
}
