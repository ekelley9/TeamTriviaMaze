import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TriviaQuestions {
	
	Scanner input = new Scanner(System.in);
	Random chance = new Random();
	SQLDatabase database;
	
	public TriviaQuestions(SQLDatabase database) {
		this.database = database;
	}
	
	// calls the appropriate menu for a random question type
	public void menuSelect() {
		int randomNum = chance.nextInt(3);
		
		if(randomNum == 0)
			printTrueOrFalseMenu();
		else if(randomNum == 1) 
			printMultipleChoiceMenu();
		else if(randomNum == 2)
			printShortAnswerMenu();
		else
			throw new IllegalArgumentException("Yoo your random didnt work :/"); 
	}
	
	public void printTrueOrFalseMenu() {
		ArrayList<String> trueFalse = database.getTrueFalse();
		String question = trueFalse.get(0);
		
		System.out.println("True or False");
		System.out.println(question);
		System.out.println("1. True");
		System.out.println("2. False");
	}
	
	public void printMultipleChoiceMenu() {
		ArrayList<String> multipleChoice = database.getMultipleChoice();
		String question = multipleChoice.get(0);
		multipleChoice.remove(0);
		Collections.shuffle(multipleChoice);
		
		System.out.println("Select correct answer");
		System.out.println(question);
		System.out.println("1. " + multipleChoice.get(0));
		System.out.println("2. " + multipleChoice.get(1));
		System.out.println("3. " + multipleChoice.get(2));
	}
	
	public void printShortAnswerMenu() {
		ArrayList<String> shortAnswer = database.getShortAnswer();
		String question = shortAnswer.get(0);
		
		System.out.println("Enter correct answer");
		System.out.println(question);
	}
	
	public boolean isAnswerCorrect(int solution) {
		int playerAnswer = input.nextInt();
		
		if(playerAnswer == solution)
			return true;
		else
			return false;
	}
}
