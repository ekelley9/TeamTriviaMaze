import java.util.Scanner;
import java.io.*;

public class GamePlay {

	public static void main(String[] args) throws Exception {

		TriviaMaze testMaze = new TriviaMaze(4,4);
		Scanner input = new Scanner(System.in);
		
		
		gamePlayMenu(testMaze, input);
		
		

		
	}
	
	public static void mainGame(TriviaMaze theMaze, TriviaQuestions theQuestions,Player player, Scanner input){
		theMaze.printGameRules();
		
		while (player.getPlayerRow() != 3 || player.getPlayerCol() != 3) {
			if(theMaze.mazeParserHelper(player) == false) {
				System.out.println("Looks like you don't know as much about Pokemon as you thought"
						+ "\nthere is no longer a path to the exit you lose!");
				theMaze.printMaze();
				System.exit(0);
			}
			theMaze.clearSearched();
			theMaze.printMaze();
			playerOptionsSelect(theMaze, theQuestions, player, input);
			
		}

		System.out.println("You win!");
		theMaze.printMaze();
	}
	
	public static void playerMenu(TriviaMaze theMaze, Scanner input) throws Exception {
		int choice = 0;
		SQLDatabase database = null;
		TriviaQuestions theQuestions = null;

		System.out.println("\n____________GAME___________");
		System.out.println(" 1. New Game \n 2. Load a Save \n 3. Cheats");

		do {
			choice = NumberValidator.numberValidator(input);
			;
			if (choice == 1) {
				theMaze = new TriviaMaze(4, 4);
				database = new SQLDatabase();
				theQuestions = new TriviaQuestions(database);
				mainGame(theMaze, theQuestions, new Player(), input);
			} else if (choice == 2) {
				database = new SQLDatabase();
				theQuestions = new TriviaQuestions(database);
				loadGame(theMaze, theQuestions, new Player(), input);
			}
			else if (choice == 3)
				cheatMenu(theMaze, input);
			else
				System.out.println("INVLAID. Choose 1 for New Game, 2 for Load a Save or 3 for Cheats");
		} while (choice < 1 || choice > 3);

	}
	
	public static void gamePlayMenu(TriviaMaze theMaze, Scanner input) throws Exception {
		int choice = 0;
		
		System.out.println(" ___________\n|           |\n| 1. ADMIN  |\n| 2. PLAYER |\n|___________|");
		
		do {
			choice = NumberValidator.numberValidator(input);
			
			if(choice == 1) {
				SQLDatabase database = new SQLDatabase();
				adminMenu(database, input);
			}
			else if(choice == 2)
				playerMenu(theMaze, input);
			else
				System.out.println("INVALID. Pick 1 to start as an ADMIN or 2 to start as a PLAYER");
		}while(choice < 1 || choice > 2);
	}
	
	public static void playerOptionsSelect(TriviaMaze theMaze, TriviaQuestions theQuestions, Player player,
			Scanner input) {
		boolean correctInput = false;
		do {
			System.out.print("Move Up(W)\n");
			System.out.print("Move Down(S)\n");
			System.out.print("Move left(A)\n");
			System.out.print("Move Right(D)\n");
			System.out.println("Save game(save)\n");
			System.out.println("Exit game(exit)\n");
			System.out.print("Enter your choice:  ");

			String playerChoice = input.nextLine();

			if (playerChoice.equalsIgnoreCase("save")) {
				saveGame(theMaze, player, input);
				correctInput = true;
			} else if (playerChoice.equalsIgnoreCase("W") || playerChoice.equalsIgnoreCase("A")
					|| playerChoice.equalsIgnoreCase("S") || playerChoice.equalsIgnoreCase("D")) {
				if (theMaze.curRoomIsDoor(player, playerChoice)) {
					if (theQuestions.menuSelect()) {
						theMaze.move(player, playerChoice);
					} else {
						System.out.println("Incorrect that pathway is now closed\n");
						theMaze.closePath(playerChoice.charAt(0), player);
					}
				} else {
					System.out.println("That path is closed\n");
				}
				correctInput = true;
			} else if (playerChoice.equalsIgnoreCase("exit")) {

			} else {
				System.out.println("Not a valid option please select a valid option\n");
			}

		} while (!correctInput);

	}
	
