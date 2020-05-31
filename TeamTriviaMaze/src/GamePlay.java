import java.util.Scanner;

public class GamePlay {

	public static void main(String[] args) {

		TriviaMaze testMaze = new TriviaMaze(4, 4);
		testMaze.printMaze();
		SQLDatabase database = new SQLDatabase();
		TriviaQuestions questions = new TriviaQuestions(database);
		Player player = new Player();
		String direction;

		testMaze.printGameRules();

		while (player.getPlayerRow() != 3 || player.getPlayerCol() != 3) {
			testMaze.printMaze();
			direction = testMaze.moveSelect();
			if(questions.menuSelect() == false)
				testMaze.closePath(direction.charAt(0), player);
			else
				testMaze.move(player, direction);
		}

		System.out.println("You win!");
		testMaze.printMaze();
	}

}