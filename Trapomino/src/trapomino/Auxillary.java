package trapomino;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This class reads the sprites into the game and assigns them to their coordinating game objects.
 * This class will then return the proper image to any class that calls for use of a sprite by it's name.
 * Finally, this class returns the shape of an Object on the Grid based on the Object's name.
 * @author Micheal Peterson
 */
public class Auxillary {
    private static final Logger log = Logger.getLogger(Auxillary.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static Image I, O, T, L, J, Z, S, None, Cage, Clear,

                        Snake, SnakeR, SnakeTrapped, 

                        Penguin, PenguinR, PenguinTrapped, Egg,

                        Lion, LionTrapped,

                        Bird, Bird2, Bird3, BirdR, Bird2R, Bird3R, BirdTrapped,

                        Monkey, MonkeyTrapped,

                        Bear, BearTrapped;
    
    /**
     * This constructor reads all of the Images from the Graphics folder and assigns them to their relating names.
     * If the program encounters an Exception during this process, it will write the error in the ErrorLogger file.
     */
    public Auxillary(){
        try{
            I = ImageIO.read(new File("Graphics\\Blocks\\iBlock.png"));
            O = ImageIO.read(new File("Graphics\\Blocks\\oBlock.png"));
            T = ImageIO.read(new File("Graphics\\Blocks\\tBlock.png"));
            L = ImageIO.read(new File("Graphics\\Blocks\\lBlock.png"));
            J = ImageIO.read(new File("Graphics\\Blocks\\jBlock.png"));
            Z = ImageIO.read(new File("Graphics\\Blocks\\zBlock.png"));
            S = ImageIO.read(new File("Graphics\\Blocks\\sBlock.png"));
            
            Cage = ImageIO.read(new File("Graphics\\Blocks\\trapped.png"));
            
            Clear = ImageIO.read(new File("Graphics\\Blocks\\Clear.png"));
            
            Snake = ImageIO.read(new File("Graphics\\Animals\\Snake\\snake.png"));
            SnakeR = ImageIO.read(new File("Graphics\\Animals\\Snake\\snakeR.png"));
            SnakeTrapped = ImageIO.read(new File("Graphics\\Animals\\Snake\\SnakeTrapped.png"));
            
            Penguin = ImageIO.read(new File("Graphics\\Animals\\Penguin\\Penguin.png"));
            PenguinR = ImageIO.read(new File("Graphics\\Animals\\Penguin\\PenguinR.png"));
            PenguinTrapped = ImageIO.read(new File("Graphics\\Animals\\Penguin\\PenguinTrapped.png"));
            Egg = ImageIO.read(new File("Graphics\\Animals\\Penguin\\Egg.png"));
            
            Bird = ImageIO.read(new File("Graphics\\Animals\\Bird\\Bird.png"));
            Bird2 = ImageIO.read(new File("Graphics\\Animals\\Bird\\Bird2.png"));
            Bird3 = ImageIO.read(new File("Graphics\\Animals\\Bird\\Bird3.png"));
            
            BirdR = ImageIO.read(new File("Graphics\\Animals\\Bird\\BirdR.png"));
            Bird2R = ImageIO.read(new File("Graphics\\Animals\\Bird\\Bird2R.png"));
            Bird3R = ImageIO.read(new File("Graphics\\Animals\\Bird\\Bird3R.png"));
            
            BirdTrapped = ImageIO.read(new File("Graphics\\Animals\\Bird\\BirdTrapped.png"));
            
            Monkey = ImageIO.read(new File("Graphics\\Animals\\Monkey\\Monkey.png"));
            MonkeyTrapped = ImageIO.read(new File("Graphics\\Animals\\Monkey\\MonkeyTrapped.png"));
            
            Bear = ImageIO.read(new File("Graphics\\Animals\\Bear\\Bear.png"));
            BearTrapped = ImageIO.read(new File("Graphics\\Animals\\Bear\\BearTrapped.png"));
        
        }
        catch(IOException e){
            //errorLogging and print statement
            ErrorHandle.writeTo("Error at setting auxillary image: " + e);
        }
    }
    
    /**
     * This method returns the Image of the sprite corresponding to the given shape.
     * @param shape - the shape of the requested Block or Animal in the Grid
     * @return the Image of the shape's sprite or null if the shape has no Image
     */
    public static Image getImage(Grid.SHAPE shape){
        switch(shape){
            case I:
                return I;
            case O:
                return O;
            case T:
                return T;
            case L:
                return L;
            case J:
                return J;
            case Z:
                return Z;
            case S:
                return S;
            case Trapped:
                return Cage;
                
            case Snake:
                return Snake;
            case SnakeR:
                return SnakeR;
            case SnakeTrapped:
                return SnakeTrapped;
                
            case Lion:
                return Lion;
            case LionTrapped:
                return LionTrapped;
            case Bear:
                return Bear;
            case BearTrapped:
                return BearTrapped;
                
            case Bird:
                return Bird;
            case Bird2:
                return Bird2;
            case Bird3:
                return Bird3;
              
            case BirdR:
                return BirdR;
            case Bird2R:
                return Bird2R;
            case Bird3R:
                return Bird3R;
                
            case BirdTrapped:
                return BirdTrapped;
            case Monkey:
                return Monkey;
            case MonkeyTrapped:
                return MonkeyTrapped;
                
            case Penguin:
                return Penguin;
            case PenguinR:
                return PenguinR;
            case PenguinTrapped:
                return PenguinTrapped;
            case Egg:
                return Egg;
        }
        return null;
    }
    
    /**
     * This method returns the shape of a Block or Animal on the Grid based on its name.
     * @param shape - the name of a Block or Animal as a string
     * @return the shape of a Block/Animal or no shape if the name has no corresponding Image
     */
    public static Grid.SHAPE stringToShape(String shape){
        switch(shape){
            case "I":
                return Grid.SHAPE.I;
            case "O":
                return Grid.SHAPE.O;
            case "T":
                return Grid.SHAPE.T;
            case "L":
                return Grid.SHAPE.L;
            case "J":
                return Grid.SHAPE.J;
            case "Z":
                return Grid.SHAPE.Z;
            case "S":
                return Grid.SHAPE.S;
            case "Trapped":
                return Grid.SHAPE.Trapped;
                
            case "Snake":
                return Grid.SHAPE.Snake;
            case "SnakeR":
                return Grid.SHAPE.SnakeR;
            case "SnakeTrapped":
                return Grid.SHAPE.SnakeTrapped;
                
            case "Bear":
                return Grid.SHAPE.Bear;
            case "BearTrapped":
                return Grid.SHAPE.BearTrapped;
                
            case "Bird":
                return Grid.SHAPE.Bird;
            case "Bird2":
                return Grid.SHAPE.Bird2;
            case "Bird3":
                return Grid.SHAPE.Bird3;
              
            case "BirdR":
                return Grid.SHAPE.BirdR;
            case "Bird2R":
                return Grid.SHAPE.Bird2R;
            case "Bird3R":
                return Grid.SHAPE.Bird3R;
                
            case "BirdTrapped":
                return Grid.SHAPE.BirdTrapped;
            case "Monkey":
                return Grid.SHAPE.Monkey;
            case "MonkeyTrapped":
                return Grid.SHAPE.MonkeyTrapped;
                
            case "Penguin":
                return Grid.SHAPE.Penguin;
            case "PenguinR":
                return Grid.SHAPE.PenguinR;
            case "PenguinTrapped":
                return Grid.SHAPE.PenguinTrapped;
            case "Egg":
                return Grid.SHAPE.Egg;
        }
        return Grid.SHAPE.None;
    }
}
