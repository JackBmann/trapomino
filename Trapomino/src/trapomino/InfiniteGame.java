package trapomino;

import java.awt.Color;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

/**
 * This class manages the Endless game mode in Trapomino.
 * @author Micheal Peterson
 */
public class InfiniteGame{
    private static final Logger log = Logger.getLogger(InfiniteGame.class.getName());{log.addHandler(ErrorHandle.infiniteGameHandler);}
    private static Grid grid;
    
    private static Tetromino currentBlock, nextBlock;

    private static ArrayList<Animal> animals = new ArrayList<Animal>();
    private static ArrayList<Animal> animalsTrapped = new ArrayList<Animal>();
    
    private static ArrayList<Grid.SHAPE> spawnAnimals = new ArrayList<Grid.SHAPE>();
    
    private static String music = "game_scary_music.wav";
	
    public static Entity titleScreen = new Entity(0,0,0,0,"city.png"),
                          loseEntity = new Entity(280,150,480,360,"lose.png"),
                              border = new Entity(200,-140,0,0,"GridBorder.png"),
                         pauseButton = new Entity(790,610,335,75),
                     pauseBackground = new Entity(330, 10,0,0,"Grid2.png"),
                          exitButton = new Entity(370,300,335,75,"Buttons\\exitButton.png"),
                          saveButton = new Entity(370,200,335,75,"Buttons\\saveButton.png");
    
    private static int counterY = 0, counterX = 0, counterR = 0, waitCounter = 10;

    private boolean paused = false, lose = false;
    
    private boolean clearBlocks = false;
    private ArrayList<Position> clearPositions = new ArrayList<Position>();
    private ArrayList<Integer> clearedRows;
    
    private int score;
    private int spawnCounter = 0;
    
    Random rand = new Random();
    
    /**
     * This constructor starts a new game of the InfiniteGame mode.
     */
    public InfiniteGame(){
        newGame();
    }
    
    /**
     * This constructor loads the saved game of the InfiniteGame mode.
     * @param x - any int so as to specify this constructor
     */
    public InfiniteGame(int x){
        newGame();
        loadGame();
    }
    
    /**
     * This method manages the movement in the grid as long as the game is not paused.
     * This method moves the current Tetromino down, left, and right, rotates the current Tetromino, 
     * checks if the user is pressing the button to speed its falling, 
     * and calls the move method of all the Animals in the grid.
     * It also checks to see if the pause button is clicked, if so it will render and manage the pause menu.
     * This method also sets the music, renders the lose menu, and adds scores to HighScores.
     */
    public void run() {
        if(!paused){
            //iterates counter for X movement
            counterY++;
            //iterates counter for Y movement
            counterX++;
            //iterates counter for rotation movement
            counterR++;

            if(currentBlock == null){
                nextBlock = new Tetromino();
                newBlock();
            }

            if(Trapomino.getKeys()[2] && counterR>20){
                currentBlock.rotate();
                counterR = 0;
            }

            //Downwards movement
            //Speed Up
            if(Trapomino.getKeys()[3]){
                if(!currentBlock.moveDown()){
                    Trapomino.playSound("block_dropped_new.wav");
                    if(unsupportedBlock(currentBlock)){
                        currentBlock.removeBlocksFromGrid();
                        clearBlocks = true;
                        paused = true;
                        clearPositions.addAll(currentBlock.getPositions());
                    }
                    newBlock();
                }
            }
            //Normal Movement
            else if(counterY%30 == 0){
                if(!currentBlock.moveDown()){
                    Trapomino.playSound("block_dropped_new.wav");
                    if(unsupportedBlock(currentBlock)){
                        currentBlock.removeBlocksFromGrid();                       
                        clearBlocks = true;
                        paused = true;
                        clearPositions.addAll(currentBlock.getPositions());
                    }
                    newBlock();
                }
            }

            if(Trapomino.getKeys()[0] && counterX>10)
            {
                if(currentBlock.moveRight())
                {
                    counterX = 0;
                }
            }
            else if(Trapomino.getKeys()[1] && counterX>10){
                if(currentBlock.moveLeft()){
                    counterX = 0;
                }
            } 

            for(Animal a: animals){
                a.move();
            }
        }
        else if(paused){
            if(clearBlocks){
                waitCounter--;
                if(waitCounter == 0){
                    paused = false;
                    clearBlocks = false;
                    clearPositions.clear();
                    waitCounter = 10;
                }     
            }
            else{
               if(saveButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
                    Trapomino.playSound("button_click.wav"); 
                    save();
                    Trapomino.clearMouse();
               } 
               if(exitButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
                   Trapomino.playSound("button_click.wav"); 
                   newGame();
                    Trapomino.clearMouse();
                    paused = false;
                   Trapomino.changeState(Trapomino.STATE.MAINMENU);
               } 
            }
        }
        
        //pausing
        if(Trapomino.getKeys()[4]){
                paused = !paused;
        }
        
        if(lose){
            if(loseEntity.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
                //saves the high Score
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date date = new Date();
                Trapomino.highScores.addScore(score, dateFormat.format(date));
                
                newGame();
                Trapomino.playSound("button_click.wav");
                Trapomino.changeState(Trapomino.STATE.MAINMENU);
            }
        }
        
        if(pauseButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
            paused = !paused;
            Trapomino.playSound("button_click.wav");
            Trapomino.clearMouse();
        }
        
        Trapomino.setMusic(music);
    }
        
