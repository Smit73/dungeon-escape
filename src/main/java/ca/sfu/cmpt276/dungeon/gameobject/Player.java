package ca.sfu.cmpt276.dungeon.gameobject;

import ca.sfu.cmpt276.dungeon.Direction;
import ca.sfu.cmpt276.dungeon.Round;

/**
 * class for player that is controlled by user
 * extends gameObject
 * @see GameObject
 */
public class Player extends GameObject {
    protected int score;

    /** 
     * constructor
     * calls constructor from gameObject, then also sets score to 0
     */
    public Player(int x, int y, Round round) {
        super(x, y, round);
        this.score = 0;
    }

    /**
     * method to move the player
     * given a direction enum, moves one tile
     * if tile trying to move to is wall, doesnt move
     */
    public void move(Direction dir) {
        if (!round.map.isWall(this.x+dir.dx, this.y+dir.dy)) {
            this.x += dir.dx;
            this.y += dir.dy;
        }
    }

    /**
     * implements interact method
     * only here because i have to :( 
     */
    @Override
    public void interact(Player playa){
        //does nothing - only one player
    };

    public int x() {
        return getX();
    }

    public int y() {
        return getY();
    }

    public int getScore() {
        return score;
    }

    public void addScore(int i) {
        score +=i;
    }
}

