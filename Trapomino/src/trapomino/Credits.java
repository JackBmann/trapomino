package trapomino;

import java.awt.Graphics;
import java.util.logging.Logger;

/**
 * This class renders the Entities that are part of the Credits frame linked to the MainMenu.
 * @author Jack Bazumann
 */
public class Credits {
    private static final Logger log = Logger.getLogger(Credits.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static Entity background = new Entity(0,0,0,0,"city.png"), 
                        credits = new Entity(280,150,0,0,"credits.png"),
                        mainMenuButton = new Entity(400, 500, 335, 75, "Buttons\\returnButton.png");
    
    /**
     * This method renders the background of the frame, the Credits Image, and the button to return to the MainMenu.
     * @param g - the Graphics class used to draw the Credits Image
     */
    public static void render(Graphics g){
        g.drawImage(background.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                Trapomino.HEIGHT * Trapomino.SCALE, null);
        credits.render(g);
        mainMenuButton.render(g);
    }
    
   /**
     * This method first changes the music, then checks to see if the user has clicked the button to return to 
     * the MainMenu, if that is the case, it plays a sound and changes the frame to the MainMenu.
     */
    public void run(){
        Trapomino.setMusic("credit_music.wav");
        
        if(mainMenuButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.MAINMENU);
        }
    }
}
