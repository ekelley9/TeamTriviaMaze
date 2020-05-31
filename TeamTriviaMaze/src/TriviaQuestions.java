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
	public boolean menuSelect() {
		int randomNum = chance.nextInt(3);
		
		if(randomNum == 0)
			return printTrueOrFalseMenu();
		else if(randomNum == 1) 
			return printMultipleChoiceMenu();
		else if(randomNum == 2)
			return printShortAnswerMenu();
		else
			throw new IllegalArgumentException("Yoo your random didnt work :/"); 
	}
	
	public boolean printTrueOrFalseMenu() {
		ArrayList<String> trueFalse = database.getTrueFalse();
		String answer = trueFalse.get(trueFalse.size()-1);
		String question = trueFalse.get(0);
		boolean validAnswer = true;
		
		System.out.println("True or False");
		System.out.println(question);
		System.out.println("1. True");
		System.out.println("2. False");
		
		do {
			int playerAnswer = Integer.parseInt(input.nextLine());
			if(playerAnswer == 1)
				return answer.equals("True");
			else if(playerAnswer == 2)
				return answer.equals("False");
			else {
				System.out.println("Not a valid answer");
				validAnswer = false;
			}
		}while(validAnswer == false);
		
		return false;
	}
	
	public boolean printMultipleChoiceMenu() {
		ArrayList<String> multipleChoice = database.getMultipleChoice();
		String question = multipleChoice.get(0);
		String answer = multipleChoice.get(multipleChoice.size()-1);
		multipleChoice.remove(0);
		Collections.shuffle(multipleChoice);
		
		System.out.println("Select correct answer");
		System.out.println(question);
		System.out.println("1. " + multipleChoice.get(0));
		System.out.println("2. " + multipleChoice.get(1));
		System.out.println("3. " + multipleChoice.get(2));
		int playerAnswer = Integer.parseInt(input.nextLine());
		return answer.equals(multipleChoice.get(playerAnswer-1));
	}
	
	public boolean printShortAnswerMenu() {
		ArrayList<String> shortAnswer = database.getShortAnswer();
		String answer = shortAnswer.get(shortAnswer.size()-1);
		String question = shortAnswer.get(0);
		
		System.out.println("Enter correct answer");
		System.out.println(question);
		String playerAnswer = input.nextLine();
		
		return answer.equalsIgnoreCase(playerAnswer);
	}
}
