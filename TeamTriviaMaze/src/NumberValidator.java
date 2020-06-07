import java.util.*;


public class NumberValidator {
	
	//ensures the input from the user is a number and will not throw an errors
		public static int numberValidator(Scanner input) {
			boolean isNumber = false;
			int toReturn = 0;
			String testInput = "";
			
			do {
				try {
					testInput = input.nextLine();
					toReturn = Integer.parseInt(testInput);
					isNumber = true;
				}catch(NumberFormatException e) {
					System.out.println("\n" + testInput + " is not a valid number please enter your choice again\n");
				}
				
			}while(!isNumber);
			
			return toReturn;
			
		}

}
