package ca.sfu.cmpt276.dungeon;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Contains global constants for scoring and gameplay balance.
 */
public final class SpawnBounds {

    public static final int MAP_X = 23;
    public static final int MAP_Y = 15;

    public static final int CHEST_LOWER_X = 0;
    public static final int CHEST_LOWER_Y = 0;
    public static final int CHEST_UPPER_X = MAP_X;
    public static final int CHEST_UPPER_Y = MAP_Y;
    public static final int CHEST_AMOUNT = 2;
    public static final int CHEST_TICK_LEN = 40;
    public static ReentrantLock chestLock = new ReentrantLock();

    public static final int EXIT_LOWER_X = 15;
    public static final int EXIT_LOWER_Y = 5;
    public static final int EXIT_UPPER_X = MAP_X;
    public static final int EXIT_UPPER_Y = MAP_Y;
    public static final int EXIT_AMOUNT = 1;
    public static final int[][] EXIT_LOCATION = {{15, 7}, {16, 8}}; // 2nd location here for testing purposes

    public static final int GEM_LOWER_X = 0;
    public static final int GEM_LOWER_Y = 0;
    public static final int GEM_UPPER_X = MAP_X;
    public static final int GEM_UPPER_Y = 16;
    public static final int GEM_AMOUNT = 4;

    public static final int MONSTER_LOWER_X = 10;
    public static final int MONSTER_LOWER_Y = 10;
    public static final int MONSTER_UPPER_X = MAP_X-1;
    public static final int MONSTER_UPPER_Y = MAP_Y-1;
    public static final int MONSTER_AMOUNT = 5;

    public static final int TRAP_LOWER_X = 0;
    public static final int TRAP_LOWER_Y = 0;
    public static final int TRAP_UPPER_X = MAP_X;
    public static final int TRAP_UPPER_Y = MAP_Y;
    public static final int TRAP_AMOUNT = 2;

    public static final int PLAYER_X = 1;
    public static final int PLAYER_Y = 1;

}
