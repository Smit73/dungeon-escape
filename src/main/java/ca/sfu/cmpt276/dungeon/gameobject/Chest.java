package ca.sfu.cmpt276.dungeon.gameobject;

import static ca.sfu.cmpt276.dungeon.Rules.CHEST_SCORE;
import ca.sfu.cmpt276.dungeon.Round;


/**
 * class for chest that can be collected by player
 * extends gameObject
 * @see GameObject
 */
public class Chest extends GameObject {

    int killTimer;

    /**
     * constructor
     * calls gameObject constructor
     */
    public Chest(int x, int y, Round round) {
        super(x, y, round);
        this.killTimer = 0;
    }

    /**
     * implements interact class from gameObject
     * when interacts with player, increase the players score
     */
    @Override
    public void interact(Player playa){
        playa.score += CHEST_SCORE;
        killTimer = 0;
    };

    public int getKillTimer() {
        killTimer ++;
        return killTimer;
    }
}