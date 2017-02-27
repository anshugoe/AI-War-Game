Dylan Wulf
CSC380:Artificial Intelligence
Project 2: War Game
February 26, 2017

---Building---
To build this program, simply enter this command:  
javac Driver.java  
The Java compiler will automatically compile all the other files along with the Driver.  

---Running---
Use a command of the following format to run this program:  
java Driver path/to/gameboard.txt player1Type player2Type [multi]
path/to/gameboard.txt is a path to the file that contains the 5x5 gameboard
player1Type and player2Type are the types for players 1 and 2. 
Player types include R, MM, and AB. These stand for Random, Minimax, and Alpha-Beta.
Player 1, the first player entered in the command line, will be the "blue" player
which is the first player to make a move.
The multi argument at the end is optional. If multi is left out, it will run a single game.
If the word multi is included, it will run 50 games with the specified options and average the 
results together.
An example command to run the program would be:
java Driver gameboards/Kalamazoo.txt MM R

---Files---
AlphaBetaPlayer.java: Contains logic for alpha-beta pruning minimax algorithm
Board.java: Class that contains data and logic for a board
Driver.java: Reads the file and starts the game. Main method is in here.
MatchManager.java: Small class to manage the actual playing of the game match.
MinimaxPlayer.java: Contains logic for minimax algorithm
Move.java: Stores information about a single move on the board
MoveType.java: an enum which specifies what type a move is.
PieceColor.java: an enum which describes the 2 possible colors of pieces and players (blue/green)
Player.java: abstract class from which AlphaBetaPlayer, MinimaxPlayer, and RandomPlayer inherit
RandomPlayer.java: Contains logic for randomly picking a move
