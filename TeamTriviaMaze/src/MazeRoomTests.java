import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MazeRoomTests {

	@Test
	void mazeRoomConstructor() {
		
		String correctOutput = "* - *\n" + 
				"| A |\n" + 
				"* - *";
		
		MazeRoom testRoom = new MazeRoom(true, true, true, true);
		
		
		assertEquals(correctOutput, testRoom.toString());
	}
	
	@Test
	void mazeRoomPlayerLocation() {
		
		String correctOutput = "* - *\n" + 
				"| P |\n" + 
				"* - *";
		
		MazeRoom testRoom = new MazeRoom(true, true, true, true);
		testRoom.setPlayerLocation(true);
		
		
		assertEquals(correctOutput, testRoom.toString());
	}
	
	@Test
	void mazeNoDoorsConstructor() {
		
		String correctOutput = "* * *\n" + 
				"* X *\n" + 
				"* * *";
		
		MazeRoom testRoom = new MazeRoom(false, false, false, false);
		
		
		assertEquals(correctOutput, testRoom.toString());
	}
	
	@Test
	void exitRoomConstructor() {
		
		String correctOutput = "* - *\n" + 
				"| E *\n" + 
				"* * *";
		
		MazeRoom testRoom = new ExitRoom();
		
		
		assertEquals(correctOutput, testRoom.toString());
	}
	
	@Test
	void mazeRoomSetSearched() {
		
		MazeRoom testRoom = new MazeRoom(true, true, true, true);
		testRoom.setSearched(true);
		
		
		assertTrue(testRoom.getSearched());
	}
	
	@Test
	void mazeisDoor() {
		
		MazeRoom testRoom = new MazeRoom(true, true, true, true);
		
		
		
		assertTrue(testRoom.isDoor('w'));
	}
	
	@Test
	void mazeisCloseDoor() {
		
		MazeRoom testRoom = new MazeRoom(true, true, true, true);
		
		testRoom.closeDoor('e');
		
		assertFalse(testRoom.isDoor('d'));
	}

}
