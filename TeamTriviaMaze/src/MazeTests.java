import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MazeTests {

	@Test
	public void mazeParserEdgeCase() {
		TriviaMaze testMaze = new TriviaMaze(4, 4, new Player(3, 2));
		
		assertTrue(testMaze.mazeParser(3, 2));
	}
	
	@Test
	public void mazeParserNoValidPath() {
		TriviaMaze testMaze = new TriviaMaze(4, 4);
		testMaze.closePath('w', 3, 3);
		testMaze.closePath('a', 3, 3);
		
		
		assertFalse(testMaze.mazeParser(0, 0));
	}
	
	@Test
	public void mazePathClose() {
		TriviaMaze testMaze = new TriviaMaze(4, 4);
		Player player = new Player(2,2);
		testMaze.closePath('d', player);
		testMaze.closePath('a', player);
		
		assertFalse(testMaze.curRoomIsDoor(player, "d"));
		assertFalse(testMaze.curRoomIsDoor(player, "a"));
	}
	
	@Test
	public void mazeConstructor() {
		String correctOutput = "* * * * * * * * * * * * \n" + 
				"* P | | A | | A | | A * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* A | | A | | A | | A * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* A | | A | | A | | A * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* A | | A | | A | | E * \n" + 
				"* * * * * * * * * * * * \n";
		
		TriviaMaze testMaze = new TriviaMaze(4,4);
		
		
		assertEquals(correctOutput, testMaze.toString());
	}
	
	@Test
	public void movePlayer() {
		String correctOutput = "* * * * * * * * * * * * \n" + 
				"* A | | P | | A | | A * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* A | | A | | A | | A * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* A | | A | | A | | A * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* - * * - * * - * * - * \n" + 
				"* A | | A | | A | | E * \n" + 
				"* * * * * * * * * * * * \n";
		
		TriviaMaze testMaze = new TriviaMaze(4,4);
		Player player = new Player();
		testMaze.move(player, "d");
		
		assertEquals(correctOutput, testMaze.toString());
		
	}

}
