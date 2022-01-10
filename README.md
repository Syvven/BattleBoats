# BattleShip
Game of Battleship

    -> Written in Java.
    -> Playable in terminal.
    -> Two Player or Single Player against AI.
    -> Board sizes of ixj where 3 <= i,j <= 15.
    -> Two Modes: Classic and Powerup:
        -> Classic is classic battleship.
        -> Powerup includes powerups meant to make the game more interesting.
    -> Powerups:
        -> None implemented yet but there are ideas.
        -> Decoy Ship
        -> Move: move a ship slightly but only if it has been damaged.
        -> MultiShot: allows you to attack multiple times in a turn.
        -> Radar Bomb: see ships in an area.
        -> Possibly Super Moves?
            -> Torpedo: goes down a row/col until it hits a ship or goes out of bounds.
            -> Airstrike: guaranteed ship hit, prioritizes decoy ships.
    -> AI algorithm:
        -> Gives each square on the grid and evaluation based on the state of the board.
        -> At the start of the game, each square will have the same evaluation.
        -> If a hit occurs, each adjacent block is given a higher evaluation.
        -> If another hit occurs in a certain direction, the next square in that direction
           and the next square in the opposite direction that can be acted upon are given a 
           higher evaluation.
        -> The AI will choose to fire upon the highest evaluated square.
        -> If two or more squares have the same evaluation, the AI will choose one at random.
