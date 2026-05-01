package ca.sfu.cmpt276.dungeon.gameobjecthandlers;

import ca.sfu.cmpt276.dungeon.gameobject.*;
import ca.sfu.cmpt276.dungeon.*;

import java.util.Random;


/**
 * extends GameObjectHandler
 * Manages all Chests in the game.
 */
public class ChestHandler extends GameObjectHandler<Chest> {

    /**
     * Constructs a ChestHandler linked to the active round.
     * @param round the current game round
     */
    public ChestHandler(Round round) {
        super(round);
    }

    public ChestHandler(Round round, Random fake) {
        super(round, fake);
    }

    /**
     * Adds starting chests to the round.
     */
    @Override
    public void spawn(int count) {
        int x;
        int y;
        for (int i = 0; i < count; i++) {
            while (true) {
                x = rng.nextInt(SpawnBounds.CHEST_UPPER_X - SpawnBounds.CHEST_LOWER_X + 1) + SpawnBounds.CHEST_LOWER_X;
                y = rng.nextInt(SpawnBounds.CHEST_UPPER_Y - SpawnBounds.CHEST_LOWER_Y + 1) + SpawnBounds.CHEST_LOWER_Y;
                if (!round.map.isWall(x, y) && round.getEntity(x, y) == null) {
                    break;
                }
            }
            Chest chest = new Chest(x, y, round);
            gameObjects.add(chest);
            round.addEntity(x, y, chest);
        }
        round.chests.addAll(super.gameObjects);
    }

    /**
     * overrides update method
     * adds removal of chest when collected
     */
    @Override
    public boolean update() {
        if (!round.roundActive) return false;
        Player player = round.player;

        for (Chest c : super.gameObjects) {
            if ((c.getY() == player.getY() && c.getX() == player.getX())) {
                c.interact(player);
                gameObjects.remove(c);
                round.chests.remove(c);
                round.removeEntity(c.getX(), c.getY());
                ChestThread cThr = new ChestThread(this);
                Thread thr = new Thread(cThr);
                thr.start();
                return true;
            }
            if (c.getKillTimer() >= SpawnBounds.CHEST_TICK_LEN) {
                gameObjects.remove(c);
                round.chests.remove(c);
                round.removeEntity(c.getX(), c.getY());
                ChestThread cThr = new ChestThread(this);
                Thread thr = new Thread(cThr);
                thr.start();
                return true;
            }
        }
        return false;
    }
}
