// Grid
// Written by Noah Hendrickson

import java.lang.Math;
import java.util.Scanner;

// implemented as a 2d array of cells that contain information about game state
public class Grid {
    // initialize private variables to be used in class
    private Square[][] grid;
    private Boat[] boats;
    private int rows;
    private int cols;
    private int shots;
    private int turns;
    private int shipsLeft;
    private int shipsSunk;

    // Constructor
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        fill_grid();
        generate_boats();

        this.turns = 0;
        this.shots = 0;
        this.shipsSunk = 0;
        this.shipsLeft = this.boats.length;
    } // Constructor

    // helper to fill the grid with squares all set to '-'
    public void fill_grid() {
        this.grid = new Square[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.grid[i][j] = new Square(i, j, '~');
            }
        }
    } // fill_grid

    // method to generate boats for the board based on the rows and columns
    public void generate_boats() {
        int min = Math.min(this.rows, this.cols);

        // 2 boats generated if 3 rows or 3 columns
        if (min == 3) {
            this.boats = new Boat[2];
            this.boats[0] = new Boat(1);
            this.boats[1] = new Boat(2);

        // 3 boats generated if 4 rows or 4 columns
        } else if (min == 4) {
            this.boats = new Boat[2];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(3);

        // 3 boats generated if 5 or 6 rows or columns
        } else if (5 <= min && min <= 6) {
            this.boats = new Boat[3];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(3);
            this.boats[2] = new Boat(3);

        // 4 boats generated if 7 or 8 rows or columns
        } else if (7 <= min && min <= 8) {
            this.boats = new Boat[4];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(3);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(4);

        // 5 boats generated if 9 or 10 rows or columns
        } else if (9 <= min && min <= 10) {
            this.boats = new Boat[5];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(3);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(4);
            this.boats[4] = new Boat(5);

        // 6 boats generated if 11, 12, or 13 rows or columns
        } else if (11 <= min && min <= 13) {
            this.boats = new Boat[6];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(3);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(4);
            this.boats[4] = new Boat(5);
            this.boats[5] = new Boat(6);

        // 7 boats generated if 14 or 15 rows or columns
        } else if (14 <= min && min <= 15) {
            this.boats = new Boat[7];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(3);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(4);
            this.boats[4] = new Boat(5);
            this.boats[5] = new Boat(6);
            this.boats[6] = new Boat(6);
        } 
    } // generate_boats

    public void place_boats_player() {

    }

    // method for randomly placing boats in the case that an AI is playing
    public void place_boats_AI() {
        int numBoat = this.boats.length;
        while (numBoat != 0) {
            char orient;
            int row = (int)(Math.random()*this.rows);
            int col = (int)(Math.random()*this.cols);
            double way = Math.random();
            Boat curBoat = this.boats[numBoat-1];

            if (way > 0.5) {
                orient = 'V';
            } else {
                orient = 'H';
            }

            int success = place_boat(curBoat, row, col, orient);
            if (success == 0) {
                numBoat -= 1;
            }
        }
    } // place_boats_AI
    
    public int place_boat(Boat boat, int row, int col, char orient) {
        if (orient == 'V') {
            if ((row - (boat.get_size() - 1)) < 0) {
                return -1;
            } else {
                Square[] loc = new Square[boat.get_size()];
                for (int i = 0; i < loc.length; i++) {
                    if (this.grid[row-i][col].get_state() == 'B') {
                        return -1;
                    }
                }

                for (int i = 0; i < loc.length; i++) {
                    loc[i] = this.grid[row-i][col];
                    this.grid[row-i][col].set_state('B');
                }
                boat.set_loc(loc);
                boat.set_orient('V');
            }
        } else if (orient == 'H') {
            if ((col + (boat.get_size() - 1)) >= this.cols) {
                return -1;
            } else {
                Square[] loc = new Square[boat.get_size()];
                for (int i = 0; i < loc.length; i++) {
                    if (this.grid[row][col+1].get_state() == 'B') {
                        return -1;
                    }
                }
                for (int i = 0; i < loc.length; i++) {
                    loc[i] = this.grid[row][col+i];
                    this.grid[row][col+i].set_state('B');
                }
                boat.set_loc(loc);
                boat.set_orient('H');
            }
        }
        return 0;
    } // place_boat

    public void fire(int row, int col) {} // fire

    // displays the gameboard with the relevant information visible
    public void display() {
        String returnString = "";
        for (int i = -1; i < this.rows; i++) {
            for (int j = -1; j < this.cols; j++) {
                if (i == -1 && j == -1) {
                    returnString += "    ";
                } else if (j == -1) {
                    if (i >= 10) {
                        returnString += i + "  ";
                    } else {
                        returnString += " " + i + "  ";
                    }
                } else if (i == -1) {
                    if (j >= 10) {
                        returnString += j + "  ";
                    } else {
                        returnString += j + "   ";
                    }
                } else {
                    char state = this.grid[i][j].get_state();
                    if (state == 'X' || state == '-' || state == 'O') {
                        returnString += state + "   ";
                    } else {
                        returnString += "?   ";
                    }
                }
            }
            returnString += "\n";
        }
        System.out.println(returnString);
        System.out.printf("Turn %d\n", this.turns);
        System.out.printf("Ships Remaining: %d\n", this.shipsLeft);
        System.out.printf("Ships Sunk: %d\n", this.shipsSunk);
    } // display

    // sets how many ships have been sunk by checking the state of each
    public void set_ships_sunk() {
        int counter = 0;
        for (int i = 0; i < this.boats.length; i++) {
            if (this.boats[i].get_sunk()) {
                counter++;
            }
        }
        this.shipsSunk = counter;
    } // set_ships_sunk

    // getters
    public Boat[] get_boats() { return this.boats; }
    public int get_shots() { return this.shots; }
    public int get_turns() { return this.turns; }
    public int get_shipsLeft() { return this.shipsLeft; }

    // toString to print the board in a readable fashion
    // each row and column is numbered from 0 to 1
    public String toString() {
        String returnString = "";
        for (int i = -1; i < this.rows; i++) {
            for (int j = -1; j < this.cols; j++) {
                if (i == -1 && j == -1) {
                    returnString += "    ";
                } else if (j == -1) {
                    if (i >= 10) {
                        returnString += i + "  ";
                    } else {
                        returnString += " " + i + "  ";
                    }
                } else if (i == -1) {
                    if (j >= 10) {
                        returnString += j + "  ";
                    } else {
                        returnString += j + "   ";
                    }
                } else {
                    returnString += this.grid[i][j].get_state() + "   ";
                }
            }
            returnString += "\n";
        }
        return returnString;
    } // toString

} // Grid