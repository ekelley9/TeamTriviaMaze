import java.util.Scanner;

public class GamePlay {

	public static void main(String[] args) {

		TriviaMaze testMaze = new TriviaMaze(4, 4);
		testMaze.printMaze();
		Player player = new Player();
		String direction;

		testMaze.printGameRules();

		while (player.getPlayerRow() != 3 || player.getPlayerCol() != 3) {
			testMaze.printMaze();
			direction = testMaze.moveSelect();
			testMaze.move(player, direction);
			
		}

		System.out.println("You win!");
		testMaze.printMaze();
	}

}