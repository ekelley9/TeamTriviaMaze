import java.sql.*;
import java.util.*;
import java.io.*;

public class SQLDatabase {
	
	//Fields for the SQLDatabase object setting up a Connection, Statement, and ResultSet object to properly use SQL commands
	private Connection curConection = null;
	private Statement curStatement = null;
	private ResultSet queryResult = null;
	private Random rand = new Random();
	
	//EVC Constructor that makes a database with the specified string
	public SQLDatabase(String database) {
		try {
			Class.forName("org.sqlite.JDBC");
			this.curConection = DriverManager.getConnection("jdbc:sqlite:"+ database +".db");
			this.curStatement = this.curConection.createStatement();
			this.createTables();
		}catch(Exception e) {
			System.out.println(e.getClass().getName() +": "+ e.getMessage());
		}
		System.out.println("Table made correctly");
	}
	
	//Constructor sets the connection to the database TriviQuestionsDB.db
	public SQLDatabase() {
		try {
			Class.forName("org.sqlite.JDBC");
			this.curConection = DriverManager.getConnection("jdbc:sqlite:TrivaQuestionsDB.db");
			this.curStatement = this.curConection.createStatement();
			this.createTables();
		}catch(Exception e) {
			System.out.println(e.getClass().getName() +": "+ e.getMessage());
		}
		System.out.println("Table made correctly");
	}
	
