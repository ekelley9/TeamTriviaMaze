import java.util.Scanner;
public class GamePlay {

	public static void main(String[] args) {
		
		TriviaMaze testMaze = new TriviaMaze(4,4);
		testMaze.printMaze();
		SQLDatabase database = new SQLDatabase();
		TriviaQuestions questions = new TriviaQuestions(database);
		Player player = new Player();
		
		testMaze.printGameRules();
		
		while(player.getPlayerRow() != 3 || player.getPlayerCol() != 3) {
			testMaze.printMaze();
			if(questions.menuSelect())
				testMaze.move(player);
			
		}
		
		System.out.println("You win!");
		testMaze.printMaze();
	}

}