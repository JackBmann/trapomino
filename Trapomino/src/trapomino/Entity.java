package trapomino;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This class creates, initializes, and manages the class variables used by the Entities in the Game.
 * @author Micheal Peterson
 */
public class Entity {
    private static final Logger log = Logger.getLogger(Animal.class.getName());{log.addHandler(ErrorHandle.EntityHandler);}
    private Position position;
    private int width, height;
    private int xVol, yVol;
    private Rectangle hitBox;

    private BufferedImage image;
    
    private Position gridPosition;
    
    private Grid grid;
    
    /**
     * This constructor sets all of the class variables assigned above to their default values.
     */
    public Entity(){
        position = new Position(0,0);
        this.width = 0;
        this.height = 0;
        this.xVol = 0;
        this.yVol = 0;
        this.setRectangle();
    }
    
    /**
     * This constructor sets all of the class variables assigned above to the given variables.
     * @param xPos - the X coordinate of the Entity
     * @param yPos - the Y coordinate of the Entity
     * @param width - the width of the Entity's sprite and Shape
     * @param height - the height of the Entity's sprite and Shape
     */
    public Entity(int xPos, int yPos, int width, int height){
        position = new Position(xPos,yPos);
        this.width = width;
        this.height = height;
        this.xVol = 0;
        this.yVol = 0; 
        this.setRectangle();
    }
   
   /**
     * This constructor sets all of the class variables assigned above to the given variables.
     * @param xPos - the X coordinate of the Entity
     * @param yPos - the Y coordinate of the Entity
     * @param width - the width of the Entity's sprite and Shape
     * @param height - the height of the Entity's sprite and Shape
     * @param pic_ID - the index of the sprite's Image
     */
    public Entity(int xPos, int yPos, int width, int height, int[] pic_ID){
        position = new Position(xPos,yPos);
        this.width = width;
        this.height = height;
        this.xVol = 0;
        this.yVol = 0; 
        //this.image = BufferedImageLoader.image(pic_Id)
        this.setRectangle();
    }
    
    /**
     * This constructor sets all of the class variables assigned above to the given variables.
     * @param xPos - the X coordinate of the Entity
     * @param yPos - the Y coordinate of the Entity
     * @param width - the width of the Entity's sprite and Shape
     * @param height - the height of the Entity's sprite and Shape
     * @param image - the sprite of the Entity as an Image
     */ 
    public Entity(int xPos, int yPos, int width, int height, BufferedImage image){
        position = new Position(xPos,yPos);
        this.width = width;
        this.height = height;
        this.xVol = 0;
        this.yVol = 0; 
        this.image = image;
        this.setRectangle();
    }
    
    /**
     * This constructor sets all of the class variables assigned above to the given variables.
     * @param xPos - the X coordinate of the Entity
     * @param yPos - the Y coordinate of the Entity
     * @param width - the width of the Entity's sprite and Shape
     * @param height - the height of the Entity's sprite and Shape
     * @param image - the name of the Entity's sprite
     */          
    public Entity(int xPos, int yPos,  int width, int height, String image){
        position = new Position(xPos,yPos);
        this.width = width;
        this.height = height;
        this.xVol = 0;
        this.yVol = 0;
        setImage(image);
        this.setRectangle();
    }
    
    /**
     * This constructor sets all of the class variables assigned above to the given variables.
     * @param xPos - the X coordinate of the Entity
     * @param yPos - the Y coordinate of the Entity
     * @param width - the width of the Entity's sprite and Shape
     * @param height - the height of the Entity's sprite and Shape
     * @param xVol - the X velocity of the Entity
     * @param yVol - the Y velocity of the Entity
     */
    public Entity(int xPos, int yPos,  int width, int height, int xVol, int yVol){
        position = new Position(xPos,yPos);
        this.width = width;
        this.height = height;
        this.xVol = xVol;
        this.yVol = yVol;
        this.setRectangle();
    }
        
    /**
     * This constructor sets all of the class variables assigned above to the given variables.
     * @param xPos - the X coordinate of the Entity
     * @param yPos - the Y coordinate of the Entity
     * @param width - the width of the Entity's sprite and Shape
     * @param height - the height of the Entity's sprite and Shape
     * @param xVol - the X velocity of the Entity
     * @param yVol - the Y velocity of the Entity
     * @param pic_ID - the index of the sprite's Image
     */
    public Entity(int xPos, int yPos,  int width, int height, int xVol, int yVol, int[] pic_ID){
        position = new Position(xPos,yPos);
        this.width = width;
        this.height = height;
        this.xVol = xVol;
        this.yVol = yVol;
        //this.image = BufferedImageLoader.image(pic_Id)
        this.setRectangle();
    }
       
    /**
     * This constructor sets all of the class variables assigned above to the given variables.
     * @param xPos - the X coordinate of the Entity
     * @param yPos - the Y coordinate of the Entity
     * @param width - the width of the Entity's sprite and Shape
     * @param height - the height of the Entity's sprite and Shape
     * @param xVol - the X velocity of the Entity
     * @param yVol - the Y velocity of the Entity
     * @param image - the sprite of the Entity as an Image
     */ 
    public Entity(int xPos, int yPos,  int width, int height, int xVol, int yVol, BufferedImage image){
        position = new Position(xPos,yPos);
        this.width = width;
        this.height = height;
        this.xVol = xVol;
        this.yVol = yVol;
        this.image = image;
        this.setRectangle();
    }
        
