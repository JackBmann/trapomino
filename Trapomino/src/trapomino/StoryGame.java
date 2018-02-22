package trapomino;

import java.awt.Color;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static trapomino.Trapomino.levels;

/**
 * This class manages the Story game mode in Trapomino.
 * @author Patrick Edelen
 */
public class StoryGame{   
    private static final Logger log = Logger.getLogger(StoryGame.class.getName());{log.addHandler(ErrorHandle.storyGameHandler);}
    private static Grid grid;
    
    private static Tetromino currentBlock, nextBlock;

    private static ArrayList<Animal> animals = new ArrayList<Animal>();
    private static ArrayList<Animal> animalsTrapped = new ArrayList<Animal>();
    
    private static ArrayList<Grid.SHAPE> spawnAnimals = new ArrayList<Grid.SHAPE>();
    
    private static String music = "game_scary_music.wav";
	
    public static Entity titleScreen = new Entity(0,0,0,0,"city.png"),
                            winImage = new Entity(0,0,0,0,"winImage.gif"),
                             factory = new Entity(0,0,0,0,"factory.png"),
                          apartments = new Entity(0,0,0,0,"apartments.png"),
                           cityscape = new Entity(0,0,0,0,"cityscape.png"),
                           winEntity = new Entity(280,150,480,360,"win.png"),
                          loseEntity = new Entity(280,150,480,360,"lose.png"),
                              border = new Entity(200,-140,0,0,"GridBorder.png"),
                         pauseButton = new Entity(790,610,335,75),
                     pauseBackground = new Entity(330, 10,0,0,"Grid2.png"),
                          exitButton = new Entity(370,300,335,75,"Buttons\\exitButton.png");
    
    private static int counterY = 0, counterX = 0, counterR = 0, waitCounter = 0;

    private boolean paused = false, lose = false, win = false;
    
    private boolean clearBlocks = false;
    private ArrayList<Position> clearPositions = new ArrayList<Position>();
    private ArrayList<Integer> clearedRows;
    
    
    private int score;
    private int animationCounter=0;
    private int spawnCounter = 0;
    private boolean first;
    private int currentLevel;
    private boolean remaining = true;
    
    Random rand = new Random();
    
    /**
     * This constructor starts a new game of the StoryGame mode.
     */
    public StoryGame(){
        newGame();
        first=true;
    }
    
