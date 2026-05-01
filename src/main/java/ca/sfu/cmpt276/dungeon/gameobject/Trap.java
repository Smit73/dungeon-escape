package ca.sfu.cmpt276.dungeon.gameobject;

import java.util.Random;

import static ca.sfu.cmpt276.dungeon.Rules.TRAP_PENALTY;
import ca.sfu.cmpt276.dungeon.Round;

/**
 * class for trap that can punish player
 * extends gameObject
 * @see GameObject
 */
public class Trap extends GameObject {

    private Random rng = new Random();

    /**
     * constructor
     * calls gameObject constructor
     */
    public Trap(int x, int y, Round round) {
        super(x, y, round);
    }

    //testing constructor
    public Trap(int x, int y, Round round, Random fake) {
        super(x, y, round);
        rng = fake;
    }

    /**
     * implements interact class from gameObject
     * when interacts with player, decrease the players score
     * also trap must disappear **TO BE IMPLEMENTED**
     * maybe have new trap spawn??? **TO BE IMPLEMENTED**
     * do score check to see if player dead **TO BE IMPLEMENTED**
     * if so end round **TO BE IMPLEMENTED**
     */
    @Override
    public void interact(Player playa){
        playa.score -= TRAP_PENALTY;

        int trapX;
        int trapY;
        while (true) {
            trapX = rng.nextInt(round.map.getWidth());
            trapY = rng.nextInt(round.map.getHeight());
            if (!round.map.isWall(trapX, trapY) && round.getEntity(trapX, trapY) == null) {
                break;
            }
        }

        // play animation of gem disappear and new one appear
        round.moveEntity(this.x, this.y, trapX, trapY);
        this.x = trapX;
        this.y = trapY;
    }
}