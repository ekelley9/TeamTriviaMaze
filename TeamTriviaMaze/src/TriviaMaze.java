import java.util.Scanner;

public class TriviaMaze {
	private MazeRoom[][] theMaze;
	private SQLDatabase database = new SQLDatabase();
	private TriviaQuestions theQuestions = new TriviaQuestions(database);
	Scanner input = new Scanner(System.in);

	public TriviaMaze(int row, int column) {
		this.theMaze = new MazeRoom[row][column];
		for (int x = 0; x < theMaze.length; x++) {
			for (int y = 0; y < theMaze[x].length; y++) {
				theMaze[x][y] = new MazeRoom(true, true, true, true);
			}
		}
		this.addBorders();
		this.theMaze[0][0].setPlayerLocation(true);
		if(this.theMaze[this.theMaze.length-1][this.theMaze.length-1] instanceof ExitRoom) {
		}
		
	}

	//Overrides the Maze walls at the border of the Maze so it prints/functions correctly
	public void addBorders() {
		int length = theMaze.length -1;
		for (int i = 0; i < theMaze.length; i++) {
			theMaze[0][i] = new MazeRoom(false, true, true, true);
			theMaze[length][i] = new MazeRoom(true, true, false, true);
			theMaze[i][0] = new MazeRoom(true, true, true, false);
			theMaze[i][theMaze[length].length-1] = new MazeRoom(true, false, true, true);
		}

		theMaze[0][0] = new MazeRoom(false, true, true, false);
		theMaze[0][theMaze[length].length-1] = new MazeRoom(false, false, true, true);
		theMaze[length][0] = new MazeRoom(true, true, false, false);
		this.theMaze[length][theMaze[length].length-1] = new ExitRoom();

	}
	
	//Prints out the maze based on the room objects toString
	public void printMaze() {

		for (int i = 0; i < this.theMaze.length; i++) {
			for (int j = 0; j < this.theMaze.length; j++) {
				System.out.print(this.theMaze[i][j].roomTop() + " ");
			}
			System.out.println();
			for (int k = 0; k < this.theMaze.length; k++) {
				System.out.print(this.theMaze[i][k].roomMid() + " ");
			}
			System.out.println();
			for (int l = 0; l < this.theMaze.length; l++) {
				System.out.print(this.theMaze[i][l].roomBottom() + " ");
			}
			System.out.println();

		}
	}
	
	public String moveSelect() {
		System.out.print("Move (WASD): ");
		return input.nextLine();
	}
	
	// Moves the player around the maze by using the Player object
	public void move(Player player, String direction) {
		int row = player.getPlayerRow();
		int col = player.getPlayerCol();
		MazeRoom curRoom = this.theMaze[row][col];
		this.theMaze[row][col].setPlayerLocation(false);

		Boolean validOperand;
		do {
			validOperand = true;
			if (curRoom.isDoor(direction.charAt(0))) {
				if (this.theQuestions.menuSelect()) {
					if (direction.equalsIgnoreCase("w") && curRoom.isDoor('w'))
						row--;
					else if (direction.equalsIgnoreCase("a") && curRoom.isDoor('a'))
						col--;
					else if (direction.equalsIgnoreCase("s") && curRoom.isDoor('s'))
						row++;
					else if (direction.equalsIgnoreCase("d") && curRoom.isDoor('d'))
						col++;
					else {
						System.out.println("Not a valid direction operand: please enter again");
						direction = moveSelect();
						validOperand = false;
					}
				} else {
					System.out.println("\nYour answer was inccorect that pathway is now closed\n");
					this.closePath(direction.charAt(0), player);
				}
			} else {
				System.out.println("Not a valid direction operand: please enter again");
				direction = moveSelect();
				validOperand = false;
			}
		} while (validOperand == false);

		player.setPlayerRow(row);
		player.setPlayerCol(col);
		this.theMaze[row][col].setPlayerLocation(true);
	}
	
	//Prints the rules to the Trivia maze
	public void printGameRules() {
		System.out.println("\n --------------------------------------------------------------------------------------------");
		System.out.println(" -                          Using the 'WASD' pad navigate the maze                          -");
		System.out.println(" -    In each room you will be asked a trivia question, answer them correctly to move on    -");
		System.out.println(" -                If you fail to answer correctly the path will be blocked!                 -");
		System.out.println(" -                      Reach the 'E' before blocking all paths to win                      -");
		System.out.println(" --------------------------------------------------------------------------------------------\n");
	}
	
	//used as a helper to the maze parser method takes a players current position and passes it to the mazeParser
	public boolean mazeParserHelper(Player curPlayer) {
		int row = curPlayer.getPlayerRow();
		int column = curPlayer.getPlayerCol();
		return this.mazeParser(row, column);
	}
	
	// Used to see if a path to the exit room still exists returns true if so
	public boolean mazeParser(int row, int column) {
		boolean pathExists = false;
		if (this.theMaze[row][column] instanceof ExitRoom) {
			pathExists = true;
		} else {

			if (this.theMaze[row][column].isDoor('s') && this.theMaze[row + 1][column].getSearched() == false) {
				this.theMaze[row][column].setSearched(true);
				pathExists = mazeParser(row + 1, column);
				
			} else if (this.theMaze[row][column].isDoor('d') && this.theMaze[row][column + 1].getSearched() == false) {
				this.theMaze[row][column].setSearched(true);
				pathExists = mazeParser(row, column + 1);
			}

			else if (this.theMaze[row][column].isDoor('w') && this.theMaze[row - 1][column].getSearched() == false) {
				this.theMaze[row][column].setSearched(true);
				pathExists = mazeParser(row - 1, column);
			}

			else if (this.theMaze[row][column].isDoor('a') && this.theMaze[row][column - 1].getSearched() == false) {
				this.theMaze[row][column].setSearched(true);
				pathExists = mazeParser(row, column - 1);
			}
		}

		return pathExists;
	}

	//used to close a path when the player gets a question wrong
	public void closePath(char direction, Player curPlayer) {
		int curRow = curPlayer.getPlayerRow();
		int curColumn = curPlayer.getPlayerCol();
		if(direction == 'w') {
			this.theMaze[curRow][curColumn].closeDoor('n');
			this.theMaze[curRow - 1][curColumn].closeDoor('s');
		}
		else if(direction == 's') {
			this.theMaze[curRow][curColumn].closeDoor('s');
			this.theMaze[curRow + 1][curColumn].closeDoor('n');
		}
		else if(direction == 'd') {
			this.theMaze[curRow][curColumn].closeDoor('e');
			this.theMaze[curRow][curColumn + 1].closeDoor('w');
		}
		else if(direction == 'a') {
			this.theMaze[curRow][curColumn].closeDoor('w');
			this.theMaze[curRow][curColumn - 1].closeDoor('e');
		}
	}

	//clears the searched fields for each room in the maze setting them to false
	public void clearSearched() {
		for(int row = 0; row < this.theMaze.length; row++) {
			for(int column = 0; column < this.theMaze[row].length; column++) {
				this.theMaze[row][column].setSearched(false);
			}
		}
	}
	
	public void gamePlayMenu() {
		int choice = 0;
		
		System.out.println(" ___________\n|           |\n| 1. ADMIN  |\n| 2. PLAYER |\n|___________|");
		
		do {
			choice = Integer.parseInt(input.nextLine());
			
			if(choice == 1)
				adminMenu();
			else if(choice == 2)
				playerMenu();
			else
				System.out.println("INVALID. Pick 1 for ADMIN or 2 for PLAYER");
		}while(choice < 1 || choice > 2);
	}
	
	public void adminMenu() {
		String password = "123Password";
		String attemptedPassword;
		int choice = 0;
		
		System.out.print("Password: ");
		attemptedPassword = input.nextLine();
		
		if(password.equals(attemptedPassword)) {
			System.out.println("\n______ADDING QUESTIONS_____");
			System.out.println(" 1. True or False \n 2. Multiple Choice \n 3. Short Answer");
			
			do {
				choice = Integer.parseInt(input.nextLine());
				if(choice == 1)
					database.addTrueFalse(input);
				else if(choice == 2)
					database.addMultipleChoice(input);
				else if(choice == 3)
					database.addShortAnswer(input);
				else
					System.out.println("INVALID. Choose 1 for True or False, 2 for Multiple choice or 3 for Short Answer");
			} while(choice < 1 || choice > 3);
		}
	}
	
	public void playerMenu() {
		int choice = 0;
		
		System.out.println("\n____________GAME___________");
		System.out.println(" 1. New Game \n 2. Load a Save \n 3. Cheats");
		
		do {
			choice = Integer.parseInt(input.nextLine());
			if(choice == 1)
				System.out.println("Make new game here...");
			else if(choice == 2)
				System.out.println("Load a save here....");
			else if(choice == 3)
				cheatMenu();
			else
				System.out.println("INVLAID. Choose 1 for New Game, 2 for Load a Save or 3 for Cheats");
		} while(choice < 1 || choice >3);
		
	}
	
	public boolean cheatMenu() {
		boolean switches = false; //TODO save my status based on last edit to cheat menu
		String onOff = "";
		
		System.out.print("Navigate Maze Cheat (On/Off): ");
		
		do {
			onOff = input.nextLine();
			if(onOff.equalsIgnoreCase("on")) {
				System.out.println("-ON-");
				switches = true;
			}
			else if(onOff.equalsIgnoreCase("off")) {
				System.out.println("-OFF-");
				switches = false;
			}
			else
				System.out.println("INVALID. Enter 'on' to turn cheat on or 'off' to turn cheat off");
		} while(!onOff.equalsIgnoreCase("on") && !onOff.equalsIgnoreCase("off"));
		
		return switches;
	}
}
