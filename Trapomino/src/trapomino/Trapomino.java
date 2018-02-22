package trapomino;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Character.toUpperCase;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * This is the main class of Trapomino, it runs the game, initializes the variables, creates the frame, 
 * manages the KeyListener and MouseListener, and alters the states, sounds, and settings of the game.
 * @author Micheal Peterson
 */
public class Trapomino extends Canvas implements Runnable, KeyListener, MouseListener{
    private static final Logger log = Logger.getLogger(Trapomino.class.getName());

    private boolean running=false;

    public static final int WIDTH=600;
    public static final int HEIGHT=350;
    public static final int SCALE=2;

    public static int mouseX;
    public static int mouseY;

    //[Level Beaten][Snake][Penguin][Bird][Monkey][Bear][Lion]
    public static int[][] levels = new int[][]{
                                    {0,2,0,1,0,0,0},
                                    {0,0,0,1,1,1,0},
                                    {0,0,0,2,1,1,0},
                                    {0,1,1,0,0,2,0},
                                    {0,2,0,1,1,0,0},
                                    {0,0,1,2,1,0,0},
                                    {0,1,0,1,2,1,0}
                                    };

    public static boolean[] keys = new boolean[5];

    public final String TITLE="Trapomino";
    private Thread thread;

    private TitleScreen titleScreen;
    private MainMenu mainMenu;
    public static InfiniteGame infiniteGame;
    private InfiniteMenu infiniteMenu;
    public static StoryGame storyGame;
    public static LevelSelect levelSelect;
    public static HighScores highScores;
    private Options options;
    private Instructions instructions;
    private Credits credits;

    private static String musicName;
    private static boolean musicChanged;
    private static AudioStream audioStream;
    private static int musicLoopCounter = 3000;
    public static boolean music = true;

    private static AudioStream soundStream;
    public static boolean sound = true;

    public static boolean fps = false;
    private static int framesPS;
    
    /**
     * This enum contains all of the game states.
     */
    public enum STATE{
            TITLE,
            MAINMENU,
            INFINITEGAME,
            INFINITEMENU,
            STORYGAME,
            LEVELSELECT,
            HIGHSCORES,
            OPTIONS,
            INSTRUCTIONS,
            CREDITS
            };

    private static STATE state = STATE.TITLE;
    
    /**
     * This method sets the game to running, adds a MouseListener and KeyListener, and starts the thread.
     */
    public synchronized void start(){
        log.log(Level.INFO, "Thread Started");
        if(running)
            return;

        running = true;

        this.addMouseListener(this);
        addKeyListener(this);

        thread = new Thread(this);
        thread.start();
    }

    /**
     * This method sets running to false and terminates the program.
     */
    public synchronized void stop(){
        log.log(Level.INFO, "Thread Stopped");
        if(!running)
            return;

        running = false;
        System.exit(1);
    }
    
    /**
     * This method adds an ErrorHandle, loads the game settings and saved level, 
     * and initializes all of the game states. 
     */
    private void init(){
        log.log(Level.INFO, "Game Initiated");
        requestFocus();
        log.addHandler(ErrorHandle.TrapominoHandler);
        loadSettings();

        titleScreen = new TitleScreen();
        mainMenu = new MainMenu();
        infiniteGame = new InfiniteGame(); 
        infiniteMenu = new InfiniteMenu();
        storyGame = new StoryGame();
        levelSelect = new LevelSelect();
        highScores = new HighScores();
        options = new Options();
        instructions = new Instructions();
        credits = new Credits();
    }
    
    /**
     * This method manages the tick system to make sure that calculations and 
     * processes are synchronized with the program's clock.
     */
    @Override
    public void run() {
        init();
        long lastTime = System.nanoTime();
        final double idealTics = 60.0;
        double ns = 1000000000 / idealTics;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            playMusic();
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer+=1000;
                log.log(Level.FINE, updates + " Ticks, FPS " + frames);
                framesPS = frames;
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }
    
    /**
     * This method runs a frame after its state is set to true.
     */
    private void tick() {
        log.log(Level.FINER, "Game state is currently " + state);
        musicLoopCounter--;
        if(state == STATE.TITLE){
            titleScreen.run();
        }
        else if(state == STATE.MAINMENU){
            mainMenu.run();
        }
        else if(state == STATE.HIGHSCORES){
            highScores.run();
        }
        else if(state == STATE.LEVELSELECT){
            levelSelect.run();
        }
        else if(state == STATE.INSTRUCTIONS){
            instructions.run();
        }
        else if(state == STATE.INFINITEGAME){
            infiniteGame.run();
        }
        else if(state == STATE.INFINITEMENU){
            infiniteMenu.run();
        }
        else if(state == STATE.STORYGAME){
            storyGame.run();
        }
        else if(state == STATE.OPTIONS){
            options.run();
        }
        else if(state == STATE.CREDITS){
            credits.run();
        }
    }
    
