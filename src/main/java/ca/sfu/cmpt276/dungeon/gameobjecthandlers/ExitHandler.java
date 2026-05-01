package ca.sfu.cmpt276.dungeon.gameobjecthandlers;

import java.util.Random;

import ca.sfu.cmpt276.dungeon.gameobject.*;
import ca.sfu.cmpt276.dungeon.*;


/**
 * extends gameobjecthandler
 * manages all exits in the game
 */
public class ExitHandler extends GameObjectHandler<Exit> {

    /**
     * Constructs a ExitHandler linked to the active round.
     * @param round the current game round
     */
    public ExitHandler(Round round) {
        super(round);
    }

    public ExitHandler(Round round, Random fake) {
        super(round, fake);
    }

    /**
     * spawns starting exits into the round.
     */
    @Override
    public void spawn(int count) {
        int x;
        int y;
        for (int i = 0; i < count; i++) {
            x = SpawnBounds.EXIT_LOCATION[i][0];//rng.nextInt(SpawnBounds.EXIT_UPPER_X - SpawnBounds.EXIT_LOWER_X + 1) + SpawnBounds.EXIT_LOWER_X;
            y = SpawnBounds.EXIT_LOCATION[i][1];//rng.nextInt(SpawnBounds.EXIT_UPPER_Y - SpawnBounds.EXIT_LOWER_Y + 1) + SpawnBounds.EXIT_LOWER_Y;
            Exit exit = new Exit(x, y, round);
            gameObjects.add(exit);
            round.addEntity(x, y, exit);
        }
        round.exits.addAll(super.gameObjects);
    }

    /** @return true iff the player is on any exit. */
    @Override
    public boolean update() {
        CollisionHandler c = new CollisionHandler(round);
        if (!round.roundActive || round.player == null || round.exits == null) return false;
        for (Exit e : round.exits) {
            if (c.isColliding(e, round.player)) return true;
        }
        return false;
    }
}
