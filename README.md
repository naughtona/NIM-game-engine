# NIM game engine in Java
NIM is a mathematical game of strategy that has been around for hundreds of years. 

This program facilitates game play of two variants of the NIM game:
- NIM game 1
- NIM game 2

The program also maintains an up-to-date player registry, including a record of selected statistics of the games they have played.

Player data kept in the player registry is continually saved down to data file `player_data.dat` upon exiting the program, and fully restored on subsequent executions, i.e. using the `exit` command to exit, and then running `java Nimsys` to re-execute.  The data that is stored includes usernames, given and family names, and the number of games played and won.

## NIM game 1

NIM game 1 has the following identifying rules:
- The game starts with and maintains strictly one heap of stone(s), i.e. where stones are positioned and which of them you remove is **unimportant**
- The initial stone count and the maximum number of stones allowed to be removed on a turn (“K”) are each determined prior to the game’s start
- On each turn, players must remove between 1 and K stone(s), unless the current stone count (“CSC”) is less than K, in which case the upper bound removal is CSC
- The player who removes the last stone loses the game
## NIM game 2
NIM game 2 has the following identifying rules:
- The game starts with one heaps of stone(s), however may becomes numerous heaps of stone(s) since position matters, i.e. where stones are positioned and which of them you remove is **important**
- The initial stone count is determined prior to the game’s start
- On each turn, players must remove either 1 stone or 2 adjacent stones, unless the heap has less than 2 stones, in which case players can only remove 1 stone. Note that players can only remove stones from one heap before the next player’s turn
- The player who removes the last stone wins the game
# Usage
The program provides a text based interactive system that reads and executes commands from `stdin` until an `exit` command is issued, which will terminate the program.

There are two states that the program can be in:
- Idle
- Game
## Idle state
In an idle state, the program allows the following commands:  
_Note that square brackets denote that the parameter is optional_
- `addplayer username,family_name,given_name`
	>adds a human player to the player registry
- `addaiplayer username,family_name,given_name`
	>adds an AI player to the player registry
- `removeplayer [username]`
	>removes a user from the player registry OR, if no username is provided, removes all users from the player registry
- `editplayer username,new_family_name,new_given_name`
	>edits a user’s family name OR given name OR both. Note that a user’s username cannot be edited
- `resetstats [username]`
	>resets the current statistics kept on a specific user OR, if no username is provided, all users
- `displayplayer [username]`
	>displays `username,givenname,familyname,n_games_played,n_ games_won` of a specific user OR all users in the player registry
- `rankings [asc|desc]`
	>displays a list of player rankings ordered by users’ win percentages, i.e. `n_games_won` / `n_games_played`. The list is sorted in ascending order if `asc` is provided, in descending order if `desc` is provided, OR in descending order if no argument is provided. The syntax of the output is: `win_percentage,n_games_played,full_name`
- `startgame initialstones,upperbound,username1,username2`
	>initializes a game of NIM game 1 between `username1` and `username2`, where initial stone count = `initialstones` and maximum stone removal = `upperbound`

- `startadvancedgame initialstones,username1,username2`
	>initializes a game of NIM game 2 between `username1` and `username2`, where initial stone count = `initialstones
`
- `exit`
	>exits the program at once

## Game state

In a game state, the program may be in the middle of either NIM game 1 OR NIM game 2.

Below is an example of a NIM game 1 being played out between two human players, Luke Skywalker and Han Solo:
```
$startgame 10,3,lskywalker,hsolo
Initial stone count: 10
Maximum stone removal: 3
Player 1: Luke Skywalker
Player 2: Han Solo

10 stones left: **********
Luke's turn - remove how many?
3

7  stones left: *******
Han's turn - remove how many?
4

Invalid move. You must remove between 1 and 3 stones.

7 stones left: *******
Han's turn - remove how many?
3

4 stones left: ****
Luke's turn - remove how many?
3

1 stones left: *
Han's turn - remove how many?
0

Invalid move. You must remove between 1 and 1 stones.

1 stones left: *
Han's turn - remove how many?
1

Game over
Luke Skywalker wins!

$
```

Below is an example of a NIM game 2 being played out between two players, a human player by the name of Luke Skywalker and an AI player by the name of R2 D2:
```
$startadvancedgame 10,lskywalker,artoo

Initial stone count: 10
Stones display: <1,*> <2,*> <3,*> <4,*> <5,*> <6,*> <7,*> <8,*> <9,*> <10,*>
Player 1: Luke Skywalker
Player 2: R2 D2

10 stones left: <1,*> <2,*> <3,*> <4,*> <5,*> <6,*> <7,*> <8,*> <9,*> <10,*>
Luke's turn - which to remove?
3 2

8 stones left: <1,*> <2,*> <3,x> <4,x> <5,*> <6,*> <7,*> <8,*> <9,*> <10,*>
R2's turn - which to remove?

6 stones left: <1,*> <2,*> <3,x> <4,x> <5,*> <6,x> <7,x> <8,*> <9,*> <10,*>
Luke's turn - which to remove?
1 2

4 stones left: <1,x> <2,x> <3,x> <4,x> <5,*> <6,x> <7,x> <8,*> <9,*> <10,*>
R2's turn - which to remove?

2 stones left: <1,x> <2,x> <3,x> <4,x> <5,*> <6,x> <7,x> <8,x> <9,x> <10,*>
Luke's turn - which to remove?
5 1

1 stones left: <1,x> <2,x> <3,x> <4,x> <5,x> <6,x> <7,x> <8,x> <9,x> <10,*>
R2's turn - which to remove?

Game Over
R2 D2 wins!
$
```
## Getting started
To compile and run the program, type the following in your terminal:
```bash
javac *.java && java Nimsys
```
Then, you should be greeted with this:
```
Welcome to Nim

$
```
From there you can issue any of the above commands to your heart's content.
# License
[MIT](https://choosealicense.com/licenses/mit/)
