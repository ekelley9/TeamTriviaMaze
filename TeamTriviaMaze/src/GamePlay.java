import java.util.Scanner;
public class GamePlay {

	public static void main(String[] args) {
		
		TriviaMaze testMaze = new TriviaMaze(4,4);
		testMaze.printMaze();
		SQLDatabase database = new SQLDatabase();
		database.testTables();
		TriviaQuestions questions = new TriviaQuestions(database);
		Player player = new Player();
		String direction;
		Boolean shouldMove = false;
		
		testMaze.printGameRules();
		
		while(player.getPlayerRow() != 3 || player.getPlayerCol() != 3) {
			testMaze.printMaze();
			direction = testMaze.moveSelect();
			testMaze.move(player, direction);
			questions.menuSelect();
		}
		
		System.out.println("You win!");
		testMaze.printMaze();
	}

}