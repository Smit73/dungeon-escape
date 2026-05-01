package ca.sfu.cmpt276.dungeon.gameobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.sfu.cmpt276.dungeon.Direction;
import ca.sfu.cmpt276.dungeon.Pathfinder;
import ca.sfu.cmpt276.dungeon.Position;
import ca.sfu.cmpt276.dungeon.Round;

/**
 * Class that represents a monster entity.
 * This class implements the behaviour and functionality of a monster
 * 
 * <p>
 * Includes implementations for movement, player searching, and path finding
 * <p>
 * @see GameObject
 * @author Alex Tran
 */
public class Monster extends GameObject {
    private MonsterState state;
    private Direction facing;
    private int faceIndex;
    private boolean move = true;

    private final Pathfinder pathfinder;
    private int pathIterator;
    private List<Position> playerPath;
    
    private final Random random;

    /**
     * An enum class representing monster states
     */
    private enum MonsterState {
        ROAMING,
        CHASING,
    }

    /**
     * Constructor for monster class
     */
    public Monster(int x, int y, Round round) {
        super(x, y, round);
        state = MonsterState.ROAMING;
        facing = Direction.DOWN;
        random = new Random();
        pathfinder = round.pathfinder;
    }

    /**
     * Constructs a path to the player 
     */
    private void constructPlayerPath() {
        pathfinder.setStartingPosition(x, y);
        pathfinder.setGoalPosition(round.player.getX(), round.player.getY());
        playerPath = pathfinder.getPath();
        pathIterator = 0;
    }

    /**
     * Main function of the monster class, handles the behaviour of the monster
     */
    public void handleMonster() {
        //  collision BEFORE movement
        collisionCheck();
        switch (state) {
            case MonsterState.ROAMING -> {
                ArrayList<Direction> availableDirections = getAvailableDirections(facing);
                Direction randomDirection = getRandomDirection(availableDirections);
                if(!searchPlayer()) {
                    move(randomDirection);
                }
            }
            case MonsterState.CHASING -> {
                if (playerPath == null) {
                    break;
                }
                if (pathIterator < playerPath.size()) {
                    move(playerPath.get(pathIterator).x,playerPath.get(pathIterator).y);
                    pathIterator++;
                } else {
                    state = MonsterState.ROAMING;
                }
                searchPlayer();
            }
        }
        // collision AFTER movement (normal chasing hit)
        collisionCheck();
    }

    /**
     * Checks for player collision
     */
    private void collisionCheck() {
        if (round.collisionHandler.isColliding(this, round.player)) {
            interact(round.player);
            round.endRound();
        }
    }

    /**
     * Moves the monster a step towards the given x-y coordinates
     * @param x target x position
     * @param y target y position
     */
    public void move(int x, int y) {
        int dx = Integer.compare(x, this.x);
        int dy = Integer.compare(y, this.y);

        Direction dir = null;
        if (Math.abs(dx) > Math.abs(dy)) {
            dir = dx > 0 ? Direction.RIGHT : Direction.LEFT;
        } else if (dy != 0) {
            dir = dy > 0 ? Direction.DOWN : Direction.UP;
        }

        if (dir != null) {
            move(dir);
        }
    }

    /**
     * method to move a monster
     * given a direction enum, moves one tile
     * if tile trying to move to is wall, doesnt move
     * 
     * @param dir the direction to move in∂
     */
    public void move(Direction dir) {
        facing = dir;
        if (canMove(dir)) {
            round.removeEntity(this.x, this.y);
            this.x += dir.dx;
            this.y += dir.dy;
            round.addEntity(this.x, this.y, this);
        }
    }

    /**
     * Checks of the monster can move in the given direction
     * @param dir a given direction
     * @return true if it can move in that direction, false otherwise
     */
    private boolean canMove(Direction dir) {
        return !round.map.isWall(this.x+dir.dx, this.y+dir.dy);
    }

    /**
     * Gets the direction scope of the 
     * @param currentFacing the direction the monster is currently facing
     * @return a list of the direction scope
     */
    private Direction[] getDirectionScope(Direction currentFacing) {
        Direction[] directions = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
        for (int i = 0; i < directions.length; i++) {
            if (directions[i] == currentFacing) {
                faceIndex = i;
                return new Direction[] {directions[i], directions[Math.floorMod((i-1), directions.length)], directions[(i+1) % directions.length]};
            }
        }
        return new Direction[]{};
    }

    /**
     * Checks the direction scope to get available moves
     * @param directionScope the direction scope 
     * @return an arraylist of available moves
     */
    private ArrayList<Direction> checkDirectionScope(Direction[] directionScope) {
        ArrayList<Direction> availableMoves = new ArrayList<>();
        for (Direction directionScope1 : directionScope) {
            if (canMove(directionScope1)) {
                availableMoves.add(directionScope1);
            }
        }
        return availableMoves;
    }

    /**
     * Gets a list of available directions that the monster can move in
     * @param facing the direction that the monster is facing
     * @return a list of available directions
     */
    private ArrayList<Direction> getAvailableDirections(Direction currentFacing) {
        Direction[] directions = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};

        // Getting the scope of directions based on what direction the monster is facing (e.g. monster is facing LEFT its direction scope should be LEFT, UP, DOWN)
        Direction[] directionScope = getDirectionScope(currentFacing);
        ArrayList<Direction> availableMoves = checkDirectionScope(directionScope);

        // If there are no available moves (meaning we ran into a dead end), we set the face to its opposite direction
        if (availableMoves.isEmpty()) {
            availableMoves.add(directions[(faceIndex+2) % directions.length]);
        }

        return availableMoves;
    }

    /**
     * Gets a random direction from a list of available directions
     */
    private Direction getRandomDirection(ArrayList<Direction> availableDirections) {
        int randomIndex = random.nextInt(availableDirections.size());
        return availableDirections.get(randomIndex);
    }
    
    /**
     * Searches for the player using a raycast to detect the player
     * @return true if player is found, false otherwise
     */
    private boolean searchPlayer() {
        boolean isVisible = !round.collisionHandler.rayCast(this, round.player);
        if (isVisible) {
            state = MonsterState.CHASING;
            constructPlayerPath();
            return true;
        }
        return false;
    }

    /**
     * implements interact class from gameObject
     * when interacts with player, 
     * set players score to -1
     * end round **TO BE IMPLEMENTED**
     */
    @Override
    public void interact(Player playa){
        // maybe play a funny little animation
        round.endRound();
    };

    /**
     * Getter for the move cooldown
     * @return true if the monster can move, false otherwise
     */
    public boolean getMoveCap() {return this.move;}

    /**
     * Setter for the move cooldown
     * @param set true or false
     */
    public void setMoveCap(boolean set) {this.move = set;}
}