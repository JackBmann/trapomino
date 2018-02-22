package trapomino;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This class will manage the class variables for the Tetromino blocks.
 */
public class Tetromino{
    private static final Logger log = Logger.getLogger(Tetromino.class.getName());{log.addHandler(ErrorHandle.TetrominoHandler);}
    private int xPos, yPos;
    private int speed;
    
    private Grid.SHAPE shape;
    
    private Grid grid;
    
    public Position[] positions;
    
    private int blockSpacing = 31;
    private int rotation = 0;
    
    Random rand = new Random();
    
    /**
     * This constructor constructs a new Tetromino which is a series of 4 different blocks.
     * Each Tetromino must exist within a grid and will be assigned its variables from the given variables.
     * @param x - the X coordinate of the Tetromino in the Grid
     * @param y - the Y coordinate of the Tetrominos in the Grid
     * @param grid - the Grid the Tetromino exists in
     * @param type - the Shape of the Tetromino
     */
    public Tetromino(int x, int y, Grid grid, Grid.SHAPE type){
        this.xPos = x;
        this.yPos = y;
        this.grid = grid;
        
        //makes the block the random number based on the shape array
        shape = type;
        
        positions = getType(shape);
                
        makeBlocks(getX(), getY(), positions);
    }
    
    /**
     * This constructor constructs a new Tetromino which is a series of 4 different blocks.
     * Each Tetromino must exist within a grid and its variables will be assigned as random accepted values.
     */
    public Tetromino(){
        //creates a random number bewtween 0-7
        int type = rand.nextInt(8);
        //makes the block the random number based on the shape array
        shape = Grid.shapes.get(type);
        
        positions = getType(shape);
    }
    
    /**
     * This method returns an Array of Positions that the given block's shape occupies.
     * @param shape - the shape of the Tetromino
     * @return the Array of Positions that that shape occupies
     */
    private Position[] getType(Grid.SHAPE shape){
                //creates the blocks based on each case TODO add auxillary class so these positiion arrays arent here
        switch(shape){
            case I:
                return new Position[]{new Position(-1,0), 
                                        new Position(0,0),
                                        new Position(1,0),
                                        new Position(2,0)};
            case O:
                return new Position[]{new Position(0,0), 
					 new Position(1,0),
					 new Position(1,1),
					 new Position(0,1)};
            case T:
                return new Position[]{new Position(0,0), 
		  			new Position(-1,0),
		  			new Position(1,0),
		  			new Position(0,-1)};
            case L:
                return new Position[]{new Position(0,0), 
		  			new Position(1,0),
		  			new Position(-1,-1),
		  			new Position(-1,0)};
            case J:
                return new Position[]{new Position(0,0), 
		  			new Position(1,0),
		  			new Position(1,-1),
		  	                new Position(-1,0)};
            case Z:
                return new Position[]{new Position(0,0), 
		  			new Position(0,1),
		  			new Position(-1,-1),
		  			new Position(-1,0)};
            case S:
                return new Position[]{new Position(0,0), 
		  			new Position(0,-1),
		  			new Position(-1,0),
		  			new Position(-1,1)};
        }
        return null;
    }

    /**
     * This method returns the X coordinate of the Tetromino in the Grid.
     * @return the X coordinate of the Tetromino in the Grid
     */
    public int getX(){
        return xPos;
    }
    
    /**
     * This method returns the Y coordinate of the Tetromino in the Grid.
     * @return the Y coordinate of the Tetromino in the Grid
     */
    public int getY(){
        return yPos;
    }
    
    /**
     * This method returns the Grid the Tetromino is placed in.
     * @return the Grid the Tetromino is placed in
     */
    public Grid getGrid(){
        return grid;
    }
    
    /**
     * This method returns the shape of the Tetromino.
     * @return the shape of the Tetromino
     */
    public Grid.SHAPE getShape(){
        return shape;
    }
    
    /**
     * This method returns the Positions the Tetromino occupies in the Grid.
     * @return the Positions the Tetromino occupies in the Grid
     */
    public ArrayList<Position> getPositions(){
        ArrayList<Position> positions = new ArrayList<Position>();
        
        positions.add(new Position(this.positions[0].getX() + getX(),this.positions[0].getY() + getY()));
        positions.add(new Position(this.positions[1].getX() + getX(),this.positions[1].getY() + getY()));
        positions.add(new Position(this.positions[2].getX() + getX(),this.positions[2].getY() + getY()));
        positions.add(new Position(this.positions[3].getX() + getX(),this.positions[3].getY() + getY()));
        
        return positions;
    }
    
