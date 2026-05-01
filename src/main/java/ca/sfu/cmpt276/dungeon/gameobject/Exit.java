package ca.sfu.cmpt276.dungeon.gameobject;

import ca.sfu.cmpt276.dungeon.Round;

/**
 * class for exit that can be used by player
 * extends gameObject
 * @see GameObject
 */
public class Exit extends GameObject {

    /**
     * constructor
     * calls gameObject constructor
     */
    public Exit(int x, int y, Round round) {
        super(x, y, round);
    }

    /**
     * implements interact method from gameObject
     * when interacts with player,
     * end current round **TO BE IMPLEMENTED**
     * display victory screen **TO BE IMPLEMENTED**
     */
    @Override
    public void interact(Player playa){
        // play funny luh animation
        round.endRound();
        // display vicroy screen! (or maybe have endRound take a parameter - victory/defeat)
    };
}