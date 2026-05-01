package ca.sfu.cmpt276.dungeon;

/**
 * Enum for the four movement directions, each with a delta offset. This way we know how much to move each thing.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    /** Horizontal delta. */
    public final int dx;
    /** Vertical delta. */
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}