    /**
     * This method moves the Positions of the Tetromino's blocks one space right in the Grid.
     * @return true if the Tetromino moved right, false otherwise
     */
    public boolean moveRight(){
    	if(moveCheck(positions,-1,0)){
            makeBlocks(getX() -1, getY(), positions);
            return true;
        }
        else
            return false;
    }
    
    /**
     * This method moves the Positions of the Tetromino's blocks one space left in the Grid.
     * @return true if the Tetromino moved left, false otherwise
     */
    public boolean moveLeft(){
    	if(moveCheck(positions,1,0)){
            makeBlocks(getX() +1, getY(), positions);
            return true;
        }
        else
            return false;
    }
    
    /**
     * This method moves the Positions of the Tetromino's blocks one space down in the Grid.
     * @return true if the Tetromino moved down, false otherwise
     */
    public boolean moveDown(){
    	if(moveCheck(positions,0,1)){
                makeBlocks(getX(), getY() +1, positions);
            return true;
        }
        else
            return false;
    }
    
    /**
     * This method checks whether or not the Tetromino can move to the given new Positions in the Grid.
     * @param newPositions - the Positions to check for avaliable space
     * @param xMovement - how far to move the Tetromino horizontally
     * @param yMovement - how far to move the Tetromino vertically
     * @return true if the Tetromino can move to the given Positions in the Grid, false otherwise
     */
    public boolean moveCheck(Position[] newPositions, int xMovement, int yMovement){
        removeBlocksFromGrid();
        
        //checks if the next location for each block is empty and inside the grid
    	for(int c = 0; c<4; c++){
            int x = newPositions[c].getX() + getX() + xMovement;
    	    int y = newPositions[c].getY() + getY() + yMovement;
            
            if(!Grid.inGrid(new Position(x, y)) ||
                    grid.grid[x][y] != Grid.SHAPE.None){
                addBlocksToGrid();
                return false;
            }
        }
        addBlocksToGrid();
        return true;
    }
   
    /**
     * This method rotates the Tetromino's Positions in the grid based on which orientation it has.
     */
    public void rotate(){
        switch(shape){
            case I:
                switch(rotation){
                   case 0:		
                	   rotate(new Position[]{new Position(0,-1),new Position(0,0),
               				new Position(0,1),new Position(0,2)});
                           rotation = 1;
                        break;
                    case 1:
                 	   rotate(new Position[]{new Position(-1,0),new Position(0,0),
            				new Position(1,0),new Position(2,0)});
                	   rotation = 0;
                        break;
                }
                break;
            case T:
                switch(rotation){
                    case 0:
                 	   rotate(new Position[]{new Position(0,0),new Position(1,0),
            				new Position(0,-1),new Position(0,1)});
                           rotation = 1;
                        break;
                    case 1:
                           rotate(new Position[]{new Position(0,0),new Position(-1,0),
		  			new Position(1,0), new Position(0,1)});
                           rotation = 2;
                        break;
                    case 2:
                            rotate(new Position[]{new Position(0,0),new Position(-1,0),
            				new Position(0,-1),new Position(0,1)});
                            rotation = 3;
                        break;
                   case 3:
                 	   rotate(new Position[]{new Position(0,0),new Position(-1,0),
                        		new Position(1,0), new Position(0,-1)});
                           rotation = 0;
                        break;
                }
                break;
            case L:
                switch(rotation){
                    case 0:
                 	   rotate(new Position[]{new Position(0,0), new Position(0,-1),
                        		new Position(-1,1), new Position(0,1)});
                	   rotation = 1;
                        break;
                    case 1:
                           rotate(new Position[]{new Position(0,0),new Position(-1,0),
            				new Position(1,0),new Position(1,1)});
                	   rotation = 2;
                        break;
                    case 2:
                           rotate(new Position[]{new Position(0,0),new Position(0,1),
            				new Position(1,-1),new Position(0,-1)});
                	   rotation = 3;
                        break;
                    case 3:
                           rotate(new Position[]{new Position(0,0),new Position(1,0),
            				new Position(-1,0),new Position(-1,-1)});
                	   rotation = 0;
                        break;
                }
                break;
            case J:
                switch(rotation){
                    case 0:
                            rotate(new Position[]{new Position(0,0),new Position(0,-1),
            				new Position(0,1),new Position(-1,-1)});
                            rotation = 1;
                        break;
                    case 1:
                 	   rotate(new Position[]{new Position(0,0),new Position(-1,0),
            				new Position(1,0),new Position(-1,1)});
                           rotation = 2;
                        break;
                    case 2:
                 	   rotate(new Position[]{new Position(0,0),new Position(0,1),
            				new Position(0,-1),new Position(1,1)});
                	   rotation = 3;
                        break;
                    case 3:
                 	   rotate(new Position[]{new Position(0,0), new Position(1,0),
            		  			new Position(1,-1), new Position(-1,0)});
                	   rotation = 0;
                        break;
                }
                break;
            case Z:
                switch(rotation){
                    case 0:
                 	   rotate(new Position[]{new Position(0,0), new Position(-1,0),
                        		new Position(0,-1), new Position(1,-1)});
                           rotation = 1;
                        break;
                    case 1:
                 	   rotate(new Position[]{new Position(0,0), new Position(0,1), 
                        		new Position(-1,-1), new Position(-1,0)});
                           rotation = 0;
                        break;
                }
                break;
            case S:
                switch(rotation){
                    case 0: 
                 	   rotate(new Position[]{new Position(0,0), new Position(-1,-1),
                        		new Position(1,0), new Position(0,-1)});
                           rotation = 1;
                        break;
                    case 1:
                 	   rotate(new Position[]{new Position(0,0), new Position(0,-1),
            		  			new Position(-1,0), new Position(-1,1)});
                	   rotation = 0;
                        break;
                }
                break;
        }
    }
    
