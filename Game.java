// Game
// Written By Noah Hendrickson

import java.util.Scanner;

/********************************************************************************
    Contains almost all the methods that allow the game to be played.
    Each subsequent method is called upon calling of the start_game() function
    within the object. The game is initialized with how many players are playing
    and the rows and columns of the game board.
*********************************************************************************/
public class Game {
    private int players;
    private int rows;
    private int cols;
    private Scanner s;

    public Game(int players, int rows, int cols, Scanner s) {
        this.players = players;
        this.rows = rows;
        this.cols = cols;
        this.s = s;
    }

    // starts a game based on how many players there will be
    public void start_game() {
        if (this.players == 1) {
            single_player(this.rows, this.cols);
        } else if (this.players == 2) {
            multi_player(this.rows, this.cols);
        }
    }

    // sets up the game for when there is a single player playing against an AI
    private void single_player(int rows, int cols) {
        boolean game_over = false;
        boolean player1Turn = false;
        boolean playerAITurn = true;
        boolean valid = false;
        boolean spot_clear = false;
        boolean hit;
        Boat sink = null;
        int row = 0;
        int col = 0;

        // initializes each player's game grid
        Grid playerAI = new Grid(rows, cols, this.s, "Player AI");
        Grid player1 = new Grid(rows, cols, this.s, "Player 1");
        
        // places boats for the AI and prompts the player to place their own boats
        playerAI.place_boats_AI();
        player1.place_boats_player();

        System.out.println("\nGame Starting!\n");

        // initiates the game loop that loops until the win condition is reached
        while (!game_over) {
            // inverts whose turn it is
            player1Turn = !player1Turn;
            playerAITurn = !playerAITurn;
            
            // checks for whose turn it is and executes accordingly
            if (player1Turn) {

                // displays the enemy's board in order for the player to fire upon
                System.out.println("Player1's Turn:\n");
                playerAI.display();
                System.out.println("\nWhere would you like to fire?");
                System.out.print("Row: ");

                while (!spot_clear) {
                    // prompts the user to enter a row and handles that accordingly
                    valid = false;
                    while (!valid) {
                        try {
                            row = this.s.nextInt();
                            if (row >= this.rows || row < 0) {
                                System.out.println("Row Out of Bounds.");
                                System.out.print("Please enter a valid row: ");
                            } else {
                                valid = true;
                            }
                        } catch (Exception e) {
                            System.out.print("Please enter an integer for starting row: ");
                            s.nextLine();
                        }
                    }

                    // prompts the user to enter a column and handles that accordingly
                    valid = false;
                    System.out.print("Column: ");
                    while (!valid) {
                        try {
                            col = s.nextInt();
                            if (col >= this.cols || col < 0) {
                                System.out.println("\nColumn Out of Bounds.");
                                System.out.print("Please enter a valid column: ");
                            } else {
                                valid = true;
                            }
                        } catch (Exception e) {
                            System.out.print("\nPlease enter an integer for starting column: ");
                            s.nextLine();
                        }
                    }
                    char state = playerAI.get_grid()[row][col].get_state();
                    if (state == 'X' || state == '-') {
                        System.out.println("Repeat Coordinates. Please choose new coordinates.");
                    } else {
                        spot_clear = true;
                    }
                }


                hit = playerAI.fire(row, col);
                if (hit) {
                    sink = playerAI.check_sink();
                    if (sink != null) {
                        System.out.println("Hit!");
                        System.out.println("Enemy " + sink + " sunk!");
                    } else {
                        System.out.println("Hit!");
                    }   
                } else {
                    System.out.println("Miss!");
                }
                
                game_over = playerAI.check_win();
            } else if (playerAITurn) {
                System.out.println("PlayerAI's Turn:\n");
                player1.display();
                game_over = player1.check_win();
            }
        }

    } // single_player

    // sets up the game for when there are two players playing against each other
    private void multi_player(int rows, int cols) {
        Grid player1 = new Grid(rows, cols, this.s, "Player 1");
        Grid player2 = new Grid(rows, cols, this.s, "Player 2");

        // prompts both users to place their boats
        player1.place_boats_player();   
        player2.place_boats_player();
    } // multi_player





