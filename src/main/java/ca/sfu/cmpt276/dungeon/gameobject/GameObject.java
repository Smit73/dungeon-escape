package ca.sfu.cmpt276.dungeon.gameobject;

import ca.sfu.cmpt276.dungeon.Round;

// game object class - defines abstract methods/vars for game objects. could maybe use factory to make these?
/**
 * abstract class, common name between Player, Chest, Gem, Exit, Monster, Trap
 * has getters for x and y of gameObjects, a constructor, and abstract methods to be overwritten
 * @author Ben Holmes
 */
public abstract class GameObject {
    protected int y;
    protected int x;
    protected Round round;

    /**
     * constructs a new Gameobject class, setting x, y, width, height vars
     */
    public GameObject(int x, int y, Round round) {
        this.x = x;
        this.y = y;
        this.round = round;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * abstract class for what to do when interacting with player
     * to be implemented by each inheritor class
     */
    public abstract void interact(Player playa);

}

