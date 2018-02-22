package trapomino;

import java.util.logging.Logger;

/**
 * This class creates and manages the Grid Position, direction, and movement of a Bird.
 * @author Micheal Peterson
 */
public class Bird extends Animal{
    private static final Logger log = Logger.getLogger(Bird.class.getName());{log.addHandler(ErrorHandle.animalHandler);}
    int Direction = -1;
    int moveCounter = 0;
    Grid grid;
	
    /**
     * This Bird constructor defines the necessary variables for use in the Bird class.
     * @param x - the X coordinate where the Bird's sprite should be drawn
     * @param y - the Y coordinate where the Bird's sprite should be drawn
     * @param grid - the grid to place the Bird on
     */
    public Bird(int x, int y, Grid grid) {		
        super(x, y, grid, new Position[]{new Position(0,0)}, Grid.SHAPE.Bird, 150);
        this.grid = grid;
    }

    /**
     * This method manages the movement of the Bird, the Bird will move every 5 ticks.
     * The method will first check if the Bird can move to the space adjacent to and 
     * in the current direction as the Bird, if so, it makes the new blocks at that Position.
     * If the Bird can not move forward, it will change direction.
     * This method also animates the sprite of the Bird as it moves by changing its shape.
     */
    @Override
    public void move(){
        moveCounter++;
        if(moveCounter % 5 == 0){
            if(moveCheck(getPositions(), Direction, 0)){
                super.makeBlocks(getX() + Direction, getY(), getPositions());
                    
                if(super.getName() == Grid.SHAPE.Bird){
                    super.setName(Grid.SHAPE.Bird2);
                }
                else if(super.getName() == Grid.SHAPE.Bird2){
                    super.setName(Grid.SHAPE.Bird3);
                }
                else if(super.getName() == Grid.SHAPE.Bird3){
                    super.setName(Grid.SHAPE.Bird);
                }
                else if(super.getName() == Grid.SHAPE.BirdR){
                    super.setName(Grid.SHAPE.Bird2R);
                }
                else if(super.getName() == Grid.SHAPE.Bird2R){
                    super.setName(Grid.SHAPE.Bird3R);
                }
                else if(super.getName() == Grid.SHAPE.Bird3R){
                    super.setName(Grid.SHAPE.BirdR);
                }
            }
            else{
                Direction = -Direction;
                if(super.getName() == Grid.SHAPE.BirdR){
                    super.setName(Grid.SHAPE.Bird);
                }
                else if(super.getName() == Grid.SHAPE.Bird2R){
                    super.setName(Grid.SHAPE.Bird2);
                }
                else if(super.getName() == Grid.SHAPE.Bird3R){
                    super.setName(Grid.SHAPE.Bird3);
                }
                else if(super.getName() == Grid.SHAPE.Bird){
                    super.setName(Grid.SHAPE.BirdR);
                }
                else if(super.getName() == Grid.SHAPE.Bird2){
                    super.setName(Grid.SHAPE.Bird2R);
                }
                else if(super.getName() == Grid.SHAPE.Bird3){
                    super.setName(Grid.SHAPE.Bird3R);
                }
            }
        }
    }
 } 