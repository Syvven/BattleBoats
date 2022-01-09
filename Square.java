// Square
// Written by Noah Hendrickson

/*******************************************************************  
    Used as the individual indices in the game grid.
    Each square has its own row, column, and state that
    determines, if it has a ship in it, whether the ship has been
    hit or not, and if it does not have a ship, whether or not that
    square has been fired upon
*******************************************************************/
public class Square {
    public int row;
    public int col;
    public char state;

    // constructor
    public Square(int row, int col, char state) {
        this.row = row;
        this.col = col;
        this.state = state;
    }

    // getters and setters for row
    public void set_row(int row) {
        this.row = row;
    }

    public int get_row() {
        return this.row;
    }

    // getters and setters for column
    public void set_col(int col) {
        this.col = col;
    }

    public int get_col() {
        return this.col;
    }
    
    // getters and setters for state
    public void set_state(char state) {
        this.state = state;
    }

    public char get_state() {
        return this.state;
    }
} // Square