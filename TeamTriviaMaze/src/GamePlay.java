import java.util.Scanner;

public class GamePlay {

	public static void main(String[] args) {

		TriviaMaze testMaze = new TriviaMaze(4, 4);
		Player player = new Player();
		String direction;
		
		testMaze.gamePlayMenu();
		testMaze.printGameRules();
		

		while (player.getPlayerRow() != 3 || player.getPlayerCol() != 3) {
			if(testMaze.mazeParserHelper(player) == false) {
				System.out.println("Looks like you don't know as much about Pokemon as you thought"
						+ "\nthere is no longer a path to the exit you lose!");
				testMaze.printMaze();
				System.exit(0);
			}
			testMaze.clearSearched();
			testMaze.printMaze();
			direction = testMaze.moveSelect();
			testMaze.move(player, direction);
			
		}

		System.out.println("You win!");
		testMaze.printMaze();
	}
	
}