	//Adds a Multiple choice question to the MultipleChoice table in the current database ADMIN USE ONLY
	public void addMultipleChoice(Scanner input){
		String question = "";
		String wrongAnswer1 = "";
		String wrongAnswer2 = "";
		String correctAnswer = "";
		String toInsert = "";
		boolean submit = true;
		
		do { 
			System.out.print("Enter the question you'd like to add: ");
			question = input.nextLine();
			
			System.out.print("\nEnter an incorrect answer: ");
			wrongAnswer1 = input.nextLine();
			
			System.out.print("\nEnter another incorrect answer: ");
			wrongAnswer2 = input.nextLine();
			
			System.out.print("\nEnter the correct answer: ");
			correctAnswer = input.nextLine();
			
			System.out.print("\nYour query is Quesiton: " + question + ", Wrong Answer 1: "
					+ wrongAnswer1 + "\nWrong Answer 2: " + wrongAnswer2 + ", Correct Answer: " + correctAnswer
					+"\nIs that correct? \n1. yes 2.no: ");
			
			if(input.nextLine().equalsIgnoreCase("1"))
				submit = false;
		}while(submit); 
		
		toInsert = "Insert into MultipleChoice (QUESTION, WRONG_ANSWER1, WRONG_ANSWER2, CORRECT_ANSWER)"
				+ "Values(" + "\"" +question + "\", " +"\"" + wrongAnswer1+"\", " + "\""+ wrongAnswer2 + "\", " + "\"" + correctAnswer+"\");";
			
		try {
			this.curStatement.executeUpdate(toInsert);
			System.out.println("New tuple successfully inserted!");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Adds a True False question to the TrueFalse table in the current database ADMIN USE ONLY
	public void addTrueFalse(Scanner input) {
		String question = "";
		String correctAnswer = "";
		String toInsert = "";
		boolean submit = true;
		
		do { 
			System.out.print("Enter the question you'd like to add: ");
			question = input.nextLine();
			
			System.out.print("\nEnter the correct answer: ");
			correctAnswer = input.nextLine();
			
			System.out.print("\nYour query is Quesiton: " + ", Correct Answer: " + correctAnswer
					+"\nIs that correct? \n1. yes \n2.no: ");
			
			if(input.nextLine().equalsIgnoreCase("1"))
				submit = false;
		}while(submit); 
		
		toInsert = "Insert into TrueFalse (QUESTION, CORRECT_ANSWER)"
				+ "Values(" + "\"" +question + "\", " + "\"" + correctAnswer+"\");";
		
		
		try {
			this.curStatement.executeUpdate(toInsert);
			System.out.println("New tuple successfully inserted!");
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Adds a Short Answer question to the ShortAnswer table in the current database ADMIN USE ONLY
	public void addShortAnswer(Scanner input) {
		String question = "";
		String correctAnswer = "";
		String toInsert = "";
		boolean submit = true;
		
		do { 
			System.out.print("Enter the question you'd like to add: ");
			question = input.nextLine();
			
			System.out.print("\nEnter the correct answer: ");
			correctAnswer = input.nextLine();
			
			System.out.print("\nYour query is Quesiton: " + ", Correct Answer: " + correctAnswer
					+"\nIs that correct? \n1. yes \n2.no: ");
			
			if(input.nextLine().equalsIgnoreCase("1"))
				submit = false;
		}while(submit); 
		
		toInsert = "Insert into ShortAnswer (QUESTION, CORRECT_ANSWER)"
				+ "Values(" + "\"" +question + "\", " + "\"" + correctAnswer+"\");";
		
		
		try {
			this.curStatement.executeUpdate(toInsert);
			System.out.println("New tuple successfully inserted!");
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Gets a random tuple from the Multiple Choice table in the current database returns it as a String ArrayList
	//in the form of ArrayList[0] = is the actual question ArrayList[1-2] = the wrong answers ArrayList[3] = is the correct answer
	public ArrayList<String> getMultipleChoice() {
		ArrayList<String> questionArray = new ArrayList<String>();
		int queryToReturn = 0;
		int maxNum = 0;
		String question = "";
		String wrongAnswer1 = "";
		String wrongAnswer2 = "";
		String correctAnswer = "";
		String query = "Select *"
				+ " from MultipleChoice "
				+ "where QUESTION_ID = ";
		
		
		try {
			this.queryResult = this.curStatement.executeQuery("Select max(QUESTION_ID) from MultipleChoice");
			
			maxNum = this.queryResult.getInt(1);
			
			queryToReturn = this.rand.nextInt(maxNum)+1;
			
			this.queryResult = this.curStatement.executeQuery(query+queryToReturn+";");
			
			while(this.queryResult.next()) {
				question = this.queryResult.getString("QUESTION");
				wrongAnswer1 = this.queryResult.getString("WRONG_ANSWER1");
				wrongAnswer2 = this.queryResult.getString("WRONG_ANSWER2");
				correctAnswer = this.queryResult.getString("CORRECT_ANSWER");
			}
		}catch(SQLException e) {
			System.out.println(e.getClass().getName() + " " + e.getMessage());
		}
		
		questionArray.add(question);
		questionArray.add(wrongAnswer1);
		questionArray.add(wrongAnswer2);
		questionArray.add(correctAnswer);
		
		return questionArray;
	}
	
	//Gets a random tuple from the True False table in the current database returns it as a string array
	//in the form of ArrayList[0] = is the actual question and ArrayList[1] = is the correct answer false or true*
	public ArrayList<String> getTrueFalse() {
		ArrayList<String> questionArray = new ArrayList<String>();
		int queryToReturn = 0;
		String question = "";
		String correctAnswer = "";
		String query = "Select QUESTION, CORRECT_ANSWER"
				+ " from TrueFalse "
				+ "where QUESTION_ID = ";
		
		try {
			this.queryResult = this.curStatement.executeQuery("Select max(QUESTION_ID) from TrueFalse");
		
			queryToReturn = this.rand.nextInt(queryResult.getInt(1))+1;
			
			this.queryResult = this.curStatement.executeQuery(query+queryToReturn+";");
			
			while(this.queryResult.next()) {
				question = this.queryResult.getString("QUESTION");
				correctAnswer = this.queryResult.getString("CORRECT_ANSWER");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		questionArray.add(question);
		questionArray.add(correctAnswer);
		
		return questionArray;
	
	}
	
	//Gets a random tuple from the True False table in the current database returns it as a string array
	//in the form of ArrayList[0] = is the actual question and ArrayList[1] = is the correct answer
	public ArrayList<String> getShortAnswer() {
		ArrayList<String> questionArray = new ArrayList<String>();
		int queryToReturn = 0;
		String question = "";
		String correctAnswer = "";
		String query = "Select QUESTION, CORRECT_ANSWER"
				+ " from ShortAnswer "
				+ "where QUESTION_ID = ";
		
		try {
			this.queryResult = this.curStatement.executeQuery("Select max(QUESTION_ID) from TrueFalse");
		
			queryToReturn = this.rand.nextInt(queryResult.getInt(1))+1;
			
			this.queryResult = this.curStatement.executeQuery(query+queryToReturn+";");
			
			while(this.queryResult.next()) {
				question = this.queryResult.getString("QUESTION");
				correctAnswer = this.queryResult.getString("CORRECT_ANSWER");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		questionArray.add(question);
		questionArray.add(correctAnswer);
		
		return questionArray;
	
	}
	
	//Closes the Connection and Statement Objects use only when the game is over
	public void closeDB() {
		try {
			this.curStatement.close();
			this.curConection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Creates the required tables in the current database if they currently do not exist
	public void createTables() {
		try {
			
			String multipleChoiceTable = "Create Table if not exists MultipleChoice "
					+ "(QUESTION_ID integer primary key autoincrement,"
					+ "QUESTION text not null,"
					+ "WRONG_ANSWER1 text not null,"
					+ "WRONG_ANSWER2 text not null,"
					+ "CORRECT_ANSWER text not null)";
			
			String trueFalse = "Create Table if not exists TrueFalse"
					+ "(QUESTION_ID integer primary key autoincrement,"
					+ "QUESTION text not null,"
					+ "CORRECT_ANSWER text not null)";
			
			String shortAnswer = "Create Table if not exists ShortAnswer"
					+ "(QUESTION_ID integer primary key autoincrement,"
					+ "QUESTION text not null,"
					+ "CORRECT_ANSWER text not null)";
			
			curStatement.executeUpdate(multipleChoiceTable);
			curStatement.executeUpdate(trueFalse);
			curStatement.executeUpdate(shortAnswer);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
		
	}
	
	//For testing purposes only
	public void testTables() {
		try {
			this.queryResult = curStatement.executeQuery("Select * from MultipleChoice;");
			
			System.out.println("QUESTION_ID\t QUESTION\t WRONG_ANSWER1\t WRONG_ANSWER2\t CORRECT_ANSWER");
			while(this.queryResult.next()) {
				int id = this.queryResult.getInt("QUESTION_ID");
				String question = this.queryResult.getString("QUESTION");
				String wrongAnswer1 = this.queryResult.getString("WRONG_ANSWER1");
				String wrongAnswer2 = this.queryResult.getString("WRONG_ANSWER2");
				String answer = this.queryResult.getString("CORRECT_ANSWER");
				System.out.println(id + "\t "+ question + "\t "+ wrongAnswer1 + "\t"+ wrongAnswer2+ "\t" + answer);
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Reads questions from a file and adds them to the Multiple choice table
	public void addMultipleChoiceFile(String fileName) {
		String question = "";
		String wrongAnswer1 = "";
		String wrongAnswer2 = "";
		String correctAnswer = "";
		String toInsert = "";
		File file = new File(fileName);
		try {
			Scanner fin = new Scanner(file);
			while (fin.hasNextLine()) {

				question = fin.nextLine();

				wrongAnswer1 = fin.nextLine();

				wrongAnswer2 = fin.nextLine();

				correctAnswer = fin.nextLine();

				toInsert = "Insert into MultipleChoice (QUESTION, WRONG_ANSWER1, WRONG_ANSWER2, CORRECT_ANSWER)"
						+ "Values(" + "\"" + question + "\", " + "\"" + wrongAnswer1 + "\", " + "\"" + wrongAnswer2
						+ "\", " + "\"" + correctAnswer + "\");";

				try {
					this.curStatement.executeUpdate(toInsert);
					System.out.println("New tuple successfully inserted!");
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
			fin.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}
	
	// Reads questions from a file and adds them to the TrueFalse table
		public void addTrueFalseFile(String fileName) {
			String question = "";
			String correctAnswer = "";
			String toInsert = "";
			File file = new File(fileName);
			try {
				Scanner fin = new Scanner(file);
				while (fin.hasNextLine()) {

					question = fin.nextLine();

					correctAnswer = fin.nextLine();

					toInsert = "Insert into TrueFalse (QUESTION, CORRECT_ANSWER)"
							+ "Values(" + "\"" + question + "\", " + "\"" + correctAnswer + "\");";

					try {
						this.curStatement.executeUpdate(toInsert);
						System.out.println("New tuple successfully inserted!");
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
				fin.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

		}
		
		// Reads questions from a file and adds them to the ShortAnswer table
				public void addShortAnswerFile(String fileName) {
					String question = "";
					String correctAnswer = "";
					String toInsert = "";
					File file = new File(fileName);
					try {
						Scanner fin = new Scanner(file);
						while (fin.hasNextLine()) {

							question = fin.nextLine();

							correctAnswer = fin.nextLine();

							toInsert = "Insert into ShortAnswer (QUESTION, CORRECT_ANSWER)"
									+ "Values(" + "\"" + question + "\", " + "\"" + correctAnswer + "\");";

							try {
								this.curStatement.executeUpdate(toInsert);
								System.out.println("New tuple successfully inserted!");
							} catch (SQLException e) {
								System.out.println(e.getMessage());
							}
						}
						fin.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}

				}
	
	
}
