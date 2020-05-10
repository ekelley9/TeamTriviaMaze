
public class Test {

	public static void main(String[] args) {
		
		TriviaMaze testMaze = new TriviaMaze(4,4);
		TriviaQuestions questions = new TriviaQuestions();
		Player player = new Player();
		
		testMaze.printGameRules();
		
		while(player.getPlayerRow() != 3 || player.getPlayerCol() != 3) {
			testMaze.printMaze();
			testMaze.move(player);
			questions.menuSelect("trueOrFalse"); //hard coded true or false for now, change this to read question type from sqlite
			questions.isAnswerCorrect(1); //hard coded for now, change this to read solution from sqlite
		}
		
		System.out.println("You win!");
		testMaze.printMaze();
	}

}
