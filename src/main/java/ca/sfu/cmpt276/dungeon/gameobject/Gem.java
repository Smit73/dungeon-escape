package ca.sfu.cmpt276.dungeon.gameobject;

import java.util.Random;

import static ca.sfu.cmpt276.dungeon.Rules.GEM_SCORE;
import ca.sfu.cmpt276.dungeon.Round;

/**
 * class for gem that can be collected by player
 * extends gameObject
 * @see GameObject
 */
public class Gem extends GameObject {

    private Random rng = new Random();

    /**
     * constructor
     * calls gameObject constructor
     */
    public Gem(int x, int y, Round round) {
        super(x, y, round);
    }

    //testing constructor
    public Gem(int x, int y, Round round, Random rand) {
        super(x, y, round);
        rng = rand;
    }

    /**
     * implements interact class from gameObject
     * when interacts with player, increase the players score
     * moves gem to new random place, simulating gem being collected and new gem appearing 
     */
    @Override
    public void interact(Player playa){
        playa.score += GEM_SCORE;

        int gemX;
        int gemY;
        while (true) {
            gemX = rng.nextInt(round.map.getWidth());
            gemY = rng.nextInt(round.map.getHeight());
            if (!round.map.isWall(gemX, gemY) && round.getEntity(gemX, gemY) == null) {
                break;
            }
        }

        // play animation of gem disappear and new one appear
        round.moveEntity(this.x, this.y, gemX, gemY);
        this.x = gemX;
        this.y = gemY;
    }

}