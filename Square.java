// Square
// Written by Noah Hendrickson

/*******************************************************************  
    Used as the individual indices in the game grid.
    Each square has its own row, column, and state that
    determines, if it has a ship in it, whether the ship has been
    hit or not, and if it does not have a ship, whether or not that
    square has been fired upon
    States for squares include: 
        -> B: A boat occupies this square
        -> X: A boat occupying this square has been hit
        -> -: A square with no boat has been hit
        -> ~: Default state for each square 
        -> D: indicates a sunk decoy
*******************************************************************/
public class Square {
    private int row;
    private int col;
    private char state;

    // constructor
    public Square(int row, int col, char state) {
        this.row = row;
        this.col = col;
        this.state = state;
    } 

    // getters and setters for row
    public void set_row(int row) {
        this.row = row;
    } // set_row

    public int get_row() {
        return this.row;
    } // get_row

    // getters and setters for column
    public void set_col(int col) {
        this.col = col;
    } // set_col

    public int get_col() {
        return this.col;
    } // get_col
    
    // getters and setters for state
    public void set_state(char state) {
        this.state = state;
    } // set_state

    public char get_state() {
        return this.state;
    } // get_state
} // Square