    /********************************************************************************************** 
                                        START MAIN
    **********************************************************************************************/




    public static void main(String[] args) {
        // initiates variables needed
        boolean valid = true;
        String mode = "";
        int players = 0;
        int rows = 0;
        int cols = 0;

        Scanner s = new Scanner(System.in);

        /*****************************************************************************
            Asks the user to input either single or multiplayer
            Single player will result in a game of the user against the computer
                -> The users board will be displayed, however the computer's will not
            Multiplayer will result in a game of the user against another user
                -> Both boards will display where the boats are. This will rely on 
                   the trust of the users to not look at each other's boards.
        ******************************************************************************/
        System.out.print("Please enter 1 for Singleplayer or 2 for Multiplayer: ");
        while (valid) {
            try {
                players = s.nextInt();
                if (players == 1) {
                    System.out.println("Singleplayer Selected");
                    valid = false;
                } else if (players == 2) {
                    System.out.println("Multiplayer Selected");
                    valid = false;
                } else {
                    System.out.print("Please input either 1 or 2 players: ");
                    s.nextLine();
                }
            } catch (Exception e) {
                System.out.print("Please input an integer 1 or 2: ");
                s.nextLine();
            }
        }
        System.out.println();

        /******************************************************************
            Asks the user to input the dimensions of the game grid
            The input rows and columns must be between 3 and 15 else the 
            user is asked for input again.
        ******************************************************************/
        valid = true;
        System.out.println("Please input board dimensions. (rows x columns where 3 <= rows, columns <= 15)");
        while(valid) {
            try {
                System.out.print("Rows: ");
                rows = s.nextInt();
                if (rows < 3 || rows > 15) {
                    System.out.println("Please input an integer between 3 and 15 for rows");
                } else {
                    valid = false;
                }
            } catch (Exception e) {
                System.out.println("Please input an integer between 3 and 15 for rows");
                s.nextLine();
            }
        }

        valid = true;
        while (valid) {
            try {
                System.out.print("Columns: ");
                cols = s.nextInt();
                if (cols < 3 || cols > 15) {
                    System.out.println("Please input an integer between 3 and 15 for rows");
                } else {
                    valid = false;
                }
            } catch (Exception e) {
                System.out.println("Please input an integer between 3 and 15 for columns");
                s.nextLine();
            }
        }
        System.out.println();

        /********************************************************************************************
            This block asks the player to input whether they want to play classic or powerup mode
            Classic mode is normal battleship without any powerups and only one action per turn
            Powerup mode makes use of various powerups (that have yet to be implemented) in addition
            to the rules of normal battleship
        ********************************************************************************************/
        valid = true;
        s.nextLine();
        System.out.println("Would you like to play Classic Mode or Powerup Mode?");
        System.out.print("(Input help for more info on powerup mode): ");

        while (valid) {
            mode = s.nextLine();
            if (mode.compareTo("Classic") != 0 && mode.compareTo("Powerup") != 0 && mode.compareTo("help") != 0) {
                System.out.println("Please enter \"Classic\" for classic mode, \"Powerup\" for powerup mode, or \"help\" for more info: ");
                mode = s.nextLine();
            } else if (mode.compareTo("help") == 0) {
                String printString = "\nClassic Mode: Basic Battleship. No power ups can be used. Each player gets one shot per turn.\n" +
                                     "Powerup Mode: Battleship but with powerups. A variety of powerups will be at your disposal with limited uses.\n" +
                                     "Powerups Available:\n" +
                                     "  -> Yet To Be Implemented\n";
                System.out.println(printString);
                System.out.print("Enter \"Classic\" for classic mode or \"Powerup\" for powerup mode: ");
            } else {
                valid = false;
            }
        }

        // reiterates, to the user, game information, creates the game object, and starts the game.
        Game game = new Game(players, rows, cols, s);
        System.out.println("\nGame Starting!");
        System.out.printf("Mode: %s\n", mode);
        System.out.printf("Players: %d\n", players);
        System.out.printf("Board Size: %d x %d\n\n", rows, cols);
        game.start_game();
        s.close();
    } // main
} // Game