    /**
     * This method is called by Trapomino, it renders the background, grid, UI, and pause button.
     * When the game is paused, the method will render the pause menu or the lose screen if the player lost.
     * @param g - the Graphics where the items in this method will be rendered
     */
    public void render(Graphics g) {
        if(!paused){
            //draws the background image
            g.drawImage(titleScreen.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                    Trapomino.HEIGHT * Trapomino.SCALE, null);

            //draws the grid
            grid.render(g);
            //draws border
            border.render(g);
            //draw pausebutton
            pauseButton.render(g);
            
            //draws the nextAnimals
            int x = 0;
            for(Grid.SHAPE s: spawnAnimals){
                g.drawImage(Auxillary.getImage(s), 865, 315 + x*87, 64, 64, null);
                x++;
            }
            
            //renders next block      
            if(nextBlock != null)
                nextBlock.render(g);
            
            //renders the score
            g.setFont(new Font("Dialog",PLAIN,32));
            g.setColor(Color.CYAN);
            g.drawString("" + score, 800, 130);
        }
        else if(paused){
            if(clearBlocks){
                for(Position p:clearPositions){
                    g.drawImage(Auxillary.Clear, p.getX()*grid.GRID_SPACING + grid.GRID_X_LOCATION, 
                                                 p.getY()*grid.GRID_SPACING + grid.GRID_Y_LOCATION, null);
                }
            }
            else if(lose){
               loseEntity.render(g);
            }
            else{
                pauseBackground.render(g);
                exitButton.render(g);
                saveButton.render(g);
            }
        }
    }
    
    /**
     * This method creates a new Tetromino to be added to the grid, spawns a new Animal if 10 blocks have been placed,
     * checks if the user has lost the game, checks to see if any Animals have been trapped, clears any full rows,
     * adds to the score, and generates the next Tetromino.
     */
    private void newBlock(){
        //iterates the spawnCounter so animals spawn every 10 blocks
        spawnCounter++;
        //spawn animals
        if(spawnCounter >= 10){
            spawnAnimals();
            spawnCounter = 0;
        }
        
        if(grid.topEmpty()){ //checks if the top is empty, if not game over
           grid.unionCreate();
           
           for(Animal a: animals){
               if(!a.trapCheck()){
                    log.log(Level.INFO, a.getName() + " Trapped!");
                    
                    score = score + a.getScore();
                    
                    grid.changeToTrapped(new Position(a.getX(),a.getY()));
                    animalsTrapped.add(a);
                }
           }
           
           for(Animal a: animalsTrapped){
                 animals.remove(a);
           }
           animalsTrapped.clear();
           
           clearedRows = grid.clearRows();
           score = score + clearedRows.size()*5;
           if(!clearedRows.isEmpty()){
               for(int y: clearedRows){
                   for(int x = 0; x < grid.GRID_X; x++){
                        clearPositions.add(new Position(x,y));
                   }
               }
               clearBlocks = true;
               paused = true;
           }
           
           currentBlock = new Tetromino(5,1,grid,nextBlock.getShape());
           nextBlock = new Tetromino();
       }
       else{
            lose = true;
            paused = true;
       }
    }
    
