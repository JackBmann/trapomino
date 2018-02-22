package trapomino;

import java.util.logging.Logger;

/**
 * This class creates and manages the Grid Position, direction, and movement of a Snake.
 * @author Micheal Peterson
 */
public class Snake extends Animal{
    private static final Logger log = Logger.getLogger(Snake.class.getName());{log.addHandler(ErrorHandle.animalHandler);}
    int Direction = -1;
    int moveCounter = 0;
    Grid grid;
    
    /**
     * This Snake constructor defines the necessary variables for use in the Snake class.
     * @param x - the X coordinate where the Snake's sprite should be drawn
     * @param y - the Y coordinate where the Snake's sprite should be drawn
     * @param grid - the grid to place the Snake on
     */
    public Snake(int x, int y, Grid grid) {		
        super(x, y, grid, new Position[]{new Position(0,0)}, Grid.SHAPE.Snake, 50);
        this.grid = grid;
    }
    
    /**
     * This method manages the movement of the Snake, the Snake will move every 10 ticks.
     * The method will first check if the Snake can move to the space adjacent to and 
     * in the current direction as the Snake, if so, it makes the new blocks at that Position.
     * If the Snake can not move forward, it will change direction.
     */
    @Override
    public void move(){
        moveCounter++;
        if(moveCounter % 10 == 0){
            if(moveCheck(getPositions(), Direction, 0)){
                super.makeBlocks(getX() + Direction, getY(), getPositions());
            }
            else{
                Direction = -Direction;
                if(getName() == Grid.SHAPE.Snake)
                    setName(Grid.SHAPE.SnakeR);
                else if(getName() == Grid.SHAPE.SnakeR)
                    setName(Grid.SHAPE.Snake);
            }
        }
        while(super.getY() < super.grid.GRID_Y-1 && 
                super.grid.grid[super.getX()][super.getY()+1] == Grid.SHAPE.None){
            super.makeBlocks(getX(), getY()+1, getPositions());
        }
    }
}
