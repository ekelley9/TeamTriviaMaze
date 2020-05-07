
public class TriviaMaze {
	private int playerRow = 0;
	private int playerCol = 0;
	private MazeRoom[][] theMaze;

	public TriviaMaze(int row, int column) {
		this.theMaze = new MazeRoom[row][column];
		for (int x = 0; x < theMaze.length; x++) {
			for (int y = 0; y < theMaze[x].length; y++) {
				theMaze[x][y] = new MazeRoom(true, true, true, true);
			}
		}
		this.addBorders();
		this.theMaze[0][0].setPlayer(true);
		this.theMaze[row-1][column-1] = new ExitRoom();
	}

	public void addBorders() {
		for (int i = 0; i < theMaze.length; i++) {
			theMaze[0][i] = new MazeRoom(false, true, true, true);
			theMaze[theMaze[i].length - 1][i] = new MazeRoom(true, true, false, false);
			theMaze[i][0] = new MazeRoom(true, true, true, false);
			theMaze[i][theMaze[i].length - 1] = new MazeRoom(true, false, true, true);
		}

		theMaze[0][theMaze[1].length - 1] = new MazeRoom(false, false, true, true);
		theMaze[theMaze[1].length - 1][0] = new MazeRoom(true, true, false, false);

	}

	public void printMaze() {

		for (int i = 0; i < this.theMaze.length; i++) {
			for (int j = 0; j < this.theMaze.length; j++) {
				System.out.print(this.theMaze[i][j].roomTop() + " ");
			}
			System.out.println();
			for (int k = 0; k < this.theMaze.length; k++) {
				System.out.print(this.theMaze[i][k].roomMid() + " ");
			}
			System.out.println();
			for (int l = 0; l < this.theMaze.length; l++) {
				System.out.print(this.theMaze[i][l].roomBottom() + " ");
			}
			System.out.println();

		}
	}

}
