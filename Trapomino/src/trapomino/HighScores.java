package trapomino;

import java.awt.Color;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class renders the Entities that are part of the HighScores frame linked to the InfiniteMenu.
 * @author Micheal Peterson
 */
public class HighScores {
    private static final Logger log = Logger.getLogger(HighScores.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static Entity background = new Entity(0,0,0,0,"city.png"), 
            scoreBoard = new Entity(280,20,0,0,"highScores.png"),
            mainMenuButton = new Entity(385, 532, 335, 75, "Buttons\\returnButton.png");
    
    public static ArrayList<Score> scores = new ArrayList<Score>();
    
    /**
     * This constructor loads the scores from the highScores.txt file.
     */
    public HighScores(){
        loadScores();
    }
    
    /**
     * This class stores and compares the scores.
     */
    public class Score implements Comparable{
        int score;
        String date;
        
        /**
         * This method stores the given variables.
         * @param score - the score to store
         * @param date - the date the score was set
         */
        public Score(int score, String date){
            this.score = score;
            this.date = date;
        }

        /**
         * This method compares scores to each other.
         * @param obj - the score to compare to this score
         * @return the value of the given score minus the value of this score
         */
        @Override
        public int compareTo(Object obj) {
            Score score2 = (Score) obj;
            return score2.score - this.score;
        }
        
    }
    
    /**
     * This method renders the background of the frame, the buttons to return to 
     * the MainMenu, the scoreboard, and the recorded scores.
     * @param g - the Graphics class used to draw with
     */
    public static void render(Graphics g){
        g.drawImage(background.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                Trapomino.HEIGHT * Trapomino.SCALE, null);
        int place = 1;
        scoreBoard.render(g);
        int x = 0;
        for(Score s: scores){
            if(x < 10)
            {
                g.setFont(new Font("Dialog",PLAIN,24));
                g.setColor(Color.GREEN);
                g.drawString(place+"",    300, 70 + place*40);
                g.drawString(s.score+"",  400, 70 + place*40);
                g.drawString(s.date,      680, 70 + place*40);
                place++;
            }
            x++;
        }
        mainMenuButton.render(g);
    }
    
    /**
     * This method checks to see if the user has clicked the button to change to return to the InfiniteMenu, 
     * if that is the case, it plays a sound and changes the state to the InfiniteMenu.
     */
    public void run(){
        if(mainMenuButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            Trapomino.playSound("button_click.wav");
            Trapomino.changeState(Trapomino.STATE.INFINITEMENU);
        }
    }
    
    /**
     * This method adds the given score to the ArrayList of scores, sorts the 
     * ArrayList in descending order, then writes the scores to highScores.txt.
     * @param score  - the new score to add
     * @param date - the date the score was set
     */
    public void addScore(int score, String date){
        scores.add(new Score(score, date));
        //sorts the scores greatest to least
        Collections.sort(scores); 
        //writes to a file
        writeScores();
    }
    
    /**
     * This method reads the scores from the highScores.txt file to the ArrayList scores so they can be rendered. 
     * If the method encounters an error it will log it in the ErrorHandle.
     */
    public void loadScores(){
        try{
            Scanner in = new Scanner(new FileReader("Saves\\highScores.txt"));
           
            while(in.hasNext()){
               String[] score = in.nextLine().split(",");
               scores.add(new Score(Integer.parseInt(score[0]), score[1]));
            }
        }
        catch(IOException e){
            log.log(Level.SEVERE, e.toString(), e);
            ErrorHandle.writeTo("Loading Error: " + e);
        }
    }
    
    /**
     * This method writes the ArrayList scores to highScores.txt so it can be saved and read from.
     * If the method encounters an error it will log it in the ErrorHandle.
     */
    public void writeScores(){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("Saves\\highScores.txt"));
            
            for(Score s: scores){
                out.write(s.score + "," + s.date);
                out.newLine();
            }
            out.close();
        }   
        catch(IOException e){
            log.log(Level.SEVERE, e.toString(), e);
            ErrorHandle.writeTo("Saving Error: " + e);
        }
    }
}