package trapomino;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class will contain and manage all blocks in the grid, including Animals and Tetrominos.
 * This class also checks if the requirements needed to make a cage around an Animal have been met.
 */
public class Grid {
    private static final Logger log = Logger.getLogger(Grid.class.getName());{log.addHandler(ErrorHandle.GridHandler);}
    public final int GRID_X_LOCATION = 330;    //x location of grid onscreen
    public final int GRID_Y_LOCATION = 10;     //y location of grid onscreen
    public final int GRID_SPACING    = 31;     //size of each grid box
    public static final int GRID_X   = 14;     //number of columns
    public static final int GRID_Y   = 22;     //number of row    
    
    public Entity gridPic = new Entity(GRID_X_LOCATION,20,0,0,"grid2.png");//Background image
    
    public static SHAPE[][] grid = new SHAPE[GRID_X][GRID_Y];//grid of all the shapes, Grid of the game

    /**
     * This enum contains all of the shapes that can possibly be in the grid.
     */
    public enum SHAPE{I, O, T, L, J, Z, S, None, Trapped,
                        Snake, SnakeR, SnakeTrapped, 
                        Penguin, PenguinR, PenguinTrapped, Egg,
                        Lion, LionTrapped, 
                        Bird, Bird2, Bird3, BirdR, Bird2R, Bird3R, BirdTrapped, 
                        Monkey, MonkeyTrapped,
                        Bear, BearTrapped};
    
    public static ArrayList<SHAPE> shapes = new ArrayList<SHAPE>();
    public static ArrayList<SHAPE> animal = new ArrayList<SHAPE>();
    public static ArrayList<SHAPE> bird = new ArrayList<SHAPE>();
    
    public static UnionMap map;
    
    /**
     * This constructor defines the Grid, the sprites, and the shapes.
     */
    public Grid()
    {
        new Auxillary();
        shapes.add(SHAPE.I);
        shapes.add(SHAPE.J);
        shapes.add(SHAPE.O);
        shapes.add(SHAPE.T);
        shapes.add(SHAPE.L);
        shapes.add(SHAPE.J);
        shapes.add(SHAPE.Z);
        shapes.add(SHAPE.S);
        shapes.add(SHAPE.Egg);
        
        animal.add(SHAPE.Snake); animal.add(SHAPE.SnakeR);
        
        animal.add(SHAPE.Bear);
        
        bird.add(SHAPE.Bird); bird.add(SHAPE.Bird2); bird.add(SHAPE.Bird3);
        bird.add(SHAPE.BirdR); bird.add(SHAPE.Bird2R); bird.add(SHAPE.Bird3R);
        animal.addAll(bird);
        
        animal.add(SHAPE.Lion);
        animal.add(SHAPE.Monkey);
        
        animal.add(SHAPE.Penguin); animal.add(SHAPE.PenguinR);
        
        //sets all indexes in the grid to empty
        for(int x = 0; x < GRID_X; x++)
            for(int y = 0; y < GRID_Y; y++)
                grid[x][y] = SHAPE.None;
    }
    
    /**
     * This method sets the (x,y) Position in the grid to the given shape.
     * @param item - the shape to set at the given Position in the Grid
     * @param x - the X coordinate in the Grid to alter
     * @param y - the Y coordinate in the Grid to alter
     */
    public static void setGridLoc(SHAPE item, int x, int y){
        grid[x][y] =  item;
    }
    
    /**
     * This method creates the UnionMap that will be used to check if a cage has been created.
     * The method will then check if the Positions surrounding each Position in the Grid contain Tetrominos
     * If any two adjacent positions both contain Tetrominos, they will be united in the map.
     */
    public static void unionCreate(){
        map = new UnionMap(GRID_X, GRID_Y);
        for(int x = 0; x < GRID_X; x++){
            for(int y = 0; y < GRID_Y; y++){
                if(inGrid(new Position(x+1,y)) && similar(new Position(x,y), new Position(x+1,y))){
                    map.Unite(new Position(x,y), new Position(x+1,y));
                }
                if(inGrid(new Position(x,y+1)) && similar(new Position(x,y), new Position(x,y+1))){
                    map.Unite(new Position(x,y), new Position(x,y+1));
                }
                if(inGrid(new Position(x-1,y)) && similar(new Position(x,y), new Position(x-1,y))){
                    map.Unite(new Position(x,y), new Position(x-1,y));
                }
                if(inGrid(new Position(x,y-1)) && similar(new Position(x,y), new Position(x,y-1))){
                    map.Unite(new Position(x,y), new Position(x,y-1));
                }
            }
        }
    }
    
    /**
     * This method checks if the given Position is contained within the Grid.
     * @param pos - the position to check
     * @return true if the Position is in the Grid, false otherwise
     */
    public static boolean inGrid(Position pos){
        return pos.getX() < GRID_X && pos.getX() >= 0 && pos.getY() < GRID_Y && pos.getY() >= 0;
    }
    
    /**
     * This method checks if the two given Positions have the same shape.
     * Two Positions occupied by Tetrominos will have the same shape. 
     * @param pos1 - the first position to check for similarity
     * @param pos2 - the second position to check for similarity
     * @return true if the Positions contain similar shapes, false otherwise
     */
    public static boolean similar(Position pos1, Position pos2){
        return (grid[pos1.getX()][pos1.getY()] == grid[pos2.getX()][pos2.getY()])
                || (grid[pos1.getX()][pos1.getY()] == SHAPE.None && animal.contains(grid[pos2.getX()][pos2.getY()]))
                || (grid[pos2.getX()][pos2.getY()] == SHAPE.None && animal.contains(grid[pos1.getX()][pos1.getY()]));
    }
    
    /**
     * This method checks if the Animal at the given Position has been trapped.
     * @param pos - the Position at which to check for a trap at.
     * @return true if the Position is trapped, false otherwise
     */
    public static boolean findTrap(Position pos){
        return inGrid(pos) && map.find(pos, new Position(0,0));
    }
    
    /**
     * This method checks if the top row of the Grid is empty to see if the player has lost.
     * @return true if the top row is empty, false if it has at least one occupied Position
     */
    public boolean topEmpty() {
        boolean empty = true;
        for(int x = 0; x < GRID_X; x++){
            if(grid[x][0] != SHAPE.None){
                empty = false;
            }
        }
        return empty;
    }
    
    /**
     * This method removes the rows that are completely filled, and moves the other rows down to the next empty Position.
     * This method then adds the rows that were cleared to an ArrayList and returns that ArrayList.
     * @return an ArrayList that contains the cleared rows
     */
    public ArrayList<Integer> clearRows(){
        ArrayList<Integer> clearedRows = new ArrayList<Integer>();
        for(int y = 0; y < GRID_Y; y++){
            if(rowCheck(y)){
                moveDownAndClear(y);
                clearedRows.add(y);
            }   
        }
        return clearedRows;
    }
    
    /**
     * This method checks if a row has been completely filled with shapes and returns true if it is filled.
     * @param y - the Y coordinate of the row to check
     * @return true if the row is filled or false if one or more Positions is empty
     */
    public boolean rowCheck(int y) {
        for(int x = 0; x < GRID_X; x++){
            if(grid[x][y] == SHAPE.None || animal.contains(grid[x][y])){
                return false;
            }
        }
        return true;
    }
    
    /**
     * This method clears the row at the given Y coordinate and moves the rows above it down.
     * @param row - the Y coordinate of the row to clear
     */
    public void moveDownAndClear(int row){
        //clear the current row
        for(int x = 0; x < GRID_X; x++){
            grid[x][row] = SHAPE.None;
        }
        //Move everything down
        for(int y = row; y > 0; y--){
            for(int x = 0; x < GRID_X; x++){
                if(animal.contains(grid[x][y-1]))
                    grid[x][y] = SHAPE.None;
                else
                    grid[x][y] = grid[x][y-1];
            }
        }
        //clear first row
        for(int x = 0; x < GRID_X; x++)
            grid[x][0] = SHAPE.None;
    }
    
    /**
     * This method stores the id of a trapped location in the UnionMap and checks 
     * every Position in the grid to see if its UnionMap value matches.
     * If the checked Position matches and is therefore trapped, it replaces the shape with its trapped equivalent.
     * @param pos - a trapped Position
     */
    public void changeToTrapped(Position pos){
        int id = map.id[pos.getX()][pos.getY()];
         for(int x = 0; x < GRID_X; x++){
            for(int y = 0; y < GRID_Y; y++){
                if(map.id[x][y] == id){
                    if(grid[x][y] ==SHAPE.None)
                        grid[x][y] = SHAPE.Trapped;
                    else if(grid[x][y] == SHAPE.Snake || grid[x][y] == SHAPE.SnakeR){
                        grid[x][y] = SHAPE.SnakeTrapped;
                    }
                    else if(grid[x][y] == SHAPE.Lion){
                        grid[x][y] = SHAPE.LionTrapped;
                    }
                    else if(grid[x][y] == SHAPE.Penguin || grid[x][y] == SHAPE.PenguinR){
                        grid[x][y] = SHAPE.PenguinTrapped;
                    }
                    else if(bird.contains(grid[x][y])){
                        grid[x][y] = SHAPE.BirdTrapped;
                    }
                    else if(grid[x][y] == SHAPE.Monkey){
                        grid[x][y] = SHAPE.MonkeyTrapped;
                    }
                    else if(grid[x][y] == SHAPE.Bear){
                        grid[x][y] = SHAPE.BearTrapped;
                    }
                }
            }
         }   
    }
    
    /**
     * This method renders the Grid and the blocks it contains onto the frame.
     * @param window - the Graphics class to render with
     */
    public void render(Graphics window){
        //draws the Background image of the grid
        window.drawImage(gridPic.getImage(), GRID_X_LOCATION, GRID_Y_LOCATION, null);
        
        //goes down the columns of the grid
        for(int y = 0; y < GRID_Y; y++){
            //goes down the rows of the grid
            for(int x = 0; x < GRID_X; x++){
                //checks if the grid has a block in the spot
                if(grid[x][y] != SHAPE.None){
                    //loads the image of the particualr block
                    window.drawImage(Auxillary.getImage(grid[x][y]),
                        x*GRID_SPACING + GRID_X_LOCATION, y*GRID_SPACING + GRID_Y_LOCATION,null);
                }
            }
        }   
    }  
}
