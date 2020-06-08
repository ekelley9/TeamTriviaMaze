import java.io.Serializable;
import java.util.Scanner;

public class TriviaMaze implements Serializable {
	
	private static final long serialVersionUID = 4216581100195928697L;
	private MazeRoom[][] theMaze;
	private boolean questionsDisable = false;
	private boolean sameQuestion = false;

	public TriviaMaze(int row, int column) {
		this.theMaze = new MazeRoom[row][column];
		for (int x = 0; x < theMaze.length; x++) {
			for (int y = 0; y < theMaze[x].length; y++) {
				theMaze[x][y] = new MazeRoom(true, true, true, true);
			}
		}
		this.addBorders();
		this.theMaze[0][0].setPlayerLocation(true);
		
	}
	
	//Constructor used for testing 
	public TriviaMaze(int row, int column, Player player) {
		this.theMaze = new MazeRoom[row][column];
		for (int x = 0; x < theMaze.length; x++) {
			for (int y = 0; y < theMaze[x].length; y++) {
				theMaze[x][y] = new MazeRoom(true, true, true, true);
			}
		}
		this.addBorders();
		player.setPlayerRow(3);
		player.setPlayerCol(2);
		this.closePath('d', 0, 0);
		this.closePath('s', 0, 2);
		this.closePath('d', 1, 1);
		this.closePath('s', 1, 1);
		this.closePath('s', 1, 2);
		this.closePath('d', 2, 2);
		this.closePath('s', 2, 2);
		this.closePath('d', 3, 2);
		this.theMaze[3][2].setPlayerLocation(true);
		this.printMaze();
		
		
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
	
	// Moves the player around the maze by using the Player object
	public void move(Player player, String direction) {
		int row = player.getPlayerRow();
		int col = player.getPlayerCol();
		boolean validMove = false;
		MazeRoom curRoom = this.theMaze[row][col];

		if (curRoom.isDoor(direction.charAt(0))) {
			if (direction.equalsIgnoreCase("w")) {
				row--;
				validMove = true;
			} else if (direction.equalsIgnoreCase("a")) {
				col--;
				validMove = true;
			} else if (direction.equalsIgnoreCase("s")) {
				row++;
				validMove = true;
			} else if (direction.equalsIgnoreCase("d")) {
				col++;
				validMove = true;
			}

		} else {
			System.out.println("That pathway is closed you cannot move that direction\n");

		}

		if (validMove) {
			curRoom.setPlayerLocation(false);
			player.setPlayerRow(row);
			player.setPlayerCol(col);
			this.theMaze[row][col].setPlayerLocation(true);
		}

	}
	
	//
	public boolean curRoomIsDoor(Player player, String direction) {
		MazeRoom curRoom = theMaze[player.getPlayerRow()][player.getPlayerCol()];
		return curRoom.isDoor(direction.charAt(0));
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

			if (this.theMaze[row][column].isDoor('s') && this.theMaze[row + 1][column].getSearched() == false
					&& !pathExists) {
				this.theMaze[row][column].setSearched(true);
				pathExists = mazeParser(row + 1, column);
				
			} if (this.theMaze[row][column].isDoor('d') && this.theMaze[row][column + 1].getSearched() == false 
					&& !pathExists) {
				this.theMaze[row][column].setSearched(true);
				pathExists = mazeParser(row, column + 1);
			}

			 if (this.theMaze[row][column].isDoor('w') && this.theMaze[row - 1][column].getSearched() == false
					 && !pathExists) {
				this.theMaze[row][column].setSearched(true);
				pathExists = mazeParser(row - 1, column);
				
			}

			 if (this.theMaze[row][column].isDoor('a') && this.theMaze[row][column - 1].getSearched() == false
					 && !pathExists) {
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
	
	//used to close paths at specific coordinates used for testing
	public void closePath(char direction, int curRow, int curColumn) {
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
	
	public void setQuestionsDisabled(boolean toSet) {
		this.questionsDisable = toSet;
	}
	
	public boolean getQuestionsDisabled() {
		return this.questionsDisable;
	}
	
	public void setSameQuestion(boolean toSet) {
		this.sameQuestion = toSet;
	}
	
	public boolean getSameQuestion() {
		return this.sameQuestion;
	}
	
}
