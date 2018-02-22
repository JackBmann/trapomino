package trapomino;

import java.util.logging.Logger;

/**
 * This class creates and manages the Grid Position, direction, and movement of a Polar Bear.
 * @author Micheal Peterson
 */
public class Bear extends Animal{
    private static final Logger log = Logger.getLogger(Bear.class.getName());{log.addHandler(ErrorHandle.animalHandler);}
    int Direction = 1;
    int moveCounter = 0;
    Grid grid;
    
    /**
     * This Bear constructor defines the necessary variables for use in the Bear class.
     * @param x - the X coordinate where the Bear's sprite should be drawn
     * @param y - the Y coordinate where the Bear's sprite should be drawn
     * @param grid - the grid to place the Bear on
     */
    public Bear(int x, int y, Grid grid) {		
        super(x, y, grid, new Position[]{new Position(0,0)}, Grid.SHAPE.Bear, 100);
        this.grid = grid;
    }

    /**
     * This method manages the movement of the Bear, the Bear will check if is able to move every 10 ticks.
     * The method will check if the Bear can move to a higher Y coordinate on the Grid through diagonal motion,
     * if so, it makes the new blocks at that Position.
     */
    @Override
    public void move(){
        moveCounter++;
        if(moveCounter%10 == 0){
            if(!Grid.inGrid(new Position(getX() + Direction, getY())) 
                || ((Grid.inGrid(new Position(getX() + Direction, getY() + 1))
                && grid.grid[getX()+Direction][getY()+1] == Grid.SHAPE.None 
                &&(Grid.inGrid(new Position(getX() + Direction, getY()))
                && grid.grid[getX()+Direction][getY()] == Grid.SHAPE.None)))){
                super.makeBlocks(getX(), getY(), getPositions());
                Direction = -Direction;
            }
            else if(moveCheck(getPositions(), Direction, 0)){
                super.makeBlocks(getX() + Direction, getY(), getPositions());
            }
            else{
                grid.grid[getX()][getY()-1] = Grid.SHAPE.None;
                grid.grid[getX() + Direction][getY()-1] = Grid.SHAPE.None;
                super.makeBlocks(getX() + Direction, getY() -1, getPositions());
            }
        }
        while(super.getY() < super.grid.GRID_Y-1 
            && super.grid.grid[super.getX()][super.getY()+1] == Grid.SHAPE.None){
            super.makeBlocks(getX(), getY()+1, getPositions());
        }
    } 
}