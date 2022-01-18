// Boat
// Written By Noah Hendrickson

/******************************************************************************************************
    Boat contains all the information needed to impelement the boats onto the board:
    size, orientation, the squares in which the boat is located, and the state of the ship as a whole
******************************************************************************************************/
public class Boat {
    private int size;
    private char orient;
    private Square[] loc;
    private boolean sunk;
    private String noExist = "This boat does not exist.";

    // constructor
    public Boat(int size) {
        this.size = size;
        this.loc = new Square[size];
        this.sunk = false;
    }

    // getters/setters for size
    public int get_size() { return this.size; }
    public void set_size(int size) { this.size = size; }

    // getters/setters for orientation
    public char get_orient() { return this.orient; }
    public void set_orient(char orient) { this.orient = orient; }

    // getters/setters for loc
    public Square[] get_loc() { return this.loc; }
    public void set_loc(Square[] loc) { this.loc = loc; }

    // getters/setters for sunk state
    public boolean get_sunk() { return this.sunk; }
    public void set_sunk() { 
        for (int i = 0; i < loc.length; i++) {
            if (loc[i].get_state() == 'B') {
                this.sunk = false;
            }
        }
        this.sunk = true;
    }

    /***********************************************************************
        Display:
            The purpose of this method is to determine which type of boat
            the specific boat object refers to, then print it
            This is determined based on the size of the boat object. 
        match size with 
        1 -> raft (* ixj grids where i or j == 3* ) 
        2 -> patrol boat (* ixj grids where i or j >= 3 *)
        3 -> submarine (* ixj grids where i or j >= 4 *)
        4 -> Battleship (* ixj grids where i or j >= 5 *)
        5 -> Carrier (* ixj grids where i or j >= 6 *)
        6 -> Oil Tanker (* ixj grids where i or j > 10 *)
    ************************************************************************/
    public void display() {
        String retString = "";

        // matches size with 1 - 6 and adds the corresponding boat name to the 
        // return string. If the boat is improperly sized, a proper message is returned.
        switch (this.size) {
            case 1: retString += "Raft";
                    break;
            case 2: retString += "Patrol Boat";
                    break;
            case 3: retString += "Submarine";
                    break;
            case 4: retString += "Battleship";
                    break;
            case 5: retString += "Carrier";
                    break;
            case 6: retString += "Oil Tanker";
                    break;
            default: retString += noExist;
                    break;
        }
        
        // adds the location data as well as state to the return string as long as 
        // the boat is properly sized
        if (retString.compareTo(noExist) != 0) {
            retString += " Coordinates: ";
            for (int i = 0; i < this.loc.length; i++) {
                retString += String.format("(%d, %d, %c)", 
                                this.loc[i].get_row(), 
                                this.loc[i].get_col(), 
                                this.loc[i].get_state());
            }   
        }
        System.out.println(retString);
    } // display

    // prints only the name of the boat
    public String toString() {
        String retString = "";
        // matches size with 1 - 6 and adds the corresponding boat name to the 
        // return string. If the boat is improperly sized, a proper message is returned.
        switch (this.size) {
            case 1: retString += "Raft";
                    break;
            case 2: retString += "Patrol Boat";
                    break;
            case 3: retString += "Submarine";
                    break;
            case 4: retString += "Battleship";
                    break;
            case 5: retString += "Carrier";
                    break;
            case 6: retString += "Oil Tanker";
                    break;
            default: retString += noExist;
                    break;
        }
        return retString;
    }
} // Boat