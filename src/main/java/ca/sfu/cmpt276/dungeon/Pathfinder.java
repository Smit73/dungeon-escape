package ca.sfu.cmpt276.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A pathfinding algorithm class used for finding a path between 2 positions
 * 
 * <p>
 * This classes implements a Bread-First-Search (BFS) algorithm with an iterative approach
 * <p>
 * 
 * @author Alex Tran
 */
public class Pathfinder {
    private ArrayList<CellInfo> queue;
    private Map<String, Position> visited;
    private final Round round;

    private Position startPos;
    private Position goalPos;

    private final int MAX_ITER = 100000;

    /**
     * Class to represent a cell on the map grid
     */
    private class CellInfo {
        public Position currentPos;
        public Position lastPos;

        /**
         * Constructs a cell with the current position and last position 
         * @param currentPos current position
         * @param lastPos last position 
         */
        public CellInfo(Position currentPos, Position lastPos) {
            this.currentPos = currentPos;
            this.lastPos = lastPos;
        }
    }

    /**
     * Constructs a path finder given the starting and goal position and the round to construct the path in
     * @param round the round
     */
    public Pathfinder(Round round) {
        this.round = round;
    }

    /**
     * Sets the starting position of the pathfinder
     * @param x the x cooridinate of the position
     * @param y the y cooridinate of the position
     */
    public void setStartingPosition(int x, int y) {
        this.startPos = new Position(x, y);
    }

    /**
     * Sets the goal position of the pathfinder
     * @param x the x cooridinate of the position
     * @param y the y coordinate of the position
     */
    public void setGoalPosition(int x, int y) {
        this.goalPos = new Position(x, y);
    }

    /**
     * Constructs a path using BFS and returns a list of the found path, if a path does not exist it will return an empty list
     * @return a list of coordinates leading to the end position
     */
    public List<Position> getPath() {
        visited = new HashMap<>();
        queue = new ArrayList<>();
        queue.add(new CellInfo(startPos, null));
        
        if (!constructPath()) {
            return new ArrayList<>();
        }
        return backTrackPath();
    }

    /**
     * constructs a path to the goal position 
     * @return true if target is found, false otherwise
     */
    private boolean constructPath() {
        int iterations = 0;
        while (!queue.isEmpty()) {
            CellInfo cellInfo = queue.removeFirst();
            if (checkCell(cellInfo)) {
                break;
            }
            iterations += 1;
            if (iterations >= MAX_ITER) {
                return false;
            }
        }
        return true;
    }

    /**
     * Backtracks from the goal position to construct an ordered path
     * @return an arraylist of the path to the target
     */
    private ArrayList<Position> backTrackPath() {
        Position currentPos = goalPos;
        ArrayList<Position> backTrackedPath = new ArrayList<>();
        while (visited.containsKey(currentPos.toString()) && visited.get(currentPos.toString()) != null) {
            if (currentPos != null) {
                backTrackedPath.add(currentPos);
            }
            currentPos = visited.get(currentPos.toString());
        }
        Collections.reverse(backTrackedPath);
        return backTrackedPath;
    }

    /**
     * Checks the cell if it is valid
     * <p>
     * if the position cant be moved into or if the position has been visited it is not a valid cell
     * if the position is the end position a path has been found 
     * <p>
     * @param cellInfo the cell to check
     * @return true if the end position is found, false otherwise
     */
    private boolean checkCell(CellInfo cellInfo) {
        Position currentPos = cellInfo.currentPos;
        Position lastPos = cellInfo.lastPos;
        if (!canMove(currentPos)) {
            return false;
        }
        if (visited.containsKey(currentPos.toString())) {
            return false;
        }
        visited.put(currentPos.toString(), lastPos);
        if (currentPos.x == goalPos.x && currentPos.y == goalPos.y) {
            return true;
        }
        queue.add(new CellInfo(new Position(currentPos.x, currentPos.y-1), currentPos));
        queue.add(new CellInfo(new Position(currentPos.x+1, currentPos.y), currentPos));
        queue.add(new CellInfo(new Position(currentPos.x, currentPos.y+1), currentPos));
        queue.add(new CellInfo(new Position(currentPos.x-1, currentPos.y), currentPos));
        return false;
    }
    
    /**
     * Checks if the entity can move to this position
     * @param currentPosition the current position to check
     * @return true if it can move, false otherwise
     */
    private boolean canMove(Position currentPosition) {
        if (currentPosition.x == startPos.x && currentPosition.y == startPos.y) {
            return true;
        }
        return !round.map.isWall(currentPosition.x, currentPosition.y);
    }
}
