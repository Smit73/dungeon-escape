package ca.sfu.cmpt276.dungeon;

/**
 * static layout of the map, dynamic game objects handled in GameObjects
 * @author daniel
 */
public class Map {
    
    private Tile[][] grid;
    private int width;
    private int height;
    
    /**
     * Constructor for the Map
     * 
     * @param width The width of the map
     * @param height The height of the Map
     */
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Tile[height][width];
        
        //Fill map as floor
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = Tile.FLOOR;
            }
        }

    }
    
    /**
     * Create the map layout for level 1
     */
    public void createLevel1() {
        
        for (int x = 0; x < width; x++) {
            setWall(x, 0); //top wall
            setWall(x, height-1); //bottom wall
        }
        for (int y = 0; y < height; y++) {
            setWall(0, y); //left wall
            setWall(width-1, y); //right wall
        }
        
        initializeHorizontalWalls();
        initializeVeritcalWalls();
    
        createWallBlock(4,6,7,8);
        createWallBlock(17,19,7,8);
        createWallBlock(11,12,3,4);
        
        for (int x = 11; x <= 12; x++) {
            for (int y = 11; y <= 12; y++) {
                setWall(x,y);
            }
        }
        createWallBlock(11,12,11,12);
    }

    private void initializeVeritcalWalls() {
        createVerticalWall(8,3,5);
        createVerticalWall(15,3,5);
     
        createVerticalWall(8,10,12);
        createVerticalWall(15,10,12);
    
        createVerticalWall(2,4,12);
        createVerticalWall(21,4,12);

        createVerticalWall(10,6,9);
        createVerticalWall(13,6,9);
        createVerticalWall(9,7,8);
        createVerticalWall(14,7,8);
    }

    private void initializeHorizontalWalls() {
        createHorizontalWall(2,8,2);
        createHorizontalWall(15,21,2);

        createHorizontalWall(4,6,4);
        createHorizontalWall(17,19,4);

        createHorizontalWall(4,6,11);
        createHorizontalWall(17,19,11);

        createHorizontalWall(2,8,13);
        createHorizontalWall(15,21,13);

        createHorizontalWall(10,13,2);
        createHorizontalWall(10,13,13);
    }
    
    /**
     * Set the tile at (x,y) to a wall
     * @param x X coordinate of tile to be set
     * @param y Y coordinate of tile to be set
     */
    public void setWall(int x, int y) {
        // Check if coordinates are within the map bounds
        if (x >= 0 && x < width && y >= 0 && y < height) {
            this.grid[y][x] = Tile.WALL;
        }
    }
    
    /**
     * Create a vertical wall
     * 
     * @param x x-coordinate of the wall
     * @param y1 starting y-coordinate of the wall
     * @param y2 ending y-coordinate of the wall
     */
    private void createVerticalWall(int x, int y1, int y2) {
        for (int y_loop = y1; y_loop <= y2; y_loop++) {
            setWall(x,y_loop);
        }
    }
    
    /**
     * Create a horizontal wall
     * 
     * @param x1 starting x coordinate of the wall
     * @param x2 ending x coordinate of the wall
     * @param y y coordinate of the wall
     */
    private void createHorizontalWall(int x1, int x2, int y) {
        for (int x_loop = x1; x_loop <= x2; x_loop++) {
            setWall(x_loop,y);
        }
    }
    
    
    /**
     * create a block of wall
     * 
     * @param x1 starting x coordinate of the wall
     * @param x2 ending x coordinate of the wall
     * @param y1 starting y coordinate of the wall
     * @param y2 ending y coordinate of the wall
     */
    private void createWallBlock(int x1, int x2, int y1, int y2) {
        for (int x_loop = x1; x_loop <= x2; x_loop++) {
            for (int y_loop = y1; y_loop <= y2; y_loop++) {
                setWall(x_loop, y_loop);
            }
            
        }
    }
    
    
    
    /**
     * Checks if a tile is a wall
     * 
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @return True if the Tile is a wall, False if not
     */
    public boolean isWall(int x, int y) {
        // if out of bounds of the map, then it's a wall
        if (x<0 || x>=width || y<0 || y>=height) {
            return true;
        }
        
        return grid[y][x] == Tile.WALL;
    }
    
    
    /**
     * Getter for width
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter for the height
     * @return the height
     */
    public int getHeight() {
        return height;
    }
    
    
    /**
     * Getter for the Tile type
     * @param x X coordinate of the tile to be checked
     * @param y Y coordinate of the tile to be checked
     * @return The tile at position (x,y)
     */
    public Tile getTile(int x, int y) {
        // if out of bounds of the map, then it's a wall
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.WALL;
        }
        return grid[y][x];
    }
}