    /**
     * This method manages the movement in the grid as long as the game is not paused.
     * This method moves the current Tetromino down, left, and right, rotates the current Tetromino, 
     * checks if the user is pressing the button to speed its falling, 
     * and calls the move method of all the Animals in the grid.
     * It also checks to see if the pause button is clicked, if so it will render and manage the pause menu.
     * This method also sets the music, renders the lose menu.
     */
    public void run() {
        if(!paused){
            //iterates counter for X movement
            counterY++;
            //iterates counter for Y movement
            counterX++;
            //iterates counter for rotation movement
            counterR++;
            if(first){
                storySpawn();
            }
            
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
            }}

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
                if(waitCounter == 0)
                    waitCounter = 30;
                waitCounter--;
                log.log(Level.FINER, "clear");
                if(waitCounter == 1){
                    paused = false;
                    clearBlocks = false;
                    clearPositions.clear();                
                }     
            }
            else{
               if(exitButton.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
                   Trapomino.playSound("button_click.wav");
                   newGame();
                   Trapomino.clearMouse();
                   paused = false;
                   first=true;
                
                   win=false;
                   remaining=true;
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
                newGame();
                Trapomino.clearMouse();
                paused = false;
                first=true;
                
                win=false;
                remaining=true;
                Trapomino.playSound("button_click.wav");
                Trapomino.changeState(Trapomino.STATE.LEVELSELECT);
            }
        }
        if(win){
            if(currentLevel==6){
                animationCounter++;
            }
            if(winEntity.getRectangle().contains(Trapomino.getMouseX(), Trapomino.getMouseY())){
                newGame();
                levels[currentLevel][0]=1;
                first=true;
                
                win=false;
                remaining=true;
                Trapomino.changeState(Trapomino.STATE.LEVELSELECT);
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
     * This method determines the current level and spawns animals based on story array in Trapomino class.
     */
    public void storySpawn(){
        log.log(Level.INFO, "storyworking!");
            first=false;
            boolean found = false;
            for(int i=0; (i<7)&&(!found); i++){
                if(levels[i][0] == 0){
                    found = true;
                        currentLevel = i;
                    log.log(Level.INFO, currentLevel+"");
                }
            }
                
                for(int a=0;a<levels[currentLevel][1];a++){
                    Snake snake = new Snake(3+(2*a), 21, grid);
                    animals.add(snake);
                }
                for(int a=0;a<levels[currentLevel][2];a++){
                    Penguin penguin = new Penguin(7+(3*a), 21, grid);
                    animals.add(penguin);
                }
                for(int a=0;a<levels[currentLevel][3];a++){
                    Bird bird = new Bird(0, 10-(2*a), grid);
                    animals.add(bird);
                }
                for(int a=0;a<levels[currentLevel][4];a++){
                    Monkey monkey = new Monkey(5+(3*a), 21, grid);
                    animals.add(monkey);
                }
                for(int a=0;a<levels[currentLevel][5];a++){
                    Bear bear = new Bear(0+a, 21, grid);
                    animals.add(bear);
                }
                for(int a=0;a<levels[currentLevel][6];a++){
                    //lion here
                }
                
    }
    /**
     * This method is called by Trapomino, it renders the background, grid, UI, and pause button.
     * When the game is paused, the method will render the pause menu or the lose screen if the player lost.
     * @param g - the Graphics where the items in this method will be rendered
     */
    public void render(Graphics g) {
        if(!paused){
            //draws the background image
                if(currentLevel==0){
                    g.drawImage(titleScreen.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                        Trapomino.HEIGHT * Trapomino.SCALE, null);
                }
                if(currentLevel==1){
                    g.drawImage(factory.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                        Trapomino.HEIGHT * Trapomino.SCALE, null);
                }
                if(currentLevel==2){
                    g.drawImage(factory.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                        Trapomino.HEIGHT * Trapomino.SCALE, null);
                }
                if(currentLevel==3){
                    g.drawImage(titleScreen.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                        Trapomino.HEIGHT * Trapomino.SCALE, null);
                }
                if(currentLevel==4){
                    g.drawImage(apartments.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                        Trapomino.HEIGHT * Trapomino.SCALE, null);
                }
                if(currentLevel==5){
                    g.drawImage(apartments.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                        Trapomino.HEIGHT * Trapomino.SCALE, null);
                }
                if(currentLevel==6){
                    g.drawImage(cityscape.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                        Trapomino.HEIGHT * Trapomino.SCALE, null);
                }
                
            //draws the grid
            grid.render(g);
            //draws border
            border.render(g);
            //draw pausebutton
            pauseButton.render(g);
            
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
            }else if(lose){
               loseEntity.render(g);
            }else if(win){
               winEntity.render(g);
            }else{
                pauseBackground.render(g);
                exitButton.render(g);
            }
        }
        if(currentLevel==6&&win){
            if(animationCounter<180){
                    g.drawImage(winImage.getImage(), 0, 0, Trapomino.WIDTH * Trapomino.SCALE, 
                        Trapomino.HEIGHT * Trapomino.SCALE, null);
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
        
        if(grid.topEmpty()){
            grid.unionCreate();
           
           for(Animal a: animals){
               if(!a.trapCheck()){
                    log.log(Level.INFO, "Trapped!");
                    
                    score = score + a.getScore();
                    
                    grid.changeToTrapped(new Position(a.getX(),a.getY()));
                    animalsTrapped.add(a);
                }
           }
           
           for(Animal a: animalsTrapped){
                 animals.remove(a);
           }animalsTrapped.clear();
           
           
           
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
           grid.unionCreate();
           
            if((animals.size() == 0)){
               win = true;
               paused = true;
            }
        }else{
            lose = true;
            paused = true;
        }
    }
    
    /**
     * This method starts a new game by setting all of the variables to their default values and generating the new Grid.
     */
    public void newGame(){ 
        animals.clear();
        spawnAnimals.clear();
        currentBlock = null;
        paused = false;
        lose = false;
        win=false;
        spawnCounter = 0;
        score = 0;
        grid = new Grid();
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