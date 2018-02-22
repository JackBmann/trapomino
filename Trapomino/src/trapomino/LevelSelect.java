package trapomino;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import static trapomino.Trapomino.levels;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class renders the Entities that are part of the LevelSelect menu frame linked to the MainMenu.
 * @author Patrick Edelen
 */
class LevelSelect {
    private static final Logger log = Logger.getLogger(LevelSelect.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static Entity background = new Entity(0,0,0,0,"city.png"), 
                               cont = new Entity(260,500,335,75,"Buttons\\continueButton.png"),
                     mainMenuButton = new Entity(640, 500, 335, 75, "Buttons\\returnButton.png"),
                        resetButton = new Entity(445, 400, 335, 75, "Buttons\\resetButton.png"),
                                map = new Entity(0,0,0,0,"map.png"),
                         animation1 = new Entity(0,0,0,0,"animation1.png"),
                         animation2 = new Entity(0,0,0,0,"animation2.png"),
                         animation3 = new Entity(0,0,0,0,"animation3.png");
    
    private static int animation=0;
    
    private static final Color mapColor = new Color(0,255,192);
    
    /**
     * This method renders the background of the frame, the level map, and the buttons to return 
     * to the MainMenu, reset level progression, and play the next StoryGame level.
     * @param g - the Graphics class used to draw with
     */
    public static void render(Graphics g){
        g.drawImage(map.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE+10, 
                Trapomino.HEIGHT * Trapomino.SCALE+10, null);
        cont.render(g);
        mainMenuButton.render(g);
        resetButton.render(g);
            
            //Print rectangles for levels
        
        //Level 1 to 2 rectangle
        
        if(levels[0][0]==1){
            g.setColor(Color.RED);
            g.fillRect(210, 273, 215, 18);
        }
        if(levels[1][0]==1){
            g.setColor(Color.YELLOW);
            g.fillRect(436, 202, 18, 65);
        }
        if(levels[2][0]==1){
            g.setColor(Color.BLUE);
            g.fillRect(464, 178, 223, 18);
        }
        if(levels[3][0]==1){
            g.setColor(Color.GREEN);
            g.fillRect(696, 201, 18, 113);
        }
        if(levels[4][0]==1){
            g.setColor(Color.ORANGE);
            g.fillRect(725, 320, 220, 18);
        }
        if(levels[5][0]==1){
            g.setColor(mapColor);
            g.fillRect(956, 344, 18, 81);
        }
        
        
        if(levels[0][0]==0){
            if(animation<=200){
                        g.drawImage(animation1.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE+10, 
                Trapomino.HEIGHT * Trapomino.SCALE+10, null);
            }
            if(animation>200&&animation<=400){
                        g.drawImage(animation2.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE+10, 
                Trapomino.HEIGHT * Trapomino.SCALE+10, null);
            }
            if(animation>400&&animation<600){
                        g.drawImage(animation3.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE+10, 
                Trapomino.HEIGHT * Trapomino.SCALE+10, null);
            }
        }
    }
    
    /**
     * This method checks to see if the user has clicked the button to change to another state, 
     * if that is the case, it plays a sound and changes the frame to the corresponding state.
     * This method also saves the level progression to the file saveLevel.txt and 
     * resets to level 0 when the reset button is pressed.
     * If this method encounters an error, it will write it to the ErrorHandle.
     */
    public void run(){
        
        if(levels[0][0]==0&&animation<600){
            animation++;
        }
        if(cont.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
             Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.STORYGAME);
        }
        if(mainMenuButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
             Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.MAINMENU);
        }
        if(resetButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
             Trapomino.playSound("button_click.wav");
            for(int i=0; i<7;i++){
                levels[i][0]=0;
            }
            try{
                BufferedWriter out = new BufferedWriter(new FileWriter("Saves\\saveLevel.txt"));

                out.write((0) + "");
                log.log(Level.INFO, "Reset Levels");
                out.close();
            }   
            catch(IOException e){
                log.log(Level.SEVERE, e.toString(), e);
                ErrorHandle.writeTo("Saving Error: " + e);
            }
            Trapomino.loadSettings();
            Trapomino.clearMouse();
        }
    }
}