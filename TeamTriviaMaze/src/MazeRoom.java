
public class MazeRoom {
	private boolean north = true;
	private boolean east = true;
	private boolean south = true;
	private boolean west = true;
	private boolean isPlayerLocation = false;
	private boolean isAvailable = true;
	
	public MazeRoom(boolean north, boolean east, boolean south, boolean west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
	}
	
	public boolean isDoor(char direction) {
		if(direction == 'n')
			return this.north;
		
		if(direction == 'e')
			return this.east;
		
		if(direction == 's')
			return this.south;
		
		if(direction == 'w')
			return this.south;
		
		return false;
	}
	
	public String roomTop() {
		if(this.north)
			return "* - *";
		
		return "* * *";
	}
	
	public String roomMid() {
		String west = "";
		String east = "";
		String player = "";
		
		if(this.west && this.east) {
			west = "| ";
			east = " |";
		}
		
		if(this.west && !this.east) {
			west = "| ";
			east = " *";
		}
		
		if(!this.west && this.east) {
			west = "* ";
			east = " |";
		}
		
		if(!this.west && !this.east) {
			west = "* ";
			east = " *";
		}
		
		if(this.isPlayerLocation) 
			player = "P";
		
		
		if(!this.isPlayerLocation && this.isAvailable) 
			player = "A";
		
		
		if(!this.isPlayerLocation && !this.isAvailable)
			player = "X";
		
		return west + player + east;
			
	}
	
	public String roomBottom() {
		if(this.south)
			return "* - *";
		
		return "* * *";
	}
	
	public void setPlayer(boolean isPlayer) {
		this.isPlayerLocation = isPlayer;
	}
	
	public String toString() {
		return this.roomTop() + "\n"+ this.roomMid() + "\n" + this.roomBottom();
	}
}
