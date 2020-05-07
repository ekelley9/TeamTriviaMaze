import java.util.Scanner;

public class TriviaQuestions {
	
	Scanner input = new Scanner(System.in);
	
	public void menuSelect(String questionType) {
		if(questionType.equals("trueOrFalse"))
			printTrueOrFalseMenu();
		else if(questionType.equals("multipleChoice")) 
			printMultipleChoiceMenu();
		else
			printShortAnswerMenu();
	}
	
	public void printTrueOrFalseMenu() {
		System.out.println("True or False");
		//display question from sqlite
		System.out.println("1. True");
		System.out.println("2. False");
	}
	
	public void printMultipleChoiceMenu() {
		System.out.println("Select correct answer");
		//display question from sqlite
		System.out.print("1. ");
		System.out.println();  //sql answer option
		System.out.print("2. ");
		System.out.println();  //sql answer option
		System.out.print("3. ");
		System.out.println();   //sql answer option
		System.out.print("4. ");
		System.out.println();   //sql answer option
	}
	
	public void printShortAnswerMenu() {
		System.out.println("Enter correct answer");
		//display question from sqlite
	}
	
	public boolean isAnswerCorrect(int solution) {
		int playerAnswer = input.nextInt();
		
		if(playerAnswer == solution)
			return true;
		else
			return false;
	}
}