    /**
     * This method renders each state's components when that state is active.
     * This method also displays the FPS counter in the top right of the frame.
     */
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null){
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        if(state == STATE.TITLE){
            titleScreen.render(g);
        }
        else if(state == STATE.MAINMENU){
            mainMenu.render(g);
        }
        else if(state == STATE.HIGHSCORES){
            highScores.render(g);
        }
        else if(state == STATE.LEVELSELECT){
            levelSelect.render(g);
        }
        else if(state == STATE.INSTRUCTIONS){
            instructions.render(g);
        }
        else if(state == STATE.INFINITEGAME){
            infiniteGame.render(g);
        }
        else if(state == STATE.INFINITEMENU){
            infiniteMenu.render(g);
        }
        else if(state == STATE.STORYGAME){
            storyGame.render(g);
        }
        else if(state == STATE.OPTIONS){
            options.render(g);
        }
        else if(state == STATE.CREDITS){
            credits.render(g);
        }

        if(fps){
            g.setFont(new Font("Dialog",PLAIN,32));
            g.setColor(Color.RED);
            g.drawString("" + framesPS, 10, 50);
        }

        g.dispose();
        bs.show();
    }
    
    /**
     * This is the main method of the game, it creates and runs Trapomino, sets up the 
     * JFrame and the game's window, creates the ErrorHandle, and starts the game.
     * @param args - the default parameter for the main method.
     */
    public static void main(String[] args) {
        Trapomino game = new Trapomino();

        game.setPreferredSize(new Dimension(WIDTH*SCALE - 10, HEIGHT*SCALE - 10));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        ErrorHandle.create();

        game.start();
    }

    /**
     * This method loads the settings from saveSettings.txt and the current level from saveLevel.txt.
     * The method then stores the level completion status into the levels Array.
     * If this method encounters an error, it will write to the ErrorHandle.
     */
    public static void loadSettings(){
        log.log(Level.INFO, "Settings loaded");
        int loadedLevel = 0;
        try{
            Scanner in = new Scanner(new FileReader("Saves\\saveSettings.txt"));
            String line = in.nextLine();
            music = Integer.parseInt(line.charAt(0)+"") == 1;
            sound = Integer.parseInt(line.charAt(1)+"") == 1;                 
            fps   = Integer.parseInt(line.charAt(2)+"") == 1;
        }
        catch(IOException e){
            log.log(Level.SEVERE, e.toString(), e);
            ErrorHandle.writeTo("Loading Error: " + e);
        }
        //Load saved level
        try{
            Scanner in = new Scanner(new FileReader("Saves\\saveLevel.txt"));
            String line = in.nextLine();
            loadedLevel = Integer.parseInt(line.charAt(0)+"");
        }
        catch(IOException e){
            log.log(Level.SEVERE, e.toString(), e);
            ErrorHandle.writeTo("Loading Error: " + e);
        }
        for(int i = 0; i < loadedLevel; i++){
            levels[i][0] = 1;
        }
    }
    
    /**
     * This method changes the state of the game to the given state.
     * @param state - The state or the current screen of the game
     */
    public static void changeState(STATE state){
        clearMouse();
        Trapomino.state = state;
        log.log(Level.INFO, "State changed " + state.name());
    }

    /**
     * This method will change the music if the music timer has expired or 
     * the music was changed, as long as the music is not muted.
     * If the above is met, the method will reset the timer, play the new audio with the 
     * current musicName as found in the Music folder, and set musicChanged to false again.
     * If an error is encountered while changing the music, it will write to the ErrorHandle.
     */
    public void playMusic(){
        if((musicChanged || musicLoopCounter < 0) && music){
            musicLoopCounter = 3000;
            AudioPlayer.player.stop(audioStream);
            if(musicName != null){
                try{
                    InputStream in = new FileInputStream(new File("Music\\" + musicName));
                    audioStream = new AudioStream(in);
                    AudioPlayer.player.start(audioStream);
                    log.log(Level.INFO,"Music " + musicName + " played");
                    musicChanged = false;
                }
                catch(IOException e){
                    log.log(Level.SEVERE, e.toString(), e);
                    ErrorHandle.writeTo(e.toString());
                }
           }
        }
    }
    
    /**
     * This method will stop the music from playing.
     */
    public static void stopMusic(){
        log.log(Level.INFO, "Music stopped");
        AudioPlayer.player.stop(audioStream);
    }
    
    /**
     * This method will set the music to the music of the given name.
     * @param musicName - the name of the file of the music to set
     */
    public static void setMusic(String musicName){
        if(Trapomino.musicName != musicName && music){
            musicChanged = true;
            Trapomino.musicName = musicName;
        }
    }

    /**
     * This method will play the sound with the given name as found 
     * in the Music folder, as long as the sound is not muted.
     * If an error is encountered while playing the sound, it will write to the ErrorHandle.
     * @param soundName - the file name of the sound as found in the Music folder
     */
    public static void playSound(String soundName){
        if(soundName != null && sound){
            try{
                InputStream in = new FileInputStream(new File("Music\\" + soundName));
                soundStream = new AudioStream(in);
                AudioPlayer.player.start(soundStream);
                log.log(Level.INFO,"Sound " + soundName + " played");
            }
            catch(IOException e){
                log.log(Level.SEVERE, e.toString(), e);
                ErrorHandle.writeTo(e.toString());
            }
        }
    }

    /**
     * This method overrides the KeyListener method so the class can be implemented.
     * @param ke - the KeyEvent of the typed key
     */
    @Override
    public void keyTyped(KeyEvent ke) {}

    /**
     * This method returns the state of the keys for the KeyListener.
     * @return the state of each key
     */
    public static boolean[] getKeys(){
        return keys;
    }

    /**
     * This method checks the keys that are used by the game 
     * and sets the key that has been pressed to true.
     * This method overrides the KeyListener method so the class can be implemented.
     * @param ke - the KeyEvent of the pressed key
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        log.log(Level.FINE, "Key Pressed " + ke, ke);
        switch(toUpperCase(ke.getKeyChar()))
        {
            //Move Left   
            case 'A' : keys[0]=true; break;
            //Move Right
            case 'D' : keys[1]=true; break;
            //Rotate
            case 'W' : keys[2]=true; break;
            //speed up
            case 'S' : keys[3]=true; break;
            //paused
            case 'P' : keys[4]=true; break;
        }
    }

    /**
     * This method checks the keys that are used by the game 
     * and sets the key that has been released to false.
     * This method overrides the KeyListener method so the class can be implemented.
     * @param ke - the KeyEvent of the released key
     */
    @Override
    public void keyReleased(KeyEvent ke) {
        log.log(Level.FINE, "Key Released " + ke, ke);
        switch(toUpperCase(ke.getKeyChar()))
        {
            //Move Left   
            case 'A' : keys[0]=false; break;
            //Move Right
            case 'D' : keys[1]=false; break;
            //Rotate
            case 'W' : keys[2]=false; break;
            //speed up
            case 'S' : keys[3]=false; break;
            //paused
            case 'P' : keys[4]=false; break;
        }
    }

    /**
     * This method returns the X coordinate of the mouse on screen for the MouseListener.
     * @return the X coordinate of the mouse on screen
     */
    public static int getMouseX(){
        return mouseX;
    }

    /**
     * This method returns the Y coordinate of the mouse on screen for the MouseListener.
     * @return the Y coordinate of the mouse on screen
     */
    public static int getMouseY(){
        return mouseY;
    }

    /**
     * This method sets the X and Y coordinates of the mouse to the ones in the MouseEvent.
     * This method overrides the MouseListener method so the class can be implemented.
     * @param me - MouseEvent used to get mouse info
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        log.log(Level.INFO, "X:" + me.getX() + ", Y:" + me.getY());
        mouseX = me.getX();
        mouseY = me.getY();
    }
    
    /**
     * This method clears the mouseX and mouseY locations so that buttons are not clicked across states
     */
    public static void clearMouse(){
        mouseX = -1000000; mouseY = -1000000;
    }
    
    /**
     * This method overrides the MouseListener method so the class can be implemented.
     * @param me - the MouseEvent when the mouse is pressed
     */
    @Override
    public void mousePressed(MouseEvent me) {}

    /**
     * This method overrides the MouseListener method so the class can be implemented.
     * @param me - the MouseEvent when the mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent me) {}

    /**
     * This method overrides the MouseListener method so the class can be implemented.
     * @param me - the MouseEvent when the mouse is entered
     */
    @Override
    public void mouseEntered(MouseEvent me) {}

    /**
     * This method overrides the MouseListener method so the class can be implemented.
     * @param me - the MouseEvent when the mouse is exited
     */
    @Override
    public void mouseExited(MouseEvent me) {}
}