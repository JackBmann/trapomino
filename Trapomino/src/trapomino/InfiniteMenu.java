package trapomino;

import java.awt.Graphics;
import java.util.logging.Logger;

/**
 * This class renders the Entities that are part of the InfiniteGame menu frame linked to the MainMenu.
 * @author Micheal Peterson
 */
public class InfiniteMenu {
    private static final Logger log = Logger.getLogger(Instructions.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static Entity background = new Entity(0,0,0,0,"city.png"),
                            newGame = new Entity(400,200,335,75, "Buttons\\newGameButton.png"),
                           loadGame = new Entity(400,300,335, 75,"Buttons\\loadGameButton.png"), 
                         highScores = new Entity(400,400,335, 75,"Buttons\\highScoresButton.png"),
                     mainMenuButton = new Entity(400, 500, 335, 75, "Buttons\\returnButton.png");
    
    /**
     * This method renders the background of the frame and the buttons to return to the MainMenu, go to HighScores, and play InfiniteGame.
     * @param g - the Graphics class used to draw with
     */
    public static void render(Graphics g){
        g.drawImage(background.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                Trapomino.HEIGHT * Trapomino.SCALE, null);
        newGame.render(g);
        loadGame.render(g);
        highScores.render(g);
        mainMenuButton.render(g);
    }
    
    /**
     * This method checks to see if the user has clicked the button to change to another state, 
     * if that is the case, it plays a sound and changes the frame to the corresponding state.
     */
    public void run(){
        if(newGame.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.INFINITEGAME);
        }
        if(loadGame.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.infiniteGame = new InfiniteGame(0);
            Trapomino.changeState(Trapomino.STATE.INFINITEGAME);
        }
        if(highScores.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.HIGHSCORES);
        }
        if(mainMenuButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.MAINMENU);
        }
    }
}
