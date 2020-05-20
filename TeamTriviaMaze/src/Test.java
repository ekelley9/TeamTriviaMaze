
public class Test {

	public static void main(String[] args) {
		
		TriviaMaze testMaze = new TriviaMaze(4,4);
		SQLDatabase database = new SQLDatabase();
		TriviaQuestions questions = new TriviaQuestions(database);
		Player player = new Player();
		
		testMaze.printGameRules();
		
		while(player.getPlayerRow() != 3 || player.getPlayerCol() != 3) {
			testMaze.printMaze();
			testMaze.move(player);
			questions.menuSelect();
			questions.isAnswerCorrect(1); //hard coded for now, change this to read solution from sqlite
		}
		
		System.out.println("You win!");
		testMaze.printMaze();
	}

}