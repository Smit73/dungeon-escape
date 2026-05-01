package ca.sfu.cmpt276.dungeon;

import java.util.ArrayList;

import ca.sfu.cmpt276.dungeon.gameobject.Chest;
import ca.sfu.cmpt276.dungeon.gameobject.Exit;
import ca.sfu.cmpt276.dungeon.gameobject.GameObject;
import ca.sfu.cmpt276.dungeon.gameobject.Gem;
import ca.sfu.cmpt276.dungeon.gameobject.Monster;
import ca.sfu.cmpt276.dungeon.gameobject.Player;
import ca.sfu.cmpt276.dungeon.gameobject.Trap;

/**
 * Class for controlling the current round
 * @author Alex Tran
 */
public class Round {
    public Player player;
    public ArrayList<Monster> monsters;
    public ArrayList<Trap> traps;
    public ArrayList<Gem> gems;
    public ArrayList<Chest> chests;
    public ArrayList<Exit> exits;
    public ca.sfu.cmpt276.dungeon.Map map;
    public boolean roundActive = false;
    public CollisionHandler collisionHandler;
    public Pathfinder pathfinder;

    private final GameObject[][] entityGrid;

    /**
     * Constructor for initializing round data
     */
    public Round() {
        monsters = new ArrayList<>();
        traps = new ArrayList<>();
        gems = new ArrayList<>();
        chests = new ArrayList<>();
        exits = new ArrayList<>();
        map =  new ca.sfu.cmpt276.dungeon.Map(SpawnBounds.MAP_X+1, SpawnBounds.MAP_Y+1);
        collisionHandler = new CollisionHandler(this);
        pathfinder = new Pathfinder(this);

        entityGrid = new GameObject[SpawnBounds.MAP_X][SpawnBounds.MAP_Y];
    }

    public void addEntity(int x, int y, GameObject gameObject) {
        entityGrid[x][y] = gameObject;
    }

    public void removeEntity(int x, int y) {
        entityGrid[x][y] = null;
    }

    public void moveEntity(int x1, int y1, int x2, int y2) {
        GameObject gameObject = getEntity(x1, y1);
        removeEntity(x1, y1);
        addEntity(x2, y2, gameObject);
    }

    public GameObject getEntity(int x, int y) {
        return entityGrid[x][y];
    }

    /**
     * Starts the round
     */
    public void startRound() {
        roundActive = true;
        System.out.println("Starting Round.");
        map.createLevel1();
    }

    /**
     * Ends the round
     */
    public void endRound() {
        if (!roundActive) return; // prevent repeated calls
        roundActive = false;
        System.out.println("Ending Round.");
    }
}