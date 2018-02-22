Trapomino was created by Team Trapomino and published in association with Cognitive Thought Media.

Team Trapomino consists of:
	-- Micheal Peterson, Programming Lead
	-- Jack Baumann, Project Lead and Documentation Lead
	-- Patrick Edelen, Documentation Assistant and Programming Assistant
	-- Alexis Lopez, Graphics and Audio Lead

To play Trapomino, simply open the executable .jar file that can be found in the 
SET_Team_Trapomino/Trapomino folder.

Instructions:
Trapomino has two game modes: story and endless.  In story mode, your goal is to complete all of the levels.  
To complete a level you must simply trap all of the animals that are spawned in the grid at the start of the level, no new animals will spawn.  
In story mode you may save which level you are playing, but you may not save any progress you made within the level.  
If you would like to save and load the game in story mode you must restart any level you started but did not complete. 
In endless mode, a new animal spawns after every tenth block you place and they will continue to spawn until you lose the game by building up to the top row of the grid.  
In endless mode your goal is to score as high as possible, but as your score increases, so does the game's difficulty.  
After choosing a game mode, when playing the game, press W to rotate the falling tetromino, A to move it left, D to move it right, and S to make the tetromino fall faster.

Technical Scoring (500 points)

Rubric Evaluation Item					Possible Points		Example Location/Comments

- Object-Oriented Programming (110 points)
  Proper class design and organization			30			Every class is properly organized.
  Code Reuse (minimize code duplication)		20			Throughout the game, methods are called to execute code. (Trapomino.java)
  Use of encapsulation					20			Almost every class utilizes private and public methods and variables as well as mutators and accessors when appropriate.
  Use of inheritance					20			Many classes have hierarchies of inheritance. (all Animals inherit from Animals.java)
  Use of software design patterns			20			The program utilizes proper software design patterns.

- Design Analysis (90 points)
  Interface Design					30			Implemented & Complete
  Data Flow Diagram(s)					30			Page 9 of the Project Plan
  Selected and adhered to a Software 			30			Waterfall, Page 8 of the Project Plan

- Code Documentation (70 points)
  Comment blocks explaining classes, 			30			JavaDocs are included in SET_Team_Trapomino folder.
  	methods and complex sections of logic
  Provide an in-game tutorial or 			40			Found on Main Menu of the game. (Instructions.java)
	walkthrough for instructional purposes

- Crash Reporting (40 points)
  Generate crash report on application crash		40			The program will generate a text file in the Logs folder and display a message on crash. (ErrorHandle.java)

Data Driven Design (70 points)
  Application makes use of data driven design: 		40			High scores, system settings, story progress, and endless game save state are all 
	runtime settings are adjustable 						saved and written to text files for future use. (HighScores.java, Trapomino.java,
	via text file or database							StoryGame.java, InfinteGame.java, Options.java)
  Session data (saved games, high scores, etc.) 	30			Save files can be found within the Saves folder of Trapomino.
	are stored via flat file or database 						 
	for later reuse									

- Error handling (60 points)
  Proper use of error/exception handling techniques	30			The program will generate a text file in the Logs folder when it encounters an error. (ErrorHandle.java)
  Clear user alerts on recoverable and 			30			Every error description is unique to where it was encountered. 
	non-recoverable error conditions

- Logging (60 points)
  Log system events to dedicated text			30			The program will log system events to a text file in the Logs folder.
	file for debugging								(ErrorHandle.java contains all handlers, every Class has it's own log and log statements)
  Log system errors to dedicated text file		30			There are eight unique logs for different aspects of the game within the Log folder.

- Technical Specifications (200 points) 
* All or none points awarded in each section *
  Project application submission provides directions 	30			The game can be compiled and built as a project in NetBeans.
	for compiling/building on judging hardware
  Project application compiles successfully		30			The game can be compiled as a project in NetBeans.
  Project application runs successfully			40			The game can run from the .jar and from NetBeans.
  Project application is a playable game		60			Trapomino is a playable game based on Tetris.
  Installer included for project application		40			The .jar is found in the Trapomino folder.
