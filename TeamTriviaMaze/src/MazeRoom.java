
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
		String westString = "";
		String eastString = "";
		String playerString = "";
		
		if(this.west && this.east) {
			westString = "| ";
			eastString = " |";
		}
		
		if(this.west && !this.east) {
			westString = "| ";
			eastString = " *";
		}
		
		if(!this.west && this.east) {
			westString = "* ";
			eastString = " |";
		}
		
		if(!this.west && !this.east) {
			westString = "* ";
			eastString = " *";
		}
		
		if(this.isPlayerLocation) 
			playerString = "P";
		
		
		if(!this.isPlayerLocation && this.isAvailable) 
			playerString = "A";
		
		
		if(!this.isPlayerLocation && !this.isAvailable)
			playerString = "X";
		
		if(!this.south && !this.east)
			playerString = "E";
		
		return westString + playerString + eastString;
			
	}
	
	public String roomBottom() {
		if(this.south)
			return "* - *";
		
		return "* * *";
	}
	
	public void setPlayerLocation(boolean isPlayer) {
		this.isPlayerLocation = isPlayer;
	}
	
	public String toString() {
		return this.roomTop() + "\n"+ this.roomMid() + "\n" + this.roomBottom();
	}
}
