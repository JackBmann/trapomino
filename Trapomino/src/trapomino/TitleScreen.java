package trapomino;

import java.awt.Graphics;
import java.util.logging.Logger;

/**
 * This class manages the animations that show Trapomino's publisher and developers and then renders the TitleScreen's Entities and starts the game's music.
 * @author Jack Baumann
 */
public class TitleScreen{
    private static final Logger log = Logger.getLogger(TitleScreen.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static Entity titleScreen = new Entity(0,0,0,0,"city.png"),
                         devScreen = new Entity(0,0,0,0,"dev.png"),
                         publishScreen = new Entity(0,0,0,0,"publish.png"),
                         title = new Entity(40,450,0,0,"title.png"),
                         startButton = new Entity(400, 400, 335, 75, "Buttons\\startButton.png");
    
    private int waitCounter = 400;
    
    /**
     * This method waits 200 ticks while rendering the Cognitive Thought Media Logo, then waits another 200 ticks while the developer credits are shown.
     * Lastly, this method renders the background of the frame, the Trapomino logo Image, and the button to advance to the MainMenu.
     * @param g - the Graphics class used to draw the Instructions Image
     */
    public void render(Graphics g){
        if(waitCounter > 200){
            publishScreen.render(g);
        }
        else if(waitCounter > 0){
            devScreen.render(g);
        }
        else{
            g.drawImage(titleScreen.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                    Trapomino.HEIGHT * Trapomino.SCALE, null);
            title.render(g);
            startButton.render(g);
        }
    }
    
    /**
     * This method sets the music of the game to the file named "menu_music.wav".
     * This method also checks to see if the user has clicked the button to advance to the MainMenu, 
     * if that is the case, it plays a sound and changes the frame to the MainMenu.
     */
    public void run(){
        waitCounter--;
        Trapomino.setMusic("menu_music.wav");
        if(startButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.MAINMENU);
        }
    }
}