    /**
     * This constructor sets all of the class variables assigned above to the given variables.
     * @param xPos - the X coordinate of the Entity
     * @param yPos - the Y coordinate of the Entity
     * @param width - the width of the Entity's sprite and Shape
     * @param height - the height of the Entity's sprite and Shape
     * @param xVol - the X velocity of the Entity
     * @param yVol - the Y velocity of the Entity
     * @param image - the name of the Entity's sprite
     */   
    public Entity(int xPos, int yPos,  int width, int height, int xVol, int yVol, String image){
        position = new Position(xPos,yPos);
        this.width = width;
        this.height = height;
        this.xVol = xVol;
        this.yVol = yVol;
        setImage(image);
        this.setRectangle();
    }
    
     /**
     * This method returns the Entity's X coordinate.
     * @return the Entity's X coordinate
     */
    public int getXPosition(){
        return position.getX();
    }
    
    /**
     * This method returns the Entity's Y coordinate.
     * @return the Entity's Y coordinate
     */
    public int getYPosition(){
        return position.getY();
    }
       
    /**
     * This method returns the Entity's horizontal velocity.
     * @return the Entity's horizontal velocity
     */
    public int getXVelocity(){
        return xVol;
    }
        
    /**
     * This method returns the Entity's vertical velocity.
     * @return the Entity's vertical velocity
     */
    public int getYVelocity(){
        return yVol;
    }
     
    /**
     * This method returns the width of the Entity's sprite.
     * @return the width of the Entity's sprite
     */ 
    public int getWidth(){
        return width;
    }
      
    /**
     * This method returns the height of the Entity's sprite.
     * @return the height of the Entity's sprite
     */
    public int getHeight(){
        return height;
    }
      
    /**
     * This method returns the Entity's position and size as a rectangle.
     * @return the Entity's position and size as a rectangle
     */   
    public Rectangle getRectangle(){
        return hitBox;
    }
  
    /**
     * This method returns the Entity's sprite as an Image.
     * @return the Entity's sprite as an Image
     */
    public BufferedImage getImage(){
        return image;
    }
      
    /**
     * This method returns the current Position the Entity occupies.
     * @return the current Position of the Entity
     */
    public Position getGridPosition(){
    	return gridPosition;
    }
    
    /**
     * This method sets the Entity's X coordinate to the given value.
     * @param xPos - the new X coordinate of the Entity
     */
    public void setXPosition(int xPos){
        this.position.setX(xPos);
        setRectangle();
    }
    
    /**
     * This method sets the Entity's Y coordinate to the given value.
     * @param yPos - the new Y coordinate of the Entity
     */
    public void setYPosition(int yPos){
        this.position.setY(yPos);
        setRectangle();
    }
   
    /**
     * This method sets the Entity's horizontal velocity to the given value.
     * @param xVol - the new horizontal velocity of the Entity
     */
    public void setXVelocity(int xVol){
        this.xVol = xVol;
    }
  
    /**
     * This method sets the Entity's vertical velocity to the given value.
     * @param yVol - the new vertical velocity of the Entity
     */
    public void setYVelocity(int yVol){
        this.yVol = yVol;
    }
      
    /**
     * This method sets the Entity's height to the given value.
     * @param height - the new height of the Entity
     */  
    public void setHieght(int height){
        this.height = height;
    }
      
    /**
     * This method sets the Entity's width to the given value.
     * @param width - the new width of the Entity
     */
    public void setWidth(int width){
        this.width = width;
    }
        
    /**
     * This method sets the Entity's hitbox to a Rectangle with its current X and Y coordinates, width, and height.
     */
    public void setRectangle(){
        hitBox = new Rectangle(position.getX(), position.getY(), width, height);
    }

    /**
     * This method sets the Entity's sprite Image to the one given.
     * @param image - the Entity's new sprite Image
     */
    public void setImage(BufferedImage image){
        this.image = image;
    } 
     
    /**
     * This method sets the Entity's sprite Image to the one corresponding to the given name in the Graphics folder of Trapomino.
     * If it encounters an error while loading or assigning the image, it writes an error to the ErrorLogger.
     * @param imageFile - the file name of the Entity's sprite
     */
    public void setImage(String imageFile){
        try{
            this.image = ImageIO.read(new FileInputStream("Graphics\\" + imageFile));
        }
        catch(Exception e){
             log.log(Level.SEVERE, e.toString(), e);
             ErrorHandle.writeTo("Error at setting entity image: " + e + " " + imageFile);
        }
    } 
     
    /**
     * This method updates the Position of the Entity based on it's velocities, then renders the Entity on the frame.
     * @param g - the Graphics class to draw the sprite with
     */
    public void render(Graphics g){
            this.setXPosition(this.getXPosition() + this.getXVelocity());
            this.setYPosition(this.getYPosition() + this.getYVelocity());
            g.drawImage(image, position.getX(), position.getY(), null);
            //rendered red boxes for testing purposes
            //g.setColor(Color.RED);
            //g.drawRect(position.getX(), position.getY(), width, height);
    }     
}
