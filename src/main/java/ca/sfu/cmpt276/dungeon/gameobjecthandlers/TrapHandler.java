package ca.sfu.cmpt276.dungeon.gameobjecthandlers;


import ca.sfu.cmpt276.dungeon.gameobject.*;
import ca.sfu.cmpt276.dungeon.*;

import java.util.Random;

/**
 * extends gameobjecthandler
 * Manages all monsters in the game.
 */
public class TrapHandler extends GameObjectHandler<Trap> {

    /**
     * Constructs a TrapHandler linked to the active round.
     * @param round the current game round
     */
    public TrapHandler(Round round) {
        super(round);
    }

    public TrapHandler(Round round, Random fake) {
        super(round, fake);
    }

    /**
     * Adds starting traps to the round.
     */
    @Override
    public void spawn(int count) {
        int x;
        int y;
        for (int i = 0; i < count; i++) {
            while (true) {
                x = rng.nextInt(SpawnBounds.TRAP_UPPER_X - SpawnBounds.TRAP_LOWER_X + 1) + SpawnBounds.TRAP_LOWER_X;
                y = rng.nextInt(SpawnBounds.TRAP_UPPER_Y - SpawnBounds.TRAP_LOWER_Y + 1) + SpawnBounds.TRAP_LOWER_Y;
                if (!round.map.isWall(x, y) && round.getEntity(x, y) == null) {
                    break;
                }
            }
            Trap trap = new Trap(x, y, round);
            gameObjects.add(trap);
            round.addEntity(x, y, trap);
        }
        round.traps.addAll(super.gameObjects);
    }
}
