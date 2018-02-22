package trapomino;

import java.awt.Graphics;
import java.util.logging.Logger;

/**
 * This class renders and checks for interactions with the buttons on the main menu.
 * The class will also render the background and change the game state when a button is pressed.
 * @author Jack Baumann
 */
public class MainMenu {
    private static final Logger log = Logger.getLogger(MainMenu.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static Entity mainMenu = new Entity(0,0,0,0,"city.png"),  
                        storyGame = new Entity(400,50,335, 75,"Buttons\\storyModeButton.png"),
                      endlessGame = new Entity(400,150,335, 75,"Buttons\\endlessModeButton.png"), 
                          options = new Entity(400,250,335, 75,"Buttons\\optionsButton.png"),
                          credits = new Entity(400,350,335, 75,"Buttons\\creditsButton.png"),
                     instructions = new Entity(400,450,335, 75,"Buttons\\instructionsButton.png"),
                             exit = new Entity(400,550,335, 75,"Buttons\\exitButton.png");

    public static String musicName = "menu_music.wav";

    /**
     * This method renders the background of the frame and all of the buttons on the MainMenu.
     * @param g - the Graphics class used to draw the background Image
     */
    public static void render(Graphics g){
            g.drawImage(mainMenu.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                    Trapomino.HEIGHT * Trapomino.SCALE, null);
            storyGame.render(g);
            endlessGame.render(g);
            options.render(g);
            credits.render(g);
            instructions.render(g);
            exit.render(g);
    }

    /**
     * This method checks to see if the user has clicked any of the buttons on MainMenu, 
     * if that is the case, it plays a sound and changes to the corresponding frame.
     * If exit is clicked, the program will terminate.
     */
    public void run(){
        Trapomino.setMusic(musicName);
        if(storyGame.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.LEVELSELECT);
        }
        else if(endlessGame.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.INFINITEMENU);
        }
        else if(instructions.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.INSTRUCTIONS);
        }
        else if(options.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.OPTIONS);
        }
        else if(credits.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.CREDITS);
        }
        else if(exit.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            System.exit(1);
        }
    }
}