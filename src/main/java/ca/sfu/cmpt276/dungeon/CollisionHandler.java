package ca.sfu.cmpt276.dungeon;

import ca.sfu.cmpt276.dungeon.gameobject.GameObject;

/**
 * Class that handles collisions 
 * 
 * @author Alex Tran
 */
public class CollisionHandler {
    private final Round round;
    private final double INCREMENT = 0.1;

    /**
     * Functional interface for lambda expressions
     */
    @FunctionalInterface
    private interface Slope {double calculate(double x);}
    
    /**
     * Constructor for the collision handler
     * @param round the round that it handles the collision in
     */
    public CollisionHandler(Round round) {
        this.round = round;
    }

    /**
     * Casts a vertical ray from an object to another object
     * 
     * @param objectOne the game object to cast the vertical ray from
     * @param objectTwo the game object to cast the vertical ray to
     * @return true if the cast hits a wall, false if it reached its target
     */
    private boolean verticalCast(GameObject objectOne, GameObject objectTwo) {
        Direction[] availableDirections = {Direction.UP, Direction.DOWN};
        for (Direction dir : availableDirections) {
            int sightX = objectOne.getX();
            int sightY = objectOne.getY();
            while (!round.map.isWall(sightX, sightY)) {
                sightX += dir.dx;
                sightY += dir.dy;
                if (sightX == objectTwo.getX() && sightY == objectTwo.getY()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Shoots a ray from the first object to the second and checks if the ray collides with walls
     * 
     * <p>
     * find a slope function between 2 objects using y = mx + b
     * incrementally samples the slope function by x units
     * 
     * the vertical cast function is to accomodate for the undefined value if the slope is vertical (dx = 0) 
     * <p>
     * 
     * @param objectOne target to shoot the ray from
     * @param objectTwo target to shoot the ray to
     * @return true if it hits a wall, false if the ray reaches its target
     */
    public boolean rayCast(GameObject objectOne, GameObject objectTwo) throws IllegalArgumentException {
        if (objectOne == null || objectTwo == null) {
            throw new IllegalArgumentException();
        }

        int x1 = (objectOne.getX());
        int x2 = (objectTwo.getX());
        int y1 = (objectOne.getY());
        int y2 = (objectTwo.getY());
        int distanceX = x2 - x1;
        int distanceY = y2 - y1;
        double m = (double) distanceY / distanceX;
        double b = (double) y1 - (m * x1);
        Slope slope = (x) -> (m * x) + b;

        if (distanceX > 0) {
            for (double x = x1; x < x2; x += INCREMENT) {
                double y = Math.round(slope.calculate(x));
                if (round.map.isWall((int)Math.round(x), (int) y)) {
                    return true;
                }
            }
        } else if (distanceX < 0) {
            for (double x = x1; x > x2; x -= INCREMENT) {
                double y = Math.round(slope.calculate(x));
                if (round.map.isWall((int)Math.round(x), (int) y)) {
                    return true;
                }
            }
        } else {
            return verticalCast(objectOne, objectTwo);
        }
        return false;
    }

    /**
     * Checks if two objects overlap eachother
     * @param objectOne first game object
     * @param objectTwo second game object
     * @return true if the objects overlap, false otherwise
     */
    public boolean isColliding(GameObject objectOne, GameObject objectTwo) throws IllegalArgumentException {
        if (objectOne == null || objectTwo == null) {
            throw new IllegalArgumentException();
        }

        return (objectOne.getY() == objectTwo.getY() && objectOne.getX() == objectTwo.getX());
    }
}
