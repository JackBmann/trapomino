package trapomino;

import java.util.logging.Logger;

/**
 * This class creates and manages the Grid Position, direction, egg placement and movement of a Penguin.
 * @author Micheal Peterson
 */
public class Penguin extends Animal{
    private static final Logger log = Logger.getLogger(Penguin.class.getName());{log.addHandler(ErrorHandle.animalHandler);}
    int Direction = 1;
    int moveCounter = 0;
    int iterate = 0;
    Grid grid;

    /**
     * This Penguin constructor defines the necessary variables for use in the Penguin class.
     * @param x - the X coordinate where the Penguin's sprite should be drawn
     * @param y - the Y coordinate where the Penguin's sprite should be drawn
     * @param grid - the grid to place the Penguin on
     */
    public Penguin(int x, int y, Grid grid) {		
        super(x, y, grid, new Position[]{new Position(0,0)}, Grid.SHAPE.Penguin, 100);
        this.grid = grid;
    }

    /**
     * This method manages the movement of the Penguin, the Penguin will move every 10 ticks.
     * The method will first check if the Penguin can move to the space adjacent to and 
     * in the current direction as the Penguin, if so, it makes the new blocks at that Position.
     * If the Penguin can not move forward, it will change direction.
     * After every 5th movement the Penguin makes, the Penguin will create and place 
     * an Egg at its Position on the Grid, the Penguin will move up one space in the Grid.
     */
    @Override
    public void move(){
        moveCounter++;
        if(moveCounter%10 == 0){
            if(moveCheck(getPositions(), Direction, 0))
            {
                super.makeBlocks(getX() + Direction, getY(), getPositions());
            }
            else
            {
                Direction = -Direction;
                if(getName() == Grid.SHAPE.Penguin)
                    setName(Grid.SHAPE.PenguinR);
                else if(getName() == Grid.SHAPE.PenguinR)
                    setName(Grid.SHAPE.Penguin);
            }
            iterate++;
        }
        if(iterate == 5){
            super.makeBlocks(getX(), getY()-1, getPositions());
            grid.grid[getX()][getY()+1] = Grid.SHAPE.Egg;
            iterate = 0;
        }
        while(super.getY() < super.grid.GRID_Y-1 
                && super.grid.grid[super.getX()][super.getY()+1] == Grid.SHAPE.None){
            super.makeBlocks(getX(), getY()+1, getPositions());
        }

    }
}
