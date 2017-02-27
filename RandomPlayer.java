//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

import java.util.LinkedList;

//Random player class
public class RandomPlayer extends Player {

    private Board board;
    private PieceColor color;

    public RandomPlayer(Board b, PieceColor color) {
        board = b;
        this.color = color;
        movesMade = 0;
    }

    //Decides move by just picking a random move out of all the possible moves
    protected Move decideMove() {
        LinkedList<Move> moves = board.getPossibleMoves(color);
        int randIndex = (int) Math.floor(Math.random() * moves.size());
        return moves.get(randIndex);
    }

    public PieceColor getColor() {
        return color;
    }

    public int getScore() {
        return board.getScore(color);
    }

    //String contains data and stats about this player.
    public String toString() {
        String result = "Type: Random player\n";
        result += "Number of moves: " + movesMade + "\n";
        result += "Nodes examined: " + movesMade + "\n";
        result += "Average nodes per move: 1\n";
        result += "Average time to make a move: " + getAvgMoveTime() + " ms\n";
        result += "Score: " + board.getScore(color); 
        return result;
    }
}
