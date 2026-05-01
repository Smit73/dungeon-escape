package ca.sfu.cmpt276.dungeon.gameobjecthandlers;


import ca.sfu.cmpt276.dungeon.gameobject.*;
import ca.sfu.cmpt276.dungeon.*;

import java.util.Random;


/**
 * extends gameobjectshandler
 * Manages all monsters in the game.
 * Handles movement, collision with player, and round-level events.
 */
public class MonsterHandler extends GameObjectHandler<Monster>{    

    /**
     * Constructs a MonsterHandler linked to the active round.
     * @param round the current game round
     */
    public MonsterHandler(Round round) {
        super(round);
    }

    public MonsterHandler(Round round, Random fake) {
        super(round, fake);
    }

    /**
     * Adds starting monsters to the round.
     */
    @Override
    public void spawn(int count) {
        int x;
        int y;
        for (int i = 0; i < count; i++) {
            while (true) {
                x = rng.nextInt(SpawnBounds.MONSTER_UPPER_X - SpawnBounds.MONSTER_LOWER_X + 1) + SpawnBounds.MONSTER_LOWER_X;
                y = rng.nextInt(SpawnBounds.MONSTER_UPPER_Y - SpawnBounds.MONSTER_LOWER_Y + 1) + SpawnBounds.MONSTER_LOWER_Y;
                if (!round.map.isWall(x, y) && round.getEntity(x, y) == null) {
                    break;
                }
            }
            Monster monster = new Monster(x, y, round);
            gameObjects.add(monster);
            round.addEntity(x, y, monster);
        }
        round.monsters.addAll(super.gameObjects);
    }

    /**
     * overrides update in gameobjecthandler
     * Updates all monsters each tick.
     * Handles movement and checks for collision with the player.
     */
    @Override
    public boolean update() {
        if (!round.roundActive) return false;


        for (Monster m : super.gameObjects) {
            if (m.getMoveCap()) {
                m.handleMonster();
                m.setMoveCap(false);
            } else {
                m.setMoveCap(true);
            }
        }
        return false;
    }
}