    /**
     * This method starts a new game by setting all of the variables to their default values and generating the new Grid.
     * The new game spawns a Snake to begin by default.
     */
    public void newGame(){ 
        animals.clear();
        spawnAnimals.clear();
        currentBlock = null;
        paused = false;
        lose = false;
        spawnCounter = 0;
        score = 0;
        grid = new Grid();
        Snake snake = new Snake(0, grid.GRID_Y-1, grid);
        animals.add(snake);
        nextAnimals();
    }
    
    /**
     * This method loads the game data from the save file called save.txt in the Saves folder.
     * It reads all of the data in the file and saves it to its corresponding variable.
     * If this method encounters an error while reading from the file, it will write to the ErrorHandle.
     */
    public void loadGame(){
        grid = new Grid();
        animals.clear();
        spawnAnimals.clear();
        try{
            Scanner in = new Scanner(new FileReader("Saves\\save.txt"));
            score = in.nextInt();
            in.nextLine();
            
            for(int y = 0; y < grid.GRID_Y; y++){
                String s = in.nextLine();
                String[] shape = s.split(",");
                for(int x = 0; x < grid.GRID_X; x++){
                        grid.grid[x][y] = Auxillary.stringToShape(shape[x]);
                }  
            }
            
            while(in.hasNext()){
               String[] animal = in.nextLine().split(",");
               log.log(Level.FINE, animal[0] + ", " + animal[1] + ", " +animal[2]);
               switch(animal[0].substring(0,4)){
                case "Snak":
                    Snake snake = new Snake(Integer.parseInt(animal[1]), Integer.parseInt(animal[2]), grid);
                    animals.add(snake);
                    break;
                case "Peng":
                    Penguin penguin = new Penguin(Integer.parseInt(animal[1]), Integer.parseInt(animal[2]), grid);
                    animals.add(penguin);
                    break;
                case "Bear":
                    Bear bear = new Bear(Integer.parseInt(animal[1]), Integer.parseInt(animal[2]), grid);
                    animals.add(bear);
                    break;
                case "Monk":
                    Monkey monkey = new Monkey(Integer.parseInt(animal[1]), Integer.parseInt(animal[2]), grid);
                    animals.add(monkey);
                    break;
                case "Bird":
                    Bird bird = new Bird(Integer.parseInt(animal[1]), Integer.parseInt(animal[2]), grid);
                    animals.add(bird);
                    break;
                } 
            }
        }
        catch(IOException e){
            log.log(Level.SEVERE, e.toString(), e);
            ErrorHandle.writeTo("Loading Error: " + e);
        }
        currentBlock = null;
        lose = false;
        spawnCounter = 0;
        nextAnimals();
    }

    /**
     * This method saves the game data to the save file called save.txt in the Saves folder.
     * This file can be read from later to load the current game state.
     * If this method encounters an error while writing to the file, it will write to the ErrorHandle.
     */
    public void save(){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("Saves\\save.txt"));
            
            out.write("" + score);
            out.newLine();
            
