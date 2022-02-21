// Game
// Written By Noah Hendrickson

import java.util.Scanner;
import java.util.ArrayList;

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
    private boolean powerup;

    public Game(int players, int rows, int cols, Scanner s, boolean powerup) {
        this.players = players;
        this.rows = rows;
        this.cols = cols;
        this.s = s;
        this.powerup = powerup;
    }

    // starts a game based on how many players there will be
    public void start_game() {
        boolean valid = false;
        String first = "";

        // determines how many players and then calls the proper method
        if (this.players == 1) {
            single_player(this.rows, this.cols);
        } else if (this.players == 2) {

            // prompts the user to input who plays first
            System.out.print("Who would you like to go first? (player1/player2): ");
            while (!valid) {
                first = this.s.nextLine();
                if (first.compareTo("player1") == 0 || first.compareTo("player2") == 0) {
                    if (this.powerup) {
                        powerup_game(this.rows, this.cols, first);
                    } else {
                        multi_player(this.rows, this.cols, first);
                    }
                    
                    valid = true;
                    this.s.nextLine();
                } else {
                    // incorrect input handling
                    System.out.print("\nPlease enter player1 or player2 for who you want to go first: ");
                }
            }
        } else if (this.players == 0) {
            AI_vs_AI(this.rows, this.cols);
        }
    } // start_game

    // sets up the game for when there is a single player playing against an AI
    private void single_player(int rows, int cols) {
        // initializes variables used
        boolean game_over = false;
        boolean player1Turn = false;
        boolean playerAITurn = true;
        boolean valid = false;
        boolean spot_clear = false;
        boolean hit;
        boolean player1Win = false;
        Boat sink = null;
        int row = 0;
        int col = 0;

        // initializes each player's game grid
        Grid playerAI = new Grid(rows, cols, this.s, "Player AI", "AI");
        Grid player1 = new Grid(rows, cols, this.s, "Player 1", "Human");
        
        // places boats for the AI and prompts the player to place their own boats
        playerAI.place_boats_AI();
        player1.place_boats_player();

        System.out.println("\nGame Starting!\n");

        // initiates the game loop that loops until the win condition is reached
        while (!game_over) {
            // inverts whose turn it is
            player1Turn = !player1Turn;
            playerAITurn = !playerAITurn;

            // pauses for a second for a more natural flow
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // checks for whose turn it is and executes accordingly
            if (player1Turn) {
                
                // displays the enemy's board in order for the player to fire upon
                System.out.println("\nPlayer1's Turn:\n");
                playerAI.display();
                System.out.println("\nWhere would you like to fire?");

                spot_clear = false;
                while (!spot_clear) {
                    // prompts the user to enter a row and handles that accordingly
                    System.out.print("Row: ");
                    valid = false;
                    while (!valid) {
                        try {
                            row = this.s.nextInt();
                            if (row >= this.rows || row < 0) {
                                // checks for out of bounds
                                System.out.println("Row Out of Bounds.");
                                System.out.print("Please enter a valid row: ");
                            } else {
                                valid = true;
                            }
                        } catch (Exception e) {
                            // catches non-integer inputs
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
                                // checks for out of bounds
                                System.out.println("\nColumn Out of Bounds.");
                                System.out.print("Please enter a valid column: ");
                            } else {
                                valid = true;
                            }
                        } catch (Exception e) {
                            // catches non-integer inputs
                            System.out.print("\nPlease enter an integer for starting column: ");
                            s.nextLine();
                        }
                    }

                    // checks for if the spot had already been guessed and breaks if it hasnt
                    char state = playerAI.get_grid()[row][col].get_state();
                    if (state == 'X' || state == '-') {
                        System.out.println("Repeat Coordinates. Please choose new coordinates.");
                    } else {
                        spot_clear = true;
                    }
                }

                // initiates the fire if the spot is valid
                hit = playerAI.fire(row, col);
                // checks if the shot hit
                if (hit) { 
                    // checks if a boat was sunk with the shot
                    sink = playerAI.check_sink();
                    if (sink != null) {
                        System.out.println("\nHit!");
                        System.out.println("Enemy " + sink + " Sunk!\n");
                    } else {
                        System.out.println("\nHit!\n");
                    }   
                } else {
                    System.out.println("\nMiss!\n");
                }
                
                // last thing the loop does is check for win and if it wins the loops terminates
                game_over = playerAI.check_win();

                // if player1 wins, sets the win check to true
                if (game_over) {
                    player1Win = true;
                }
                playerAI.inc_turns();
            } else if (playerAITurn) {
                
                System.out.println("PlayerAI's Turn:\n");
                player1.display();

                System.out.println("\nAI is Choosing a Coordinate to Fire on...");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int[] coords = playerAI.eval_enemy_grid(player1);
                playerAI.display_AI_eval();
                System.out.printf("AI Fired On Square at (%d, %d)!", coords[0], coords[1]);
                
                // initiates the fire if the spot is valid
                hit = player1.fire(coords[0], coords[1]);
                // checks if the shot hit
                if (hit) { 
                    // checks if a boat was sunk with the shot
                    sink = player1.check_sink();
                    if (sink != null) {
                        System.out.println("\nHit!");
                        System.out.println("Player 1's " + sink + " Sunk!\n");
                    } else {
                        System.out.println("\nHit!\n");
                    }   
                } else {
                    System.out.println("\nMiss!\n");
                }

                game_over = player1.check_win();

                if (game_over) {
                    player1Win = false;
                }
                player1.inc_turns();
            }
        }

        System.out.println(player1);
        System.out.println(playerAI);

        // checks who won and prints accordingly
        if (player1Win) {
            System.out.println("Player 1 Wins! Thanks for playing!");
        } else {
            System.out.println("AI Wins! Better luck next time!");
        }
    } // single_player

    // sets up the game for when there are two players playing against each other
    private void multi_player(int rows, int cols, String first) {
        // initializes variables used
        boolean game_over = false;
        boolean player1Turn;
        boolean player2Turn;
        boolean valid = false;
        boolean spot_clear = false;
        boolean hit;
        boolean player1Win = false;
        Boat sink = null;
        int row = 0;
        int col = 0;

        // sets who goes first based on what the players selected
        if (first.compareTo("player1") == 0) {
            player1Turn = false;
            player2Turn = true;
        } else {
            player2Turn = false;
            player1Turn = true;
        }

        // initializes each player's game grid
        Grid player2 = new Grid(rows, cols, this.s, "Player 2", "Human");
        Grid player1 = new Grid(rows, cols, this.s, "Player 1", "Human");
        
        // places boats for the AI and prompts the player to place their own boats
        player2.place_boats_player();
        player1.place_boats_player();

        System.out.println("\nGame Starting!\n");

        // initiates the game loop that loops until the win condition is reached
        while (!game_over) {
            // pauses for a second for a more natural flow
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // inverts whose turn it is
            player1Turn = !player1Turn;
            player2Turn = !player2Turn;

            
            // checks for whose turn it is and executes accordingly
            if (player1Turn) {
                
                // displays the enemy's board in order for the player to fire upon
                System.out.println("Player1's Turn:\n");
                player2.display();
                System.out.println("\nWhere would you like to fire?");

                spot_clear = false;
                while (!spot_clear) {
                    // prompts the user to enter a row and handles that accordingly
                    System.out.print("Row: ");
                    valid = false;
                    while (!valid) {
                        try {
                            row = this.s.nextInt();
                            if (row >= this.rows || row < 0) {
                                // checks for out of bounds
                                System.out.println("Row Out of Bounds.");
                                System.out.print("Please enter a valid row: ");
                            } else {
                                valid = true;
                            }
                        } catch (Exception e) {
                            // catches non-integer inputs
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
                                // checks for out of bounds
                                System.out.println("\nColumn Out of Bounds.");
                                System.out.print("Please enter a valid column: ");
                            } else {
                                valid = true;
                            }
                        } catch (Exception e) {
                            // catches non-integer inputs
                            System.out.print("\nPlease enter an integer for starting column: ");
                            s.nextLine();
                        }
                    }

                    // checks for if the spot had already been guessed and breaks if it hasnt
                    char state = player2.get_grid()[row][col].get_state();
                    if (state == 'X' || state == '-') {
                        System.out.println("Repeat Coordinates. Please choose new coordinates.");
                    } else {
                        spot_clear = true;
                    }
                }

                // initiates the fire if the spot is valid
                hit = player2.fire(row, col);
                // checks if the shot hit
                if (hit) { 
                    // checks if a boat was sunk with the shot
                    sink = player2.check_sink();
                    if (sink != null) {
                        System.out.println("\nHit!");
                        System.out.println("Enemy " + sink + " Sunk!\n");
                    } else {
                        System.out.println("\nHit!\n");
                    }   
                } else {
                    System.out.println("\nMiss!\n");
                    
                }

                // last thing the loop does is check for win and if it wins the loops terminates
                game_over = player2.check_win();

                // if player1 wins, sets the win check to true
                if (game_over) {
                    player1Win = true;
                }
                player2.inc_turns();
            } else if (player2Turn) {
                
                // displays the enemy's board in order for the player to fire upon
                System.out.println("Player2's Turn:\n");
                player1.display();
                System.out.println("\nWhere would you like to fire?");

                spot_clear = false;
                while (!spot_clear) {
                    // prompts the user to enter a row and handles that accordingly
                    System.out.print("Row: ");
                    valid = false;
                    while (!valid) {
                        try {
                            row = this.s.nextInt();
                            if (row >= this.rows || row < 0) {
                                // checks for out of bounds
                                System.out.println("Row Out of Bounds.");
                                System.out.print("Please enter a valid row: ");
                            } else {
                                valid = true;
                            }
                        } catch (Exception e) {
                            // catches non-integer inputs
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
                                // checks for out of bounds
                                System.out.println("\nColumn Out of Bounds.");
                                System.out.print("Please enter a valid column: ");
                            } else {
                                valid = true;
                            }
                        } catch (Exception e) {
                            // catches non-integer inputs
                            System.out.print("\nPlease enter an integer for starting column: ");
                            s.nextLine();
                        }
                    }

                    // checks for if the spot had already been guessed and breaks if it hasnt
                    char state = player1.get_grid()[row][col].get_state();
                    if (state == 'X' || state == '-') {
                        System.out.println("Repeat Coordinates. Please choose new coordinates.");
                    } else {
                        spot_clear = true;
                    }
                }

                // initiates the fire if the spot is valid
                hit = player1.fire(row, col);
                // checks if the shot hit
                if (hit) { 
                    // checks if a boat was sunk with the shot
                    sink = player1.check_sink();
                    if (sink != null) {
                        System.out.println("\nHit!");
                        System.out.println("Enemy " + sink + " Sunk!\n");
                    } else {
                        System.out.println("\nHit!\n");
                    }   
                } else {
                    System.out.println("\nMiss!\n");
                }
                
                // last thing the loop does is check for win and if it wins the loops terminates
                game_over = player1.check_win();

                // if player2 wins, sets the win check to false
                if (game_over) {
                    player1Win = false;
                }
                player1.inc_turns();
            }
        }

        System.out.println(player1);
        System.out.println(player2);
        
        // checks who won and prints accordingly
        if (player1Win) {
            System.out.println("Player 1 Wins! Thanks for playing!");
        } else {
            System.out.println("Player 2 Wins! Thanks for playing!");
        }

        // pauses for a second for a more natural flow
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // multi_player

    // sets up the game for when there are two players playing against each other in powerup mode
    // NOT DONE
    private void powerup_game(int rows, int cols, String first) {
        boolean game_over = false;
        boolean player1Turn;
        boolean player2Turn;
        boolean valid = false;
        boolean extra_valid = false;
        boolean action_present = false;
        boolean spot_clear = false;
        boolean hit;
        boolean player1Win = false;
        boolean use_powerup = false;
        boolean powerup_used = false;
        String action;
        Boat sink = null;
        int row = 0;
        int col = 0;
        ArrayList<String> powerups;

        Grid player2 = new Grid(rows, cols, this.s, "Player 2", "Human");
        Grid player1 = new Grid(rows, cols, this.s, "Player 1", "Human");


        if (first.compareTo("player1") == 0) {
            player1Turn = false;
            player2.inc_turns();
            player2Turn = true;
        } else {
            player2Turn = false;
            player1Turn = true;
            player1.inc_turns();
        }
        
        // places boats for the AI and prompts the player to place their own boats
        player2.place_boats_player();
        player1.place_boats_player();

        System.out.println("\nGame Starting!\n");

        while (!game_over) {
            // pauses for a second for a more natural flow
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // inverts whose turn it is
            player1Turn = !player1Turn;
            player2Turn = !player2Turn;

            
            // checks for whose turn it is and executes accordingly
            if (player1Turn) {
                // displays the enemy's board in order for the player to fire upon
                System.out.println("Player1's Turn:\n");
                player2.display();
                powerups = player1.get_available_powerups();
                
                System.out.println("Available Powerups: " + powerups.toString());

                if (powerups.size() > 0) {
                    // prompts the user to input an action
                    System.out.print("Would you like to use a Powerup or Fire normally (input Powerup/Fire): ");
                    valid = false;
                    while (!valid) {
                        action = this.s.nextLine();
                        if (action.compareTo("Fire") == 0) {
                            valid = true;
                            use_powerup = false;
                        } else if (action.compareTo("Powerup") == 0) {
                            valid = true;
                            use_powerup = true;
                        } else {
                            // incorrect input handling
                            System.out.print("\nPlease enter either \"Fire\" or \"Powerup\": ");
                        }
                    }
                } else {
                    use_powerup = false;
                }
                
            
                if (use_powerup) {
                    powerup_used = false;
                    while (!powerup_used) {
                        System.out.println("\nWhich powerup would you like to use?\nAvailable powerups are shown below the printed board.");
                        System.out.print("If you would like to cancel powerup use, enter \"cancel\": ");

                        valid = false;
                        while (!valid) {
                            action = this.s.nextLine();
                            for (String pw : powerups) {
                                if (action.compareTo(pw) == 0) {
                                    action_present = true;
                                    boolean succ = player1.use_powerup(pw, player2);
                                    if (succ == false) {
                                        System.out.println("Powerup use unsuccessful.\n");
                                        System.out.print("Enter \"cancel\" to fire normally, or \"redo\" to use a different powerup: ");
                                        extra_valid = false;
                                        while (!extra_valid) {
                                            action = this.s.nextLine();
                                            if (action.compareTo("redo") == 0) {
                                                extra_valid = true;
                                                System.out.print("Enter powerup you would like to use: ");
                                            } else if (action.compareTo("cancel") == 0) {
                                                use_powerup = false;
                                                powerup_used = true;
                                                extra_valid = true;
                                                valid = true;
                                            } else {
                                                // incorrect input handling
                                                System.out.print("\nPlease enter either \"redo\" or \"cancel\": ");
                                            }
                                        }
                                    } else {
                                        System.out.println("Powerup successfully used.\n");
                                        powerup_used = true;
                                        valid = true;
                                    }
                                    break;
                                }
                            }
                            if (action.compareTo("cancel") == 0) {
                                powerup_used = true;
                                use_powerup = false;
                                valid = true;
                            } else {    
                                if (!action_present) {
                                    System.out.print("Please input an available powerup: ");
                                }
                            }   
                        }
                    } 
                }

                if (!use_powerup) {
                    System.out.println("\nWhere would you like to fire?");

                    spot_clear = false;
                    while (!spot_clear) {
                        // prompts the user to enter a row and handles that accordingly
                        System.out.print("Row: ");
                        valid = false;
                        while (!valid) {
                            try {
                                row = this.s.nextInt();
                                if (row >= this.rows || row < 0) {
                                    // checks for out of bounds
                                    System.out.println("Row Out of Bounds.");
                                    System.out.print("Please enter a valid row: ");
                                } else {
                                    valid = true;
                                }
                            } catch (Exception e) {
                                // catches non-integer inputs
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
                                    // checks for out of bounds
                                    System.out.println("\nColumn Out of Bounds.");
                                    System.out.print("Please enter a valid column: ");
                                } else {
                                    valid = true;
                                }
                            } catch (Exception e) {
                                // catches non-integer inputs
                                System.out.print("\nPlease enter an integer for starting column: ");
                                s.nextLine();
                            }
                        }

                        // checks for if the spot had already been guessed and breaks if it hasnt
                        char state = player2.get_grid()[row][col].get_state();
                        if (state == 'X' || state == '-') {
                            System.out.println("Repeat Coordinates. Please choose new coordinates.");
                        } else {
                            spot_clear = true;
                        }
                    }

                    // initiates the fire if the spot is valid
                    hit = player2.fire(row, col);
                    // checks if the shot hit
                    if (hit) { 
                        // checks if a boat was sunk with the shot
                        sink = player2.check_sink();
                        if (sink != null) {
                            System.out.println("\nHit!");
                            System.out.println("Enemy " + sink + " Sunk!\n");
                        } else {
                            System.out.println("\nHit!\n");
                        }   
                    } else {
                        System.out.println("\nMiss!\n");
                        
                    }
                }
                

                // last thing the loop does is check for win and if it wins the loops terminates
                game_over = player2.check_win();

                // if player1 wins, sets the win check to true
                if (game_over) {
                    player1Win = true;
                }
                player1.inc_turns();
            } else if (player2Turn) {
                
                // displays the enemy's board in order for the player to fire upon
                System.out.println("Player2's Turn:\n");
                player1.display();
                powerups = player2.get_available_powerups();
                
                System.out.println("Available Powerups: " + powerups.toString());

                if (powerups.size() > 0) {
                    // prompts the user to input an action
                    System.out.print("Would you like to use a Powerup or Fire normally (input Powerup/Fire): ");
                    valid = false;
                    while (!valid) {
                        action = this.s.nextLine();
                        if (action.compareTo("Fire") == 0) {
                            valid = true;
                            use_powerup = false;
                        } else if (action.compareTo("Powerup") == 0) {
                            valid = true;
                            use_powerup = true;
                        } else {
                            // incorrect input handling
                            System.out.print("\nPlease enter either \"Fire\" or \"Powerup\": ");
                        }
                    }
                } else {
                    use_powerup = false;
                }

                
                if (use_powerup) {
                    powerup_used = false;
                    while (!powerup_used) {
                        System.out.println("\nWhich powerup would you like to use?\nAvailable powerups are shown below the printed board.");
                        System.out.print("If you would like to cancel powerup use, enter \"cancel\": ");

                        valid = false;
                        while (!valid) {
                            action = this.s.nextLine();
                            for (String pw : powerups) {
                                if (action.compareTo(pw) == 0) {
                                    action_present = true;
                                    boolean succ = player2.use_powerup(pw, player1);
                                    if (succ == false) {
                                        System.out.println("Powerup use unsuccessful.\n");
                                        System.out.print("Enter \"cancel\" to fire normally, or \"redo\" to use a different powerup: ");
                                        extra_valid = false;
                                        while (!extra_valid) {
                                            action = this.s.nextLine();
                                            if (action.compareTo("redo") == 0) {
                                                extra_valid = true;
                                                System.out.print("Enter powerup you would like to use: ");
                                            } else if (action.compareTo("cancel") == 0) {
                                                use_powerup = false;
                                                powerup_used = true;
                                                extra_valid = true;
                                                valid = true;
                                            } else {
                                                // incorrect input handling
                                                System.out.print("\nPlease enter either \"redo\" or \"cancel\": ");
                                            }
                                        }
                                    } else {
                                        System.out.println("Powerup successfully used.\n");
                                        powerup_used = true;
                                        valid = true;
                                    }
                                    break;
                                }
                            }
                            if (action.compareTo("cancel") == 0) {
                                powerup_used = true;
                                use_powerup = false;
                                valid = true;
                            } else {    
                                if (!action_present) {
                                    System.out.println("Please input an available powerup.");
                                }
                            }   
                        }
                    } 
                } 
                if (!use_powerup) {
                    System.out.println("\nWhere would you like to fire?");

                    spot_clear = false;
                    while (!spot_clear) {
                        // prompts the user to enter a row and handles that accordingly
                        System.out.print("Row: ");
                        valid = false;
                        while (!valid) {
                            try {
                                row = this.s.nextInt();
                                if (row >= this.rows || row < 0) {
                                    // checks for out of bounds
                                    System.out.println("Row Out of Bounds.");
                                    System.out.print("Please enter a valid row: ");
                                } else {
                                    valid = true;
                                }
                            } catch (Exception e) {
                                // catches non-integer inputs
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
                                    // checks for out of bounds
                                    System.out.println("\nColumn Out of Bounds.");
                                    System.out.print("Please enter a valid column: ");
                                } else {
                                    valid = true;
                                }
                            } catch (Exception e) {
                                // catches non-integer inputs
                                System.out.print("\nPlease enter an integer for starting column: ");
                                s.nextLine();
                            }
                        }

                        // checks for if the spot had already been guessed and breaks if it hasnt
                        char state = player1.get_grid()[row][col].get_state();
                        if (state == 'X' || state == '-') {
                            System.out.println("Repeat Coordinates. Please choose new coordinates.");
                        } else {
                            spot_clear = true;
                        }
                    }

                    // initiates the fire if the spot is valid
                    hit = player1.fire(row, col);
                    // checks if the shot hit
                    if (hit) { 
                        // checks if a boat was sunk with the shot
                        sink = player1.check_sink();
                        if (sink != null) {
                            System.out.println("\nHit!");
                            System.out.println("Enemy " + sink + " Sunk!\n");
                        } else {
                            System.out.println("\nHit!\n");
                        }   
                    } else {
                        System.out.println("\nMiss!\n");
                    }
                }    
                // last thing the loop does is check for win and if it wins the loops terminates
                game_over = player1.check_win();

                // if player2 wins, sets the win check to false
                if (game_over) {
                    player1Win = false;
                }
                
                player2.inc_turns();
            }
        }

        System.out.println(player1);
        System.out.println(player2);
        
        // checks who won and prints accordingly
        if (player1Win) {
            System.out.println("Player 1 Wins! Thanks for playing!");
        } else {
            System.out.println("Player 2 Wins! Thanks for playing!");
        }

        // pauses for two seconds for a more natural flow
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // sets up the game for when there are two AI's playing against each other
    private void AI_vs_AI(int rows, int cols) {
        // initializes variables used
        boolean game_over = false;
        boolean playerAI1Turn = false;
        boolean playerAI2Turn = true;
        boolean hit;
        boolean playerAI1Win = false;
        Boat sink = null;

        // initializes each player's game grid
        Grid playerAI1 = new Grid(rows, cols, this.s, "Player AI1", "AI");
        Grid playerAI2 = new Grid(rows, cols, this.s, "Player AI2", "AI");
        
        // places boats for the AI and prompts the player to place their own boats
        playerAI1.place_boats_AI();
        playerAI2.place_boats_AI();

        System.out.println("\nGame Starting!\n");

        // initiates the game loop that loops until the win condition is reached
        while (!game_over) {
            // inverts whose turn it is
            playerAI2Turn = !playerAI2Turn;
            playerAI1Turn = !playerAI1Turn;

            // pauses for a second for a more natural flow
            // try {
            //     Thread.sleep(1000);
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }
            
            // checks for whose turn it is and executes accordingly
            if (playerAI1Turn) {
                
                System.out.println("PlayerAI1's Turn:\n");
                playerAI2.display();

                System.out.println("\nAI1 is Choosing a Coordinate to Fire on...");
                // try {
                //     Thread.sleep(500);
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

                int[] coords = playerAI1.eval_enemy_grid(playerAI2);
                playerAI1.display_AI_eval();
                System.out.printf("AI1 Fired On Square at (%d, %d)!", coords[0], coords[1]);
                
                // initiates the fire if the spot is valid
                hit = playerAI2.fire(coords[0], coords[1]);
                // checks if the shot hit
                if (hit) { 
                    // checks if a boat was sunk with the shot
                    sink = playerAI2.check_sink();
                    if (sink != null) {
                        System.out.println("\nHit!");
                        System.out.println("AI2's " + sink + " Sunk!\n");
                    } else {
                        System.out.println("\nHit!\n");
                    }   
                } else {
                    System.out.println("\nMiss!\n");
                }

                game_over = playerAI2.check_win();

                if (game_over) {
                    playerAI1Win = true;
                }
                playerAI2.inc_turns();
            } else if (playerAI2Turn) {
                
                System.out.println("PlayerAI2's Turn:\n");
                playerAI1.display();

                System.out.println("\nAI2 is Choosing a Coordinate to Fire on...");
                // try {
                //     Thread.sleep(500);
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

                int[] coords = playerAI2.eval_enemy_grid(playerAI1);
                playerAI2.display_AI_eval();
                System.out.printf("AI2 Fired On Square at (%d, %d)!", coords[0], coords[1]);
                
                // initiates the fire if the spot is valid
                hit = playerAI1.fire(coords[0], coords[1]);
                // checks if the shot hit
                if (hit) { 
                    // checks if a boat was sunk with the shot
                    sink = playerAI1.check_sink();
                    if (sink != null) {
                        System.out.println("\nHit!");
                        System.out.println("AI1's " + sink + " Sunk!\n");
                    } else {
                        System.out.println("\nHit!\n");
                    }   
                } else {
                    System.out.println("\nMiss!\n");
                }

                game_over = playerAI1.check_win();

                if (game_over) {
                    playerAI1Win = false;
                }
                playerAI1.inc_turns();
            }
        }

        System.out.println(playerAI1);
        System.out.println(playerAI2);

        // checks who won and prints accordingly
        if (playerAI1Win) {
            System.out.println("AI1 Wins!");
        } else {
            System.out.println("AI2 Wins!");
        }
    }



    /********************************************************************************************** 
                                        START MAIN
    **********************************************************************************************/



    public static void main(String[] args) {
        // initiates variables needed
        boolean powerup = false;
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
        System.out.print("Please enter 1 for Singleplayer or 2 for Multiplayer or 0 for AI vs AI: ");
        while (valid) {
            try {
                players = s.nextInt();
                if (players == 0) {
                    System.out.println("AI vs AI selected");
                    valid = false;
                } else if (players == 1) {
                    System.out.println("Singleplayer Selected");
                    valid = false;
                } else if (players == 2) {
                    System.out.println("Multiplayer Selected");
                    valid = false;
                } else {
                    System.out.print("Please input either 0, 1, or 2 players: ");
                    s.nextLine();
                }
            } catch (Exception e) {
                System.out.print("Please input an integer0, 1, or 2: ");
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
        System.out.println("Please input board dimensions. (rows x columns where 4 <= rows, columns <= 50)");
        System.out.println("Powerup games can only be played on boards 6x6 or larger.");
        while(valid) {
            try {
                System.out.print("Rows: ");
                rows = s.nextInt();
                if (rows < 4 || rows > 50) {
                    System.out.println("Please input an integer between 4 and 50 for rows");
                } else {
                    valid = false;
                }
            } catch (Exception e) {
                System.out.println("Please input an integer between 4 and 50 for rows");
                s.nextLine();
            }
        }

        valid = true;
        while (valid) {
            try {
                System.out.print("Columns: ");
                cols = s.nextInt();
                if (cols < 4 || cols > 50) {
                    System.out.println("Please input an integer between 4 and 50 for rows");
                } else {
                    valid = false;
                }
            } catch (Exception e) {
                System.out.println("Please input an integer between 4 and 50 for columns");
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
        if (players != 0) {
            if (rows >= 6 && cols >= 6) {
                valid = true;
                s.nextLine();
                System.out.println("Would you like to play Classic Mode or Powerup Mode?");
                System.out.print("Input \"Classic\" for Classic Mode, \"Powerup\" for Powerup Mode, or \"help\" for more info on powerup mode: ");
    
                while (valid) {
                    mode = s.nextLine();
                    if (mode.compareTo("Classic") != 0 && mode.compareTo("Powerup") != 0 && mode.compareTo("help") != 0) {
                        System.out.println("Please enter \"Classic\" for classic mode, \"Powerup\" for powerup mode, or \"help\" for more info: ");
                    } else if (mode.compareTo("help") == 0) {
                        String printString = "\nClassic Mode: Basic Battleship. No power ups can be used. Each player gets one shot per turn.\n" +
                                             "Powerup Mode: Battleship but with powerups. A variety of powerups will be at your disposal with limited uses.\n" +
                                             "Powerups Available:\n" +
                                             "  -> Yet To Be Implemented\n";
                        System.out.println(printString);
                        System.out.print("Enter \"Classic\" for classic mode or \"Powerup\" for powerup mode: ");
                    } else if (mode.compareTo("Powerup") == 0) {
                        powerup = true;
                        valid = false;
                    } else {
                        valid = false;
                    }
                }
            } else {
                mode = "Classic";
                String printString = "Your board size requires that the game be played in Classic mode." +
                                     "\nClassic Mode: Basic Battleship. No power ups can be used. Each player gets one shot per turn.\n";
                System.out.println(printString);
            }
        } else {
            mode = "Classic";
            String printString = "AI vs AI requires that the game be played in Classic mode." +
                                 "\nClassic Mode: Basic Battleship. No power ups can be used. Each player gets one shot per turn.\n";
            System.out.println(printString);
        }
        

        // reiterates, to the user, game information, creates the game object, and starts the game.
        Game game = new Game(players, rows, cols, s, powerup);

        System.out.println("\nGame Starting!");
        System.out.printf("Mode: %s\n", mode);
        System.out.printf("Players: %d\n", players);
        System.out.printf("Board Size: %d x %d\n\n", rows, cols);
        game.start_game();
        s.close();
    } // main
} // Game