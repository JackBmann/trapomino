package trapomino;

/**
 * This class creates, sets, returns, and prints a Position of an Object on the Grid defined at a specific X and Y coordinate.
 * @author Jack Baumann
 */
public class Position{
    private int xPos, yPos;
    
    /**
     * This constructor creates a Position of the X and Y variables passed into it.
     * @param x - the X coordinate of an Object's Position in the Grid
     * @param y - the Y coordinate of an Object's Position in the Grid
     */
    public Position(int x, int y){
        xPos = x;
        yPos = y;
    }
    
    /**
     * This method sets a new X coordinate in a Position.
     * @param x - the X coordinate of an Object's Position in the Grid
     */
    public void setX(int x){
        xPos = x;
    }
    
    /**
     * This method returns the current X coordinate in a Position.
     * @return the X coordinate in a Position
     */
    public int getX(){
        return xPos;
    }
    
    /**
     * This method sets a new Y coordinate in a Position.
     * @param y - the Y coordinate of an Object's Position in the Grid
     */
    public void setY(int y){
        yPos = y;
    }
    
    /**
     * This method returns the current Y coordinate in a Position.
     * @return the Y coordinate in a Position
     */
    public int getY(){
        return yPos;
    }
    
    /**
     * This method is called when printing a Position and it returns the coordinates as an uniform ordered pair String of: (xPos,yPos).
     * @return the ordered pair String to be printed
     */
    @Override
    public String toString(){
    	return "(" + xPos + "," + yPos + ")";
    }
}