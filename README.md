# BattleBoats (Java)

## Description

This game of BattleBoats functions very similarly to the popular game of BattleShip. 
Multiple Modes are available.

- Single Player
    - You against an AI. Simple, plain ol' gameplay. 
- Multi Player
    - You against another Human! Invite a friend and try not to cheat ;)
- AI vs AI
    - Watch as two AI's battle it out at lightning speed!
- Powerup Mode
    - Like Multi Player, but with powerups!!

BattleBoats includes these modes playable on boards that range from 4x4 to 50x50! (if you have the time)

BattleBoats is playable right in the terminal (for now...)

The AI implemented, although rudimentary, is not one to be trifled with.

The player is able to place 6 different kinds of ships, each with different sizes. How many you get
to place depends on the size of your board. The bigger the board, the more ships!!

## Pre-Requisites

- [Java](https://www.java.com/en/)

- A Terminal

## Running The Game

cd into the proper directory in the terminal using: 

```
cd <pathname>
```

where \<pathname\> is something such as `/home/user/downloads/classfiles`.

### With .class Files

Once you are in the proper directory, type in the terminal:

```
java Game
```

and the game will run.

### With .java Files

Once you are in the proper directory, type in the terminal:

```
javac Game.java
```

followed by:

```
java Game
```

and the game will run.

## Modes

BattleBoats comes with multiple modes:

- Classic Single Player
    - Classic single player pits the player against an AI
    - It is similar to the popular board game BattleShip
    - At the start of the game, the player is prompted to place boats
        - The player can either manually place them or choose to randomly place them automatically
    - The AI randomly places its boats
    - Each turn, the current player specifies a coordiante to fire on
    - If that coordinate has a ship, a hit occurs, otherwise a miss
    - If every square of a ship has been hit, that ship has been sunk
    - The game ends when all of one player's ships have been sunk
- Classic Multi Player
    - All the same rules apply as in single player
    - Only difference is that the other player is another human, not an AI
    - Each player specifies where to put their boats, so be sure not to peak at each other's boats!!
- Powerup Mode
    - Most rules are the same as classic
    - Powerup mode is restricted to multi player and grids larger than 6x6
    - In powerup mode, at certain turn milestones, the player earns powerups
    - Each powerup has a different charge time
    - Each powerup does something unique, the stronger the powerup, the longer the charge time
    - Powerups include:
        - Radar Bomb: Tells the player how many enemy boat squares are in a 3x3 area
        - Scatter Shot: Hits 3 squares in a 5x5 square centered on a specified coordinate
        - Multi Shot: Allows the player to make three shots in one turn
        - Move Ship: Moves a ship 1 or 2 squares in a random direction but only if its been hit!!
        - Decoy Ship: Allows the user to place a 2 square decoy ship
        - Possibly More in the future!
- AI vs AI
    - AI vs AI mode is quite simple
    - Two AI's duel each other in a classic game
    - The player specifies the board size 
    - In the interest of time, AI games go by quite quick

## Bugs / Suggestions

*If you run into any bugs or issues while playing BattleBoats, or want to suggest any features, feel free to comment at [the github repository](https://github.com/Syvven/BattleShip)
or [email me](hend0800@umn.edu) directly.* 

*I hope that you enjoy BattleBoats!!*
