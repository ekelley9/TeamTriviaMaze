import java.io.Serializable;

public class Player implements Serializable{
	
	private static final long serialVersionUID = 11160681266846986L;
	private int playerRow;
	private int playerCol;
	
	//not passing in row and col because new players should always start at 0
	public Player() {
		playerRow = 0;
		playerCol = 0;
	}
	
	public void setPlayerRow(int row) { this.playerRow = row; }
	
	public void setPlayerCol(int col) { this.playerCol = col; }
	
	public int getPlayerRow() { return this.playerRow; }
	
	public int getPlayerCol() { return this.playerCol; }
}
