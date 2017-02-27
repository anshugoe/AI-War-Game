//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

//Abstract class that defines a player.
//Functionality for calculating time and number of moves is here,
//all other calculations are in subclasses of this class.
public abstract class Player {

    //How long we've spent thinking about moves so far this game (total thinking time)
    protected long thinkingTimeMillis = 0;
    protected int movesMade = 0; //how many moves have been made by this player.
    
    //Decide on next move, assuming it is this player's turn
    //This method contains the actual logic to find a move
    protected abstract Move decideMove();

    //get the color of the player, blue or green
    public abstract PieceColor getColor();

    //get the score of the player
    public abstract int getScore();

    //return how many moves have been made so far
    public int getNumMoves() {
        return movesMade;
    }

    //This function returns the next move found by decideMove, and also
    //records the time taken
    public Move getNextMove() {
        movesMade++;
        Move move;
        long start, end;
        start = System.currentTimeMillis();
        move = decideMove();
        end = System.currentTimeMillis();
        thinkingTimeMillis += (end - start);
        return move;
    }

    //Compute the average time spent thinking per move
    public double getAvgMoveTime() {
        return (double) thinkingTimeMillis / movesMade;
    }
}
