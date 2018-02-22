package trapomino;

import java.util.logging.Logger;

/**
 * This class creates and manages the Grid Position, direction, and movement of a Monkey.
 * @author Micheal Peterson
 */
public class Monkey extends Animal{
    private static final Logger log = Logger.getLogger(Monkey.class.getName());{log.addHandler(ErrorHandle.animalHandler);}
    int xDirection = 1, yDirection = -1;
    int moveCounter = 0;
    int iterate = 0;
    Grid grid;

    /**
     * This Monkey constructor defines the necessary variables for use in the Monkey class.
     * @param x - the X coordinate where the Monkey's sprite should be drawn
     * @param y - the Y coordinate where the Monkey's sprite should be drawn
     * @param grid - the grid to place the Monkey on
     */
    public Monkey(int x, int y, Grid grid) {		
        super(x, y, grid, new Position[]{new Position(0,0)}, Grid.SHAPE.Monkey, 300);
        this.grid = grid;
    }

    /**
     * This method manages the movement of the Monkey, the Monkey will move every 
     * 45 ticks and will wait 3 iterations after moving again for 3 iterations in a row.
     * The method will first check if the Monkey can move to the space above it, if so, it moves up.
     * If the Monkey can not climb, it will try to move to the adjacent block in its current direction.
     * Finally, if it can not move horizontally or vertically, the Monkey will change direction.
     */
    @Override
    public void move(){
        moveCounter++;
        if(moveCounter%45 == 0){
            iterate++;
            //stand still for 3 turns, move for 3
            if(iterate < 4){
                if(moveCheck(getPositions(), 0, yDirection)){
                    super.makeBlocks(getX(), getY() + yDirection, getPositions());
                }
                else if(moveCheck(getPositions(), xDirection, 0)){
                    super.makeBlocks(getX()+xDirection, getY(), getPositions());
                }
                else{
                    xDirection = -xDirection;
                }
            }
            else{
                super.makeBlocks(getX(), getY(), getPositions());
                if(iterate == 7){
                    iterate = 0;
                }
            }
        }

    }
}
