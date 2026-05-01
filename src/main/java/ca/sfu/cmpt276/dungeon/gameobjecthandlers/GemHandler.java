package ca.sfu.cmpt276.dungeon.gameobjecthandlers;


import ca.sfu.cmpt276.dungeon.gameobject.*;
import ca.sfu.cmpt276.dungeon.*;

import java.util.Random;


/**
 * extneds gameobjecthandler
 * Manages all monsters in the game.
 */
public class GemHandler extends GameObjectHandler<Gem> {

    /**
     * Constructs a GemHandler linked to the active round.
     * @param round the current game round
     */
    public GemHandler(Round round) {
        super(round);
    }

    public GemHandler(Round round, Random fake) {
        super(round, fake);
    }

    /**
     * Adds starting gems to the round.
     */
    @Override
    public void spawn(int count) {
        int x;
        int y;
        for (int i = 0; i < count; i++) {
            while (true) {
                x = rng.nextInt(SpawnBounds.GEM_UPPER_X - SpawnBounds.GEM_LOWER_X + 1) + SpawnBounds.GEM_LOWER_X;
                y = rng.nextInt(SpawnBounds.GEM_UPPER_Y - SpawnBounds.GEM_LOWER_Y + 1) + SpawnBounds.GEM_LOWER_Y;
                if (!round.map.isWall(x, y) && round.getEntity(x, y) == null) {
                    break;
                }
            }
            Gem gem = new Gem(x, y, round);
            gameObjects.add(gem);
            round.addEntity(x, y, gem);
        }
        round.gems.addAll(super.gameObjects);
    }
}
