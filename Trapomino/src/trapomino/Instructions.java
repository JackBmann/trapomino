package trapomino;

import java.awt.Graphics;
import java.util.logging.Logger;

/**
 * This class renders the Entities that are part of the Instructions/Tutorial frame linked to the MainMenu.
 * @author Jack Baumann
 */
public class Instructions {
    private static final Logger log = Logger.getLogger(Instructions.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static Entity background = new Entity(0,0,0,0,"city.png"), 
            instructions = new Entity(280,20,0,0,"Instructions.png"),
            mainMenuButton = new Entity(385, 532, 335, 75, "Buttons\\returnButton.png");
    
    /**
     * This method renders the background of the frame, the Instructions Image, and the button to return to the MainMenu.
     * @param g - the Graphics class used to draw the Instructions Image
     */
    public static void render(Graphics g){
        g.drawImage(background.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                Trapomino.HEIGHT * Trapomino.SCALE, null);
        instructions.render(g);
        mainMenuButton.render(g);
    }
    
    /**
     * This method checks to see if the user has clicked the button to return to the MainMenu, 
     * if that is the case, it plays a sound and changes the frame to the MainMenu.
     */
    public void run(){
        if(mainMenuButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.MAINMENU);
        }
    }
}