    /**
     * This method rotates the Tetromino's Positions in the grid to the given Positions.
     * @param newPositions - the new Positions the Tetromino will occupy
     */
    private void rotate(Position[] newPositions){
        if(moveCheck(newPositions,0,0)){
            makeBlocks(getX(), getY(), newPositions);
        }
    }
    
    /**
     * This method temporarily removes the blocks from the Grid for Grid checking purposes.
     */
    public void removeBlocksFromGrid(){
        //sets current space of blocks to blank in the grid
        for(int c = 0; c<4; c++){
            int xSpace = positions[c].getX();
    	    int ySpace = positions[c].getY();
            grid.grid[getX() + xSpace][getY() + ySpace] = Grid.SHAPE.None;
        }
    }
    
    /**
     * This method adds the blocks back to the Grid after Grid checking is complete.
     */
    public void addBlocksToGrid(){
        //reads all the pieces back to the grid
        for(int c = 0; c<4; c++){
            int xSpace = positions[c].getX();
            int ySpace = positions[c].getY();
            grid.grid[getX() + xSpace][getY() + ySpace] = shape;
        } 
    }
    
    /**
     * This method sets the new X and Y coordinates and the new Positions of the Animal.
     * The method then adds the blocks to the grid at those Positions.
     * @param x - the updated X coordinate of the Animal
     * @param y - the updated Y coordinate of the Animal
     * @param newPositions  - the new set of Positions the Animal occupies on the Grid
     */
    private void makeBlocks(int x, int y, Position[] newPositions){
        removeBlocksFromGrid();
        
        //sets X and Y locaiton
        this.xPos = x;
        this.yPos = y;
        
        this.positions = newPositions;
       
        //adds all the new blocks at the new positons
        for(int c = 0; c<4; c++){
            int xSpace = positions[c].getX();
            int ySpace = positions[c].getY();

            grid.grid[getX() + xSpace][getY() + ySpace] = shape;
    	}
    }  
    
    /**
     * This method renders the Tetromino's sprite on the frame.
     * @param g - the Graphics class to render with
     */
    public void render(Graphics g){
        for(int c = 0; c<4; c++){
            int xSpace = positions[c].getX();
            int ySpace = positions[c].getY();
            g.drawImage(Auxillary.getImage(shape),
                xSpace*31 + 865, ySpace*31 + 207,null);
        }
    }
}