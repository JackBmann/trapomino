package trapomino;

import java.util.logging.Logger;

/**
 * This abstract class will set the precedent for the methods pertaining to the Animals in the Game, all Animals will extend this class.
 * This class will handle the defining and creation of an Animal by receiving and assigning the class variables pertaining to an Animal.
 * This class will also return any of the variables when the corresponding method is called.
 * This class will also check to see if an Animal can move or is trapped and will add, remove, and place blocks in the Grid.
 * @author Micheal Peterson
 */
public abstract class Animal{
    private static final Logger log = Logger.getLogger(Animal.class.getName());{log.addHandler(ErrorHandle.animalHandler);}
    
    private int xPos, yPos;
    private int score = 0;
    
    private Position[] positions;
     
    public Grid grid;
    private Grid.SHAPE name;
    
    /**
     * This constructor sets the class variables defined above to the given variables.  It then adds the Animal's blocks to the Grid.
     * @param x - the X coordinate of the Animal
     * @param y - the Y coordinate of the Animal
     * @param grid - the Grid in which to place the Animal
     * @param positions - an Array of Positions the Animal occupies in the Grid.
     * @param name - the name of the Animal's Shape
     * @param score - the current score in the Game
     */
    public Animal(int x, int y, Grid grid, Position[] positions, Grid.SHAPE name, int score){
		this.xPos = x;
                this.yPos = y;
		this.grid = grid;
                this.positions = positions;
                this.name = name;
                this.score = score;
                
                makeBlocks(getX(), getY(), positions);
    }
    
    /**
     * This method returns the Animal's X coordinate in the Grid
     * @return the Animal's X coordinate in the Grid
     */
    public int getX(){
        return xPos;
    }
    
    /**
     * This method returns the Animal's Y coordinate in the Grid
     * @return the Animal's Y coordinate in the Grid
     */
    public int getY(){
        return yPos;
    }
    
    /**
     * This method sets the Animal's Shape
     * @param name - the name of the Animal's Shape
     */
    public void setName(Grid.SHAPE name){
        this.name = name;
    }
    
    /**
     * This method returns the Animal's Shape
     * @return the Animal's Shape
     */
    public Grid.SHAPE getName(){
         return name;
    }

    /**
     * This method returns the array of Positions on the Grid occupied by the Animal.
     * @return the array of Positions on the Grid occupied by the Animal
     */
    public Position[] getPositions(){
        return positions;
    }
    
    /**
     * This method returns the score in the Game.
     * @return the score in the Game
     */
    public int getScore(){
        return score;
    }
    
    /**
     * This method will be expanded upon in each Animal child to move the Animal.
     */
    public abstract void move();
    
    /**
     * This method sets the new X and Y coordinates of the position and then checks
     * whether or not the Animal is able to move to the given new Position.
     * @param newPositions - the Array of Positions to check if empty
     * @param xMovement - the change in X coordinate in the Grid
     * @param yMovement - the change in Y coordinate in the Grid
     * @return true if Animal can move or false if the Animal cannot move
     */
    public boolean moveCheck(Position[] newPositions, int xMovement, int yMovement){
        removeBlocksFromGrid();
        
        //checks if the next location for each block is empty and inside the grid
    	for(Position p: positions){
            int x = p.getX() + getX() + xMovement;
    	    int y = p.getY() + getY() + yMovement;
            
            if(x >= grid.GRID_X || x < 0 || y >= grid.GRID_Y || y < 0 ||
                    grid.grid[x][y] != Grid.SHAPE.None){
                addBlocksToGrid();
                return false;
            }
        }
        
        addBlocksToGrid();
        return true;
    }
    
    /**
     * This method checks whether or not the Animal has been trapped.
     * @return true if the Animal is trapped, or false if it has not been trapped
     */
    public boolean trapCheck(){
        return grid.findTrap(new Position(xPos+1,yPos)) 
            || grid.findTrap(new Position(xPos,yPos+1))
            || grid.findTrap(new Position(xPos-1,yPos)) 
            || grid.findTrap(new Position(xPos,yPos-1));
    }
    
    /**
     * This method temporarily removes the blocks from the Grid for Grid checking purposes.
     */
    private void removeBlocksFromGrid(){
        //sets current space of blocks to blank in the grid
        for(Position p: positions){
            int xSpace = p.getX();
    	    int ySpace = p.getY();
            grid.grid[getX() + xSpace][getY() + ySpace] = Grid.SHAPE.None;
        }
    }
    
    /**
     * This method adds the blocks back to the Grid after Grid checking is complete.
     */
    private void addBlocksToGrid(){
        //reads all the pieces back to the grid
        for(Position p: positions){
            int xSpace = p.getX();
    	    int ySpace = p.getY();
            grid.grid[getX() + xSpace][getY() + ySpace] = name;
        } 
    }
    
    /**
     * This method sets the new X and Y coordinates and the new Positions of the Animal.
     * The method then adds the blocks to the grid at those Positions.
     * @param x - the updated X coordinate of the Animal
     * @param y - the updated Y coordinate of the Animal
     * @param newPositions  - the new set of Positions the Animal occupies on the Grid
     */
    public void makeBlocks(int x, int y, Position[] newPositions){
        removeBlocksFromGrid();
        
        //sets X and Y locaiton
        this.xPos = x;
        this.yPos = y;
        
        this.positions = newPositions;
       
        //adds all the new blocks at the new positons
        for(int c = 0; c<positions.length; c++){
            int xSpace = positions[c].getX();
            int ySpace = positions[c].getY();

            grid.grid[getX() + xSpace][getY() + ySpace] = name;
    	}
    }
}