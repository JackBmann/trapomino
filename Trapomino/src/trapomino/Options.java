package trapomino;

import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class renders the Entities that are part of the Options frame linked to the MainMenu.
 * @author Jack Baumann
 */
public class Options {
    private static final Logger log = Logger.getLogger(Options.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static Entity background = new Entity(0,0,0,0,"city.png"),
                     mainMenuButton = new Entity(400, 500, 335, 75, "Buttons\\returnButton.png"),
                        musicButton = new Entity(350,105,60,60,"Buttons\\musicOn.png"),
                        soundButton = new Entity(350,205,60,60,"Buttons\\soundOn.png"),
                          fpsButton = new Entity(350,305,60,60,"Buttons\\fpsOff.png"),
                              music = new Entity(420,100,0,0,"toggleMusic.png"),
                              sound = new Entity(420,200,0,0,"toggleSound.png"),
                                fps = new Entity(420,300,0,0,"toggleFPS.png");
            
    public static String musicName = "menu_music.wav";
    
    /**
     * This constructor sets the image of the options to show whether the option is on or off.
     */
    public Options(){
        if(Trapomino.music)
            musicButton.setImage("Buttons\\musicOn.png");
        else
            musicButton.setImage("Buttons\\musicOff.png");
        
        if(Trapomino.sound)
            soundButton.setImage("Buttons\\soundOn.png");
        else
            soundButton.setImage("Buttons\\soundOff.png");
                
       if(Trapomino.fps)
            fpsButton.setImage("Buttons\\fpsOn.png");
        else
            fpsButton.setImage("Buttons\\fpsOff.png");
    }
    
    /**
     * This method renders the background of the frame, the Options buttons, and the button to return to the MainMenu.
     * @param g - the Graphics class used to draw with
     */
    public static void render(Graphics g){
        g.drawImage(background.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                Trapomino.HEIGHT * Trapomino.SCALE, null);
        
        mainMenuButton.render(g);
        
        musicButton.render(g);
        soundButton.render(g);
        fpsButton.render(g);
        
        music.render(g);
        sound.render(g);
        fps.render(g);
    }
    
    /**
     * This method checks to see if the user has clicked the button to return to the MainMenu, 
     * if that is the case, it changes the frame to the MainMenu.
     */
    public void run(){
        Trapomino.setMusic(musicName);
        if(mainMenuButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            saveSettings();
            Trapomino.changeState(Trapomino.STATE.MAINMENU);
        }
        else if(musicButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            if(!Trapomino.music){
                musicButton.setImage("Buttons\\musicOn.png");
                Trapomino.music = true;
                Trapomino.setMusic(musicName);
            }
            else{
                musicButton.setImage("Buttons\\musicOff.png");
                Trapomino.music = false;
                Trapomino.setMusic(null);
                Trapomino.stopMusic();
            }
            Trapomino.clearMouse();
        }
        else if(soundButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            if(!Trapomino.sound){
                soundButton.setImage("Buttons\\soundOn.png");
                Trapomino.sound = true;
            }
            else{
                soundButton.setImage("Buttons\\soundOff.png");
                Trapomino.sound = false;
            }
            Trapomino.clearMouse();
        }
        else if(fpsButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            if(!Trapomino.fps){
                fpsButton.setImage("Buttons\\fpsOn.png");
                Trapomino.fps = true;
            }
            else{
                fpsButton.setImage("Buttons\\fpsOff.png");
                Trapomino.fps = false;
            }
            Trapomino.clearMouse();
        }
    }
    
    /**
     * This method saves the Options data to the file saveSettings.txt by writing their state to the file.
     * If the program encounters an error, it will log it to the ErrorHandle log.
     */
    private static void saveSettings(){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("Saves\\saveSettings.txt"));
            int music = Trapomino.music ? 1 : 0;
            int sound = Trapomino.sound ? 1 : 0;
            int fps   = Trapomino.fps   ? 1 : 0;
            
            out.write(music + "");
            out.write(sound + "");
            out.write(fps + "");

            out.close();
        }   
        catch(IOException e){
            log.log(Level.SEVERE, e.toString(), e);
            ErrorHandle.writeTo("Saving Error: " + e);
        }
    }
}
