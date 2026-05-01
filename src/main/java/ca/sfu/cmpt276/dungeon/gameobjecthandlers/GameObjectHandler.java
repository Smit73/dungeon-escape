package ca.sfu.cmpt276.dungeon.gameobjecthandlers;

import ca.sfu.cmpt276.dungeon.gameobject.*;
import ca.sfu.cmpt276.dungeon.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * abstract class for handling gameobjects
 * has generic list of gameobjects
 */
public abstract class GameObjectHandler<T extends GameObject> {

    protected List<T> gameObjects = new ArrayList<>();
    protected final Round round; // reference to the current round
    protected Random rng = new Random();
    

    /**
     * Constructs a MonsterHandler linked to the active round.
     *
     * @param round the current game round
     */
    public GameObjectHandler(Round round) {
        this.round = round;
    }

    public GameObjectHandler(Round round, Random fake) {
        this.round = round;
        this.rng = fake;
    }

    /**
     * abstract method for spawning a number of gameobjects
     * to be implemented by extenders
     * @param count how many to spawn
     */
    public abstract void spawn(int count);

    /**
     * updates all gameobjects
     * checks if colliding with player, if so calls interact for corresponding gameobject
     */
    public boolean update() {
        if (!round.roundActive) return false;
        Player player = round.player;

        for (T x : gameObjects) {
            if (round.collisionHandler.isColliding(x, player)) {
                x.interact(player);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all active gameobjects of type T.
     */
    public List<T> get() {
        return gameObjects;
    }



    /**
     * Clears the list.
     */
    public void clear() {
        gameObjects.clear();
    }
}
