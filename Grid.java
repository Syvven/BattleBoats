// Grid
// Written by Noah Hendrickson

import java.util.Scanner;
import java.util.Random;

// implemented as a 2d array of cells that contain information about game state
public class Grid {
    // initialize private variables to be used in class
    private Square[][] grid;
    private Boat[] boats;
    private int rows;
    private int cols;
    private int shots;
    private int turns;
    private int boatsLeft;
    private int boatsSunk;
    private Scanner s;
    private String playerName;
    private String type;
    private int[][] weightGrid;

    // Constructor
    public Grid(int rows, int cols, Scanner s, String playerName, String type) {
        this.rows = rows;
        this.cols = cols;
        this.s = s;
        this.playerName = playerName;
        this.type = type;

        // creates the grid and generates the boat based off the grid size
        fill_grid();
        generate_boats();

        this.turns = 0;
        this.shots = 0;
        this.boatsSunk = 0;
        this.boatsLeft = this.boats.length;
        if (this.type.compareTo("AI") == 0) {
            AI_init_weight_grid();
        }
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
        if (min == 4) {
            this.boats = new Boat[3];
            this.boats[0] = new Boat(1);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);

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
        } else if (16 <= min && min <= 18) {
            this.boats = new Boat[8];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(3);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(4);
            this.boats[4] = new Boat(5);
            this.boats[5] = new Boat(6);
            this.boats[6] = new Boat(6);
            this.boats[7] = new Boat(6);
        } else if (19 <= min && min <= 21) {
            this.boats = new Boat[9];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(3);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(4);
            this.boats[4] = new Boat(5);
            this.boats[5] = new Boat(5);
            this.boats[6] = new Boat(6);
            this.boats[7] = new Boat(6);
            this.boats[8] = new Boat(6);
        } else if (22 <= min && min <= 24) {
            this.boats = new Boat[11];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(3);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(5);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(6);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
        } else if (25 <= min && min <= 27) {
            this.boats = new Boat[13];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(4);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(5);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
            this.boats[11] = new Boat(6);
            this.boats[12] = new Boat(6);
        } else if (28 <= min && min <= 30) {
            this.boats = new Boat[16];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(4);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(5);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
            this.boats[11] = new Boat(6);
            this.boats[12] = new Boat(6);
            this.boats[13] = new Boat(6);
            this.boats[14] = new Boat(6);
            this.boats[15] = new Boat(6);
        } else if (31 <= min && min <= 33) {
            this.boats = new Boat[19];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(4);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(5);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
            this.boats[11] = new Boat(6);
            this.boats[12] = new Boat(6);
            this.boats[13] = new Boat(6);
            this.boats[14] = new Boat(6);
            this.boats[15] = new Boat(6);
            this.boats[16] = new Boat(6);
            this.boats[17] = new Boat(6);
            this.boats[18] = new Boat(6);
        } else if (34 <= min && min <= 36) {
            this.boats = new Boat[22];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(4);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(5);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
            this.boats[11] = new Boat(6);
            this.boats[12] = new Boat(6);
            this.boats[13] = new Boat(6);
            this.boats[14] = new Boat(6);
            this.boats[15] = new Boat(6);
            this.boats[16] = new Boat(6);
            this.boats[17] = new Boat(6);
            this.boats[18] = new Boat(6);
            this.boats[19] = new Boat(6);
            this.boats[20] = new Boat(6);
            this.boats[21] = new Boat(6);
        } else if (37 <= min && min <= 39) {
            this.boats = new Boat[25];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(4);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(5);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
            this.boats[11] = new Boat(6);
            this.boats[12] = new Boat(6);
            this.boats[13] = new Boat(6);
            this.boats[14] = new Boat(6);
            this.boats[15] = new Boat(6);
            this.boats[16] = new Boat(6);
            this.boats[17] = new Boat(6);
            this.boats[18] = new Boat(6);
            this.boats[19] = new Boat(6);
            this.boats[20] = new Boat(6);
            this.boats[21] = new Boat(6);
            this.boats[22] = new Boat(6);
            this.boats[23] = new Boat(6);
            this.boats[24] = new Boat(6);
        } else if (40 <= min && min <= 42) {
            this.boats = new Boat[28];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(4);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(5);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
            this.boats[11] = new Boat(6);
            this.boats[12] = new Boat(6);
            this.boats[13] = new Boat(6);
            this.boats[14] = new Boat(6);
            this.boats[15] = new Boat(6);
            this.boats[16] = new Boat(6);
            this.boats[17] = new Boat(6);
            this.boats[18] = new Boat(6);
            this.boats[19] = new Boat(6);
            this.boats[20] = new Boat(6);
            this.boats[21] = new Boat(6);
            this.boats[22] = new Boat(6);
            this.boats[23] = new Boat(6);
            this.boats[24] = new Boat(6);
            this.boats[25] = new Boat(6);
            this.boats[26] = new Boat(6);
            this.boats[27] = new Boat(6);
        } else if (43 <= min && min <= 45) {
            this.boats = new Boat[31];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(4);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(5);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
            this.boats[11] = new Boat(6);
            this.boats[12] = new Boat(6);
            this.boats[13] = new Boat(6);
            this.boats[14] = new Boat(6);
            this.boats[15] = new Boat(6);
            this.boats[16] = new Boat(6);
            this.boats[17] = new Boat(6);
            this.boats[18] = new Boat(6);
            this.boats[19] = new Boat(6);
            this.boats[20] = new Boat(6);
            this.boats[21] = new Boat(6);
            this.boats[22] = new Boat(6);
            this.boats[23] = new Boat(6);
            this.boats[24] = new Boat(6);
            this.boats[25] = new Boat(6);
            this.boats[26] = new Boat(6);
            this.boats[27] = new Boat(6);
            this.boats[28] = new Boat(6);
            this.boats[29] = new Boat(6);
            this.boats[30] = new Boat(6);
        } else if (46 <= min && min <= 48) {
            this.boats = new Boat[34];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(4);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(5);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
            this.boats[11] = new Boat(6);
            this.boats[12] = new Boat(6);
            this.boats[13] = new Boat(6);
            this.boats[14] = new Boat(6);
            this.boats[15] = new Boat(6);
            this.boats[16] = new Boat(6);
            this.boats[17] = new Boat(6);
            this.boats[18] = new Boat(6);
            this.boats[19] = new Boat(6);
            this.boats[20] = new Boat(6);
            this.boats[21] = new Boat(6);
            this.boats[22] = new Boat(6);
            this.boats[23] = new Boat(6);
            this.boats[24] = new Boat(6);
            this.boats[25] = new Boat(6);
            this.boats[26] = new Boat(6);
            this.boats[27] = new Boat(6);
            this.boats[28] = new Boat(6);
            this.boats[29] = new Boat(6);
            this.boats[30] = new Boat(6);
            this.boats[31] = new Boat(6);
            this.boats[32] = new Boat(6);
            this.boats[33] = new Boat(6);
        } else if (49 <= min && min <= 50) {
            this.boats = new Boat[37];
            this.boats[0] = new Boat(2);
            this.boats[1] = new Boat(2);
            this.boats[2] = new Boat(3);
            this.boats[3] = new Boat(3);
            this.boats[4] = new Boat(3);
            this.boats[5] = new Boat(4);
            this.boats[6] = new Boat(4);
            this.boats[7] = new Boat(5);
            this.boats[8] = new Boat(5);
            this.boats[9] = new Boat(6);
            this.boats[10] = new Boat(6);
            this.boats[11] = new Boat(6);
            this.boats[12] = new Boat(6);
            this.boats[13] = new Boat(6);
            this.boats[14] = new Boat(6);
            this.boats[15] = new Boat(6);
            this.boats[16] = new Boat(6);
            this.boats[17] = new Boat(6);
            this.boats[18] = new Boat(6);
            this.boats[19] = new Boat(6);
            this.boats[20] = new Boat(6);
            this.boats[21] = new Boat(6);
            this.boats[22] = new Boat(6);
            this.boats[23] = new Boat(6);
            this.boats[24] = new Boat(6);
            this.boats[25] = new Boat(6);
            this.boats[26] = new Boat(6);
            this.boats[27] = new Boat(6);
            this.boats[28] = new Boat(6);
            this.boats[29] = new Boat(6);
            this.boats[30] = new Boat(6);
            this.boats[31] = new Boat(6);
            this.boats[32] = new Boat(6);
            this.boats[33] = new Boat(6);
            this.boats[34] = new Boat(6);
            this.boats[35] = new Boat(6);
            this.boats[36] = new Boat(6);
        }
    } // generate_boats

    // prompts the player to place their boats
    public void place_boats_player() {

        // initiates variables that will be needed
        int numBoat = this.boats.length;
        int row = 0;
        int col = 0;
        char orient = ' ';
        boolean valid = true;
        String action = "";
        String whole = "";

        /******************************************************************
            This loop is primarily to display help information for
            the boat placement. User inputs either help or cont and
            anything else will prompt the user to input again.
        ******************************************************************/
        System.out.println("\n" + this.playerName + " Please Specify Where to Place Your Boats.\n" + 
                           "Alternatively, input \"auto-place\" to have boats placed randomly.");
        System.out.print("If you would like information on how to place boats, input \"help\", otherwise, input \"cont\": ");
        while (valid) {
            action = this.s.nextLine();
            if (action.compareTo("help") == 0) {
                String printString = "\nPlacement Information:\n-> The user will specify an orientation, followed by row and column for the boat.\n" +
                                     "-> The specified row and column will indicate where the base of the boat is.\n" +
                                     "-> From the base, the boat will extend upwards on the board if vertical, and to the right if horizontal.\n" +
                                     "-> If the boat were to go out of bounds when placed, the user will be prompted to input a new starting location.\n" +
                                     "-> If the boat were to extend on top of another boat, the user will be prompted to input a new starting location.\n" +
                                     "-> If the boat is able to be placed, the user will be prompted to place another boat until all boats have been placed.\n";
                System.out.println(printString);
                System.out.print("Input \"cont\" to continue to placement or \"help\" to reprint placement information: ");
            } else if (action.compareTo("cont") == 0) {
                valid = false;
            } else if (action.compareTo("auto-place") == 0) {
                // case for when the player does not want to place their own boats
                place_boats_AI();
                return;
            } else {
                System.out.print("Please enter either \"help\" for more info, \"cont\" to continue to placement, or \"auto-place\" to place boats randomly: ");
            }
        }

        /***************************************************************
            Main Loop
            Contains sub loops to initate the variables needed
            to place boats. If the boat is not placed, the loop 
            restarts. Tracking variable checks when the last boat
            has been placed and terminates the loop if it has.
        ***************************************************************/
        System.out.println("\n" + toString());
        while (numBoat != 0) {
            // sets the current boat being placed
            Boat curBoat = this.boats[numBoat-1];

            /************************************************************
                First variable being set is orientation.
                The user inputs either V or H for vertical or 
                horizontal. If the user implements something else, the
                user is prompted to enter it again.
            ************************************************************/
            valid = true;
            System.out.printf("Placing %s (Size %d)\n", curBoat, curBoat.get_size());
            System.out.print("Orientation ('V' for vertical, 'H' for horizontal): ");
            while (valid) {
                whole = s.nextLine();
                orient = whole.charAt(0);
                if (whole.length() != 1) {
                    System.out.print("Please enter a single character 'V' for vertical or 'H' for horizontal: ");
                } else if (orient != 'V' && orient != 'H') {
                    System.out.print("Please enter 'V' for vertical or 'H' for horizontal: ");
                } else {
                    valid = false;
                }
            }

            /************************************************************
                Second variable being set is the starting row.
                The user inputs a number that is within bounds of the
                set rows. If it is out of bounds, the user is prompted
                for a new row. If the user does not enter an integer
                they are prompted again to input row.
            ************************************************************/
            valid = true;
            System.out.print("Row: ");
            while (valid) {
                try {
                    row = s.nextInt();
                    if (row >= this.rows || row < 0) {
                        System.out.println("Row Out of Bounds.");
                        System.out.print("Please enter a valid row: ");
                    } else {
                        valid = false;
                    }
                } catch (Exception e) {
                    System.out.print("Please enter an integer for starting row: ");
                    s.nextLine();
                }
            }

            /************************************************************
                Third variable being set is the starting column.
                The user inputs a number that is within bounds of the
                set columns. If it is out of bounds, the user is prompted
                for a new column. If the user does not enter an integer
                they are prompted again to input column.
            ************************************************************/
            valid = true;
            System.out.print("Column: ");
            while (valid) {
                try {
                    col = s.nextInt();
                    if (col >= this.cols || col < 0) {
                        System.out.println("Column Out of Bounds.");
                        System.out.print("Please enter a valid column: ");
                    } else {
                        valid = false;
                    }
                } catch (Exception e) {
                    System.out.print("Please enter an integer for starting column: ");
                    s.nextLine();
                }
            }

            s.nextLine();

            /************************************************************
                The fnial block calls the place_boat function on the
                current boat with the given row, col, orientation.
                If it fails, the user restarts the variable assignment
                process. If it does not fail, the board with the boat 
                placed is displayed and the next boat is selected.
            ************************************************************/
            int success = place_boat(curBoat, row, col, orient);
            if (success == -1) {
                System.out.println("\nBoat Not Placed. Please Enter Valid Starting Coordinates.");
            } else {
                System.out.printf("\n%s placed.\n\n", curBoat);
                numBoat--;
                System.out.println(toString());
            }
        }
        System.out.println("All Boats Placed!");
    } // place_boats_player 

    // method for randomly placing boats in the case that an AI is playing
    public void place_boats_AI() {
        System.out.println(this.playerName + " is Placing their Boats.\n");
        /*************************************************************
            initiates a tracker variable for loop termination as well as 
            to determine which boat is being placed and starts the loop.
            Loops until the last boat has been placed. 
        *************************************************************/
        int numBoat = this.boats.length;
        while (numBoat != 0) {
            
            // the row, column, and orientation are randomly generated for each boat in the boat array of the AI.
            char orient;
            int row = (int)(Math.random()*this.rows);
            int col = (int)(Math.random()*this.cols);
            double way = Math.random();
            Boat curBoat = this.boats[numBoat-1];

            // determines the orientation based on what number is randomly generated
            if (way > 0.5) {
                orient = 'V';
            } else {
                orient = 'H';
            }

            /******************************************************************************* 
                attempts to place the boat at the generated coordinates
                if the boat is not successfully placed, new random coordinates are generated 
                if the boat is successfully placed, the tracking variable is decremented and 
                coordinates are generated for the next boat
            *******************************************************************************/
            int success = place_boat(curBoat, row, col, orient);
            if (success == 0) {
                numBoat -= 1;
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                System.out.printf(this.playerName + " %s has been placed.\n", curBoat);
            }
        }
        System.out.println("\n" + this.playerName + " has placed all their Boats\n");
    } // place_boats_AI
    
    /*************************************************************************
        Method to place boats. returns 0 on success and -1 on failure.
        The input row and column determine where the first square of the boat
        location will be. If orientation is vertical, the subsequent boat
        location squares will be the squares directly above the input square.
        If the orientation is horizontal, the subsequent boat location
        squares will be the squares directly to the right of the input square.
        The boat will fail to be placed when the starting location is
        close enough to either the top or right edge of the board such that
        a portion of the boat would be out of bounds. Otherwise, success.
    *************************************************************************/
    public int place_boat(Boat boat, int row, int col, char orient) {

        // input orientation is 'V' for vertical
        if (orient == 'V') {

            // checks if the boat can be placed at the specified coordinate, fails if not
            if ((row - (boat.get_size() - 1)) < 0) {
                return -1;
            } else {

                // checks if the boat would intersect with any other boat, fails if so
                Square[] loc = new Square[boat.get_size()];
                for (int i = 0; i < loc.length; i++) {
                    if (this.grid[row-i][col].get_state() == 'B') {
                        return -1;
                    }
                }

                /**************************************************
                    will only get here if the boat can be placed
                    sets each element of the location array of the 
                    boat to each of the squares that the boat will
                    occupy on the grid
                **************************************************/
                for (int i = 0; i < loc.length; i++) {
                    loc[i] = this.grid[row-i][col];
                    this.grid[row-i][col].set_state('B');
                }

                // sets the location array of boat as well as the orientation
                boat.set_loc(loc);
                boat.set_orient('V');
            }

        // input orientation is 'H' for horizontal
        } else if (orient == 'H') {

            // checks if the boat can be placed at the specified coordinate, fails if not
            if ((col + (boat.get_size() - 1)) >= this.cols) {
                return -1;
            } else {

                // checks if the boat would intersect with any other boat, fails if so
                Square[] loc = new Square[boat.get_size()];
                for (int i = 0; i < loc.length; i++) {
                    if (this.grid[row][col+i].get_state() == 'B') {
                        return -1;
                    }
                }

                /**************************************************
                    will only get here if the boat can be placed
                    sets each element of the location array of the 
                    boat to each of the squares that the boat will
                    occupy on the grid
                **************************************************/
                for (int i = 0; i < loc.length; i++) {
                    loc[i] = this.grid[row][col+i];
                    this.grid[row][col+i].set_state('B');
                }

                // sets the location array of boat as well as the orientation
                boat.set_loc(loc);
                boat.set_orient('H');
            }
        }
        // success
        return 0;
    } // place_boat

    public boolean fire(int row, int col) {
        if (this.grid[row][col].get_state() == 'B') {
            this.grid[row][col].set_state('X');
            return true;
        } else {
            this.grid[row][col].set_state('-');
            return false;
        }
    } // fire

    public void powerup() {

    }

    /*****************************************************************
        Radar Bomb:
            -> takes in a row and column for the middle of the bomb
            -> hits an area of 3 rows x 4 cols
            -> returns a number based on how many hitable squares are
               in the area
            -> charge time: 5 turns
    *****************************************************************/
    public int radar_bomb() {
        return 0;
    }

    /****************************************************************
        Multi Shot:
            -> prompts the user to fire multiple times rather than
               just once
            -> charge time: 5 turns
    ****************************************************************/
    public void multi_shot() {

    }

    /***************************************************************
        Scatter Shot:
            -> takes in a row and column to scatter shot around
            -> randomly hits 3 squares within a 5x5 radius of the 
               input row/column
            -> charge time: 7 turns
    ***************************************************************/
    public void scatter_shot() {

    }

    /**************************************************************
        Move:
            -> moves a ship one square in any direction
            -> ship has to have been hit 
            -> charge time: 8 turns
    **************************************************************/
    public void move_ship() {

    }

    /****************************************************************
        Decoy:
            -> Places a decoy ship
            -> takes up 2 squares
            -> only one decoy can be placed at a time
            -> only starts charging once it has been destroyed
            -> charge time: 5 turns
            -> if hit by airstrike or torpedo, immediately destoyed
    ****************************************************************/
    public void decoy() {

    }

    // displays the gameboard with the relevant information visible
    public void display() {
        String returnString = this.playerName + '\n';
        for (int i = -1; i < this.rows; i++) {
            for (int j = -1; j < this.cols; j++) {
                if (i == -1 && j == -1) {
                    returnString += "    ";
                } else if (j == -1 && i >= 10) {
                    returnString += i + "  ";
                } else if (j == -1) {
                    returnString += " " + i + "  ";
                } else if (i == -1 && j >= 10) {
                    returnString += j + "  ";
                } else if (i == -1) {
                    returnString += j + "   ";
                } else {
                    char state = this.grid[i][j].get_state();
                    if (state == 'X' || state == '-' || state == 'O' || state == 'S') {
                        returnString += state + "   ";
                    } else {
                        returnString += "?   ";
                    }
                }
            }
            returnString += "\n";
        }

        /***********************************************************
            prints out the constructed board as well as which turn 
            the game is on and the ships that are left/have been sunk
        ***********************************************************/
        System.out.println(returnString);
        System.out.printf("Turn %d\n", this.turns);
        System.out.printf("Ships Remaining: %d\n", this.boatsLeft);
        System.out.printf("Ships Sunk: %d\n", this.boatsSunk);
    } // display

    // sets how many ships have been sunk by checking the state of each
    // returns true if a boat was sunk and false otherwise
    public Boat check_sink() {
        // loops through the boat array and checks how many have been sunk
        for (int i = 0; i < this.boats.length; i++) {
            // checks if boat is not already sunk
            if (!this.boats[i].get_sunk()) {
                // sets boat sunk
                this.boats[i].set_sunk();
                // checks to see if boat was sunk after the fire
                if (this.boats[i].get_sunk()) {
                    // updates tracker variables and returns which boat was sunk
                    this.boatsSunk++;
                    this.boatsLeft--;
                    Square[] boat_loc = this.boats[i].get_loc();
                    for (int j = 0; j < boat_loc.length; j++) {
                        boat_loc[j].set_state('S');
                    }
                    return this.boats[i];
                }
            }
        }
        return null;
    } // check_sink

    public boolean check_win() {
        // sets the proper ships sunk and left and checks for win
        return (this.boatsLeft == 0);
    } // check_win

    public void AI_init_weight_grid() {
        this.weightGrid = new int[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.weightGrid[i][j] = 1;
            }
        }
    }

    public int[] choose_rand(Square[] coords, int num) {
        Random rand = new Random();
        int rand_int = rand.nextInt(num);
        Square rand_square = coords[rand_int];
        int[] ret_coord = {rand_square.get_row(), rand_square.get_col()};
        return ret_coord;
    }

    /**********************************************************************************
        evaluates the board and gives a score based on what the grid state is like
        0 -> this spot has already been guessed
        1 -> default score for any point where no info is known
        2 -> there is a single X within two squares in any direction
        3 -> there are two consecutive X's in any directions
        4 -> the square is adjacent to two X's along either or both axes
    **********************************************************************************/
    public int[] eval_enemy_grid(Grid enemy) {
        char state, JPlus1, JPlus2, IPlus1, IPlus2; 
        char JMinus1, JMinus2, IMinus1, IMinus2;
        int max = 1;
        int counter = 0;
        Square[][] enemy_grid = enemy.get_grid();
        Square[] temp_coords = new Square[this.rows * this.cols];
        Square[] possible_coords;
        

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                state = enemy.get_grid()[i][j].get_state();
                try {JPlus1 = enemy_grid[i][j+1].get_state(); } catch (Exception e) { JPlus1 = ' '; }
                try {JPlus2 = enemy_grid[i][j+2].get_state(); } catch (Exception e) { JPlus2 = ' '; }
                try {IPlus1 = enemy_grid[i+1][j].get_state(); } catch (Exception e) { IPlus1 = ' '; }
                try {IPlus2 = enemy_grid[i+2][j].get_state(); } catch (Exception e) { IPlus2 = ' '; }
                try {JMinus1 = enemy_grid[i][j-1].get_state(); } catch (Exception e) { JMinus1 = ' '; }
                try {JMinus2 = enemy_grid[i][j-2].get_state(); } catch (Exception e) { JMinus2 = ' '; }
                try {IMinus1 = enemy_grid[i-1][j].get_state(); } catch (Exception e) { IMinus1 = ' '; }
                try {IMinus2 = enemy_grid[i-2][j].get_state(); } catch (Exception e) { IMinus2 = ' '; }

                if (state != 'X' && state != 'S' && state != '-') {
                    if ((i + 1) < this.rows && (i-1) >= 0 && IPlus1 == 'X' && IMinus1 == 'X') {
                        this.weightGrid[i][j] = 4;
                        max = 4;

                    } else if ((j+1) < this.cols && (j-1) >= 0 && JPlus1 == 'X' && JMinus1 == 'X') {
                        this.weightGrid[i][j] = 4;
                        max = 4;

                    } else if ((j+2) < this.cols && JPlus1 == 'X' && JPlus2 == 'X') {
                        this.weightGrid[i][j] = 3;
                        if (max < 3) {
                            max = 3;
                        }

                    } else if ((j-2) >= 0 && JMinus1 == 'X' && JMinus2 == 'X') {
                        this.weightGrid[i][j] = 3;
                        if (max < 3) {
                            max = 3;
                        }
                    } else if ((i+2) < this.rows && IPlus1 == 'X' && IPlus2 == 'X') {
                        this.weightGrid[i][j] = 3;
                        if (max < 3) {
                            max = 3;
                        }

                    } else if ((i-2) >= 0 && IMinus1 == 'X' && IMinus2 == 'X') {
                        this.weightGrid[i][j] = 3;
                        if (max < 3) {
                            max = 3;
                        }

                    } else if (((i-1) >= 0 && IMinus1 == 'X') || ((i+1) < this.rows && IPlus1 == 'X') ||
                        ((j-1) >= 0 && JMinus1 == 'X') || ((j+1) < this.cols && JPlus1 == 'X')) {
                            this.weightGrid[i][j] = 2;
                            if (max < 2) {
                                max = 2;
                            }

                    } else {
                        this.weightGrid[i][j] = 1;
                    }

                } else {
                    this.weightGrid[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (this.weightGrid[i][j] == max) {
                    temp_coords[counter] = enemy_grid[i][j];
                    counter++;
                }
            }
        }

        possible_coords = new Square[counter];
        for (int i = 0; i < counter; i++) {
            possible_coords[i] = temp_coords[i];
        }

        return choose_rand(possible_coords, counter);
    }

    // getters
    public Boat[] get_boats() { return this.boats; }
    public int get_shots() { return this.shots; }
    public int get_turns() { return this.turns; }
    public Square[][] get_grid() { return this.grid; }
    public void inc_turns() { this.turns++; }

    public void display_AI_eval() {
        String returnString = "Evaluations Board:\n";
        for (int i = -1; i < this.rows; i++) {
            for (int j = -1; j < this.cols; j++) {
                if (i == -1 && j == -1) {
                    returnString += "    ";
                } else if (j == -1 && i >= 10) {
                    returnString += i + "  ";
                } else if (j == -1) {
                    returnString += " " + i + "  ";
                } else if (i == -1 && j >= 10) {
                    returnString += j + "  ";
                } else if (i == -1) {
                    returnString += j + "   ";
                } else {
                    returnString += this.weightGrid[i][j] + "   ";
                }
            }
            returnString += "\n";
        }
        System.out.println(returnString);
    }

    // toString to print the board in a readable fashion
    // each row and column is numbered from 0 to 1
    public String toString() {
        String returnString = this.playerName + '\n';
        for (int i = -1; i < this.rows; i++) {
            for (int j = -1; j < this.cols; j++) {
                if (i == -1 && j == -1) {
                    returnString += "    ";
                } else if (j == -1 && i >= 10) {
                    returnString += i + "  ";
                } else if (j == -1) {
                    returnString += " " + i + "  ";
                } else if (i == -1 && j >= 10) {
                    returnString += j + "  ";
                } else if (i == -1) {
                    returnString += j + "   ";
                } else {
                    returnString += this.grid[i][j].get_state() + "   ";
                }
            }
            returnString += "\n";
        }
        return returnString;
    } // toString

} // Grid