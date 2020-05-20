import java.util.Scanner;

public class TriviaMaze {
	private MazeRoom[][] theMaze;
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
		
	}

	//Overrides the Maze walls at the border of the Maze so it prints correctly
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
	
	//Moves the player around the maze by using the Player object
	public void move(Player player) {
		int row = player.getPlayerRow();
		int col = player.getPlayerCol();
		this.theMaze[row][col].setPlayerLocation(false);

		Boolean validOperand;
		do {
			validOperand = true;
			System.out.print("Move (WASD): ");
			String direction = input.nextLine();
			if(direction.equalsIgnoreCase("w") && row-1 >= 0)
				row--;
			else if(direction.equalsIgnoreCase("a") && col-1 >= 0)
				col--;
			else if(direction.equalsIgnoreCase("s") && row+1 < this.theMaze.length)
				row++;
			else if(direction.equalsIgnoreCase("d") && col+1 < this.theMaze.length)
				col++;
			else {
				System.out.println("Not a valid direction operand: please enter again");
				validOperand = false;
			}
		} while(validOperand == false);

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
	
	//Used to see if a path to the exit room still exists returns true if so
	public boolean mazeParser() {
		return true;
	}
	
	//clears the searched fields for each room in the maze setting them to false
	public void clearSearched() {
		for(int row = 0; row < this.theMaze.length-1; row++) {
			for(int column = 0; column < this.theMaze[row].length-1; column++) {
				this.theMaze[row][column].setSearched(false);
			}
		}
	}
}
