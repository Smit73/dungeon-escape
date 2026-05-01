package ca.sfu.cmpt276.dungeon;

/**
 * Contains global constants for scoring and gameplay balance.
 */
public final class Rules {
    /** Score gained per gem. */
    public static final int GEM_SCORE = 10;
    /** Score lost when stepping on a trap. */
    public static final int TRAP_PENALTY = 20;
    /** Score bonus for collecting a chest. */
    public static final int CHEST_SCORE = 50;

    /** Prevent instantiation. */
    private Rules() { }
}
