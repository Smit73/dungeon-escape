import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import ca.sfu.cmpt276.dungeon.Round;
import ca.sfu.cmpt276.dungeon.gameobject.Monster;
import ca.sfu.cmpt276.dungeon.gameobject.Player;

class MonsterHTest {

    
    @Test
    void handleMonster_endsRoundOnCollision() {

        Round round = new Round();
        round.player = new Player(5, 5, round);
        round.startRound();

        Monster monster = new Monster(5, 5, round); // same tile as player


        monster.handleMonster();


        assertFalse(round.roundActive);
    }
}