	public static void adminMenu(SQLDatabase database, Scanner input) {
		String password = "123Password";
		String attemptedPassword;
		int choice = 0;
		
		System.out.print("Password: ");
		attemptedPassword = input.nextLine();
		
		if(password.equals(attemptedPassword)) {
			System.out.println("\n______ADDING QUESTIONS_____");
			System.out.println(" 1. True or False \n 2. Multiple Choice \n 3. Short Answer");
			
			do {
				choice = NumberValidator.numberValidator(input);
				if(choice == 1)
					database.addTrueFalse(input);
				else if(choice == 2)
					database.addMultipleChoice(input);
				else if(choice == 3)
					database.addShortAnswer(input);
				else
					System.out.println("INVALID. Choose 1 for True or False, 2 for Multiple choice or 3 for Short Answer");
			} while(choice < 1 || choice > 3);
		}
	}
	
	public static boolean cheatMenu(TriviaMaze theMaze, Scanner input) {
		boolean switches = false; //TODO save my status based on last edit to cheat menu
		String onOff = "";
		
		System.out.print("Navigate Maze Cheat (On/Off): ");
		
		do {
			onOff = input.nextLine();
			if(onOff.equalsIgnoreCase("on")) {
				System.out.println("-ON-");
				switches = true;
			}
			else if(onOff.equalsIgnoreCase("off")) {
				System.out.println("-OFF-");
				switches = false;
			}
			else
				System.out.println("INVALID. Enter 'on' to turn cheats on or 'off' to turn cheats off");
		} while(!onOff.equalsIgnoreCase("on") && !onOff.equalsIgnoreCase("off"));
		
		return switches;
	}
	
	public static void saveGame(TriviaMaze theMaze, Player player, Scanner input) {
		if(theMaze == null) {
			System.out.println("The passed in museum object is null cannot save the game\n");
			return;
		}
		if(player == null) {
			System.out.println("The passed in robber object is null cannout save game\n");
			return;
		}
		
		if(saveExists()) {
			System.out.print("Would you like to overwrite your previous save? 1 for yes 2 for no: ");
			int choice = NumberValidator.numberValidator(input);
			switch(choice) {
			case 1:
				try {
					ObjectOutputStream mazeOut = new ObjectOutputStream(new FileOutputStream("SaveMaze.txt"));
					ObjectOutputStream playerOut = new ObjectOutputStream(new FileOutputStream("SavePlayer.txt"));
					
					mazeOut.writeObject(theMaze);
					playerOut.writeObject(player);
					mazeOut.close();
					playerOut.close();
					System.out.println("Your game has been saved\n");
				}catch(Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			break;
			case 2:
				return;
			default:
				System.out.println("Please choose a valid option");
				
			}
		}else {
			try {
				ObjectOutputStream mazeOut = new ObjectOutputStream(new FileOutputStream("SaveMaze.txt"));
				ObjectOutputStream playerOut = new ObjectOutputStream(new FileOutputStream("SavePlayertxt"));
				
				mazeOut.writeObject(theMaze);
				playerOut.writeObject(player);
				mazeOut.close();
				playerOut.close();
				System.out.println("Your game has been saved\n");
			}catch(Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	public static void loadGame(TriviaMaze theMaze, TriviaQuestions theQuestions, Player player, Scanner input) throws Exception {

		ObjectInputStream mazeIn = new ObjectInputStream(new FileInputStream("SaveMaze.txt"));
		ObjectInputStream playerIn = new ObjectInputStream(new FileInputStream("SavePlayer.txt"));

		theMaze = (TriviaMaze) mazeIn.readObject();
		player = (Player) playerIn.readObject();

		mainGame(theMaze, theQuestions, player, input);
	}
	

	public static boolean saveExists() {
		File f = new File("SaveMaze.txt");
		if(f.exists())
		{
			return true;
		}
		return false;
	}
	
}