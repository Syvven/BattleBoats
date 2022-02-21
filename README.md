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
        -> How Powerups Work: 
            -> 
        -> None implemented yet but there are ideas.
        -> Decoy Ship
        -> Move: move a ship slightly but only if it has been damaged.
        -> MultiShot: allows you to attack multiple times in a turn.
        -> Radar Bomb: identifies ships in an area but does not reveal them.
        -> Possibly Super Powerups?
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
        -> Evaluations include:
            0 -> this spot has already been guessed
            1 -> default score for any point where no info is known
            2 -> there is a single X within two squares in any direction
            3 -> there are two consecutive X's in any directions
            4 -> the square is adjacent to two X's along either or both axes

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

and the game will run.

## Available Functions

- GET → http://127.0.0.1:7999/library/getCatalog
    - returns the catalog of available authors and their books
    - authors ordered by last name
- GET → http://127.0.0.1:7999/library/getCheckouts
    - returns a list of who has checked out books and which books they have checked out
- POST → http://127.0.0.1:7999/library/checkoutBook
    - allows the user to check out books
    - input should be as such, each element being a string:

>```
>// these comments should not be included in the input
>// each entry should be a string
>// author name is ordered such: "lastname, firstname middleinitial"
>// eg. J.R.R. Tolkien should be entered as "Tolkien, J.R.R."
>// each book must have an author (no empty string)
>// each author must have at least 1 book "no empty list"
>// each person must have at least 1 author and 1 book (no empty dictionary)
>// user does not need to have already checked out a book
>// any book that does not exist will not be checked out
>// each string must be exactly the same as the author/book/person shown in the catalog/checkouts
>// if you wish to know how which books are available, consult /library/getCatalog or /library/getCheckouts
>// if you wish to checkout for two people, you may do so, however, it wouldnt make logical sense
>{
>     <user-name1>: 
>        {
>             <author1>: [<book1>, <book2>, etc...], 
>             <author2>: [<book3>, <book4>, etc...], 
>             etc...
>        },
>     <user-name2>: 
>        { 
>             etc...
>        }
>}
>```

- POST → http://127.0.0.1:7999/library/returnBook
    - allows user to return books they have checked out
    - inputs should be as such, each element being a string:

>```
>// these comments should not be included in the input
>// each entry should be a string
>// author name is ordered such: "lastname, firstname middleinitial"
>// eg. J.R.R. Tolkien should be entered as "Tolkien, J.R.R."
>// each book must have an author (no empty string)
>// each author must have at least 1 book "no empty list"
>// each person must have at least 1 author and 1 book (no empty dictionary)
>// user needs to have already checked out a book
>// any book that does not exist or was not checked out will not be returned
>// each string must be exactly the same as the author/book/person shown in the person's checkouts
>// if you wish to know how which books you checked out, consult /library/getCheckouts
>// if you wish to return for two people, you may do so, however, it wouldnt make logical sense
>{
>     <user-name1>: 
>        {
>             <author1>: [<book1>, <book2>, etc...], 
>             <author2>: [<book3>, <book4>, etc...], 
>             etc...
>        },
>     <user-name2>: 
>        { 
>             etc...
>        }
>}
>```

- POST → http://127.0.0.1:7999/library/donateBooks
    - allows the user to donate, or add, new books and authors to the catalog
    - returns catalog with new books added
    - input should be as follows, each element a string:

>```
>// these comments should not be included in the input
>// each entry should be formatted as a string
>// author name is ordered such: "lastname, firstname middleinitial"
>// eg. J.R.R. Tolkien should be entered as "Tolkien, J.R.R."
>// each book must have an author (no empty string)
>// each author must have at least 1 book "no empty list"
>// you may donate books that are already in the catalog -- cant have enough books
>// be sure to double check spelling of book/author name -- will be inserted in the catalog even with spelling errors
>{
>        <author1>: [<book1>, <book2>, etc...], 
>        <author2>: [<book3>, <book4>, etc...], 
>        etc...
>}
>```

## Bugs / Suggestions

*If you run into any bugs or issues while using the API, or want to suggest any features, feel free to comment at [the github repository](https://github.com/Syvven/school_stuff)
or [email me](hend0800@umn.edu) directly.* 

*I hope that you enjoy this little library simulation!*
