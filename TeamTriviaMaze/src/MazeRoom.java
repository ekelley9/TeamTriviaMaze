import java.io.Serializable;

public class MazeRoom implements Serializable{
	
	//boolean fields for each door in a room true means there is a door in that direction
	private static final long serialVersionUID = 8786708419275454576L;
	private boolean north = true;
	private boolean east = true;
	private boolean south = true;
	private boolean west = true;
	
	//fields to assist in various algorithms 
	private boolean isPlayerLocation = false;
	private boolean isAvailable = true;
	private boolean searched = false;
	
	public MazeRoom(boolean north, boolean east, boolean south, boolean west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
	}
	
	//returns whether the specified direction is a door
	public boolean isDoor(char direction) {
		if(direction == 'w')
			return this.north;
		
		if(direction == 'd')
			return this.east;
		
		if(direction == 's')
			return this.south;
		
		if(direction == 'a')
			return this.west;
		
		return false;
	}
	
	public void closeDoor(char direction) {
		if(direction == 'n') 
			this.north = false;
		
		if(direction == 's')
			this.south = false;
			
		if(direction == 'e')
			this.east = false;
		
		if(direction == 'w')
			this.west = false;
	}
	
	//gets the searched boolean for the room used in the maze path algorithm
	public boolean getSearched() {
		return this.searched;
	}
	
	//Sets the searched boolean for a room used in the maze path algorithm
	public void setSearched(boolean isSearched) {
		this.searched = isSearched;
	}
	
	//returns the string representing the top of the room
	public String roomTop() {
		if(this.north)
			return "* - *";
		
		return "* * *";
	}
	
	//returns the string representing the middle of the room
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
		
		
		if(!north && !south && !east && !west)
			playerString = "X";
		
		if(this instanceof ExitRoom)
			playerString = "E";
		
		return westString + playerString + eastString;
			
	}
	
	//returns the string representing the bottom of the room
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