            String s;
            for(int y = 0; y < grid.GRID_Y; y++){
                for(int x = 0; x < grid.GRID_X; x++){
                    boolean blockCheck = false;
                    for(Position p: currentBlock.positions){
                        if(p.getX() + currentBlock.getX() == x && 
                                p.getY() + currentBlock.getY() == y)
                                    blockCheck = true;
                    }
                    for(Animal a: animals){
                        if(a.getX() == x && a.getY() == y)
                            blockCheck = true;
                    }

                    if(blockCheck)
                        out.write("None");
                    else
                        out.write(grid.grid[x][y].toString());

                    if(x+1 != grid.GRID_X){
                        out.write(",");
                    }
                }
                out.newLine();
            }
            for(Animal a: animals){
                out.write("" + a.getName() + "," + a.getX() + "," + a.getY());
                out.newLine();
            }
            out.close();
        }   
        catch(IOException e){
            log.log(Level.SEVERE, e.toString(), e);
            ErrorHandle.writeTo("Saving Error: " + e);
        }
    }
    
    /**
     * This method will randomly generate and add a new set of Animals to add to the spawnAnimals Array.
     * This method will also change the music and number of Animals to spawn based based on the user's current score.
     */
    private void nextAnimals(){
        int spawnAmount;
        
        if(score < 1000){
            spawnAmount = 1;
            music = "good_video_game.wav";
        }
        else if(score < 5000){
            spawnAmount = 2;
            music = "game_scary_music.wav";
        }
        else
            spawnAmount = 3;
        
        for(int x = 0; x < spawnAmount; x++){            
            int animal = rand.nextInt(5);
            switch(animal){
                case 0:
                    spawnAnimals.add(Grid.SHAPE.Snake);
                    break;
                case 1:
                    spawnAnimals.add(Grid.SHAPE.Penguin);
                    break;
                case 2:
                    spawnAnimals.add(Grid.SHAPE.Bear);
                    break;
                case 3:
                    spawnAnimals.add(Grid.SHAPE.Monkey);
                    break;
                case 4:
                    spawnAnimals.add(Grid.SHAPE.Bird);
                    break;
            }
        }
    }
    
    /**
     * This method will play a sound signifying an Animal has been trapped, 
     * then it will add the Animals from the spawnAnimals Array to the grid.
     * The method will then clear the spawnAnimals Array and generate the next Animals to add.
     */
    private void spawnAnimals(){
        Trapomino.playSound("trapped.wav");
        for(Grid.SHAPE s: spawnAnimals){
            int xLoc = rand.nextInt(grid.GRID_X);
            
            int y = 0;
            while(y < grid.GRID_Y && grid.grid[xLoc][y] == Grid.SHAPE.None){
                y++;
            }
            y--;
            
            switch(s){
                case Snake:
                    Snake snake = new Snake(xLoc, y, grid);
                    animals.add(snake);
                    break;
                case Penguin:
                    Penguin penguin = new Penguin(xLoc, y, grid);
                    animals.add(penguin);
                    break;
                case Bear:
                    Bear bear = new Bear(xLoc, y, grid);
                    animals.add(bear);
                    break;
                case Monkey:
                    Monkey monkey = new Monkey(xLoc, y, grid);
                    animals.add(monkey);
                    break;
                case Bird:
                    if(y-5 <= 2){
                        Bird animal = new Bird(xLoc, y, grid);
                        animals.add(animal);
                    }
                    else{
                        Bird animal = new Bird(xLoc, y-5, grid);
                        animals.add(animal);
                    }
                    break;
            }
        }
        spawnAnimals.clear();
        nextAnimals();
    }
    
    /**
     * This method checks to see if a Tetromino that has landed on an Animal has a supporting block underneath it.
     * If so it returns false otherwise it removes the block from the grid and returns true.
     * @param tetromino - the Tetromino to check for interaction with
     * @return false if Tetromino has a supporting block underneath it, true otherwise
     */
    private boolean unsupportedBlock(Tetromino tetromino){
        boolean check = false;
        tetromino.removeBlocksFromGrid();
        for(int c = 0; c<4; c++){
            int x = tetromino.positions[c].getX() + currentBlock.getX();
            int y = tetromino.positions[c].getY() + currentBlock.getY()+1;
            //checks if animal below block
            if(Grid.inGrid(new Position(x, y)) &&
                Grid.animal.contains(tetromino.getGrid().grid[x][y])){
                check = true;
            }
            //returns true if tetromino anywhere below block or the block is on top of edge
            if(!Grid.inGrid(new Position(x, y)) ||
                Grid.shapes.contains(tetromino.getGrid().grid[x][y])){
                tetromino.addBlocksToGrid();
                return false;
            }
        }
        tetromino.addBlocksToGrid();
        
        return check;
    }
}