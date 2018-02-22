package trapomino;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;

/**
 * This class creates a BufferedWriter file to log any encountered errors into as well as all of the individual file handlers and formatters.
 * Whenever a class requests to log an error to the ErrorLog.txt file, this class will write to it, then close the BufferedWriter.
 * @author Jack Baumann
 */
public class ErrorHandle {
    private static final Logger log = Logger.getLogger(ErrorHandle.class.getName());{log.addHandler(ErrorHandle.basicHandler);}
    public static BufferedWriter bwFile;
    public static FileHandler basicHandler;
    public static FileHandler animalHandler;
    public static FileHandler GridHandler;
    public static FileHandler MapHandler;
    public static FileHandler TetrominoHandler;
    public static FileHandler EntityHandler;
    public static FileHandler infiniteGameHandler;
    public static FileHandler storyGameHandler;
    public static FileHandler TrapominoHandler;
    
    /**
     * This method tries to create a BufferedWriter out of a file named "ErrorLog.txt".
     * It also creates the log handlers and formatter.
     */
    public static void create(){
        try
        {
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            bwFile = new BufferedWriter(new FileWriter("Logs\\ErrorLog.txt"));
            
            basicHandler = new FileHandler("Logs\\BasicLog.log");
            basicHandler.setFormatter(simpleFormatter);
            
            infiniteGameHandler = new FileHandler("Logs\\InfiniteGameLog.log");
            infiniteGameHandler.setFormatter(simpleFormatter);
            
            storyGameHandler = new FileHandler("Logs\\StoryGameLog.log");
            storyGameHandler.setFormatter(simpleFormatter);
            
            TrapominoHandler = new FileHandler("Logs\\TrapominoLog.log");
            TrapominoHandler.setFormatter(simpleFormatter);

            animalHandler = new FileHandler("Logs\\AnimalLog.log");
            animalHandler.setFormatter(simpleFormatter);
            
            GridHandler = new FileHandler("Logs\\GridLog.log");
            GridHandler.setFormatter(simpleFormatter);
            
            TetrominoHandler = new FileHandler("Logs\\GridLog.log");
            TetrominoHandler.setFormatter(simpleFormatter);
            
            MapHandler = new FileHandler("Logs\\MapLog.log");
            MapHandler.setFormatter(simpleFormatter);
            
            EntityHandler = new FileHandler("Logs\\EntityLog.log");
            EntityHandler.setFormatter(simpleFormatter);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error at Errorlog.create(): " + e);
        }
    }
    
    /**
     * This method writes the error text passed to it into the ErrorLog.  It then moves to the next line, closes the BufferedWriter, and stops the game.
     * @param text - the text describing the error to write into the error logging file
     */
    public static void writeTo(String text)
    {
        try
        {
            bwFile.write(text);
            bwFile.newLine();
            JOptionPane.showMessageDialog(null, text);
            close();
            System.exit(0);
        }
        catch(Exception e)
        { 
            JOptionPane.showMessageDialog(null, "Error at Errorlog.writeTo(): " +  e);
        }
    }
    
    /**
     * This method closes the BufferedWriter.
     */
    public static void close()
    {
        try
        {
            bwFile.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error at Errorlog.close(): " +  e);
        }
    }
}
