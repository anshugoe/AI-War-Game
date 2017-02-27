//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

import java.util.LinkedList;

public class RandomPlayer implements Player {

    private Board board;
    private PieceColor color;

    public RandomPlayer(Board b, PieceColor color) {
        board = b;
        this.color = color;
    }

    public Move decideMove() {
        LinkedList<Move> moves = board.getPossibleMoves(color);
        int randIndex = (int) Math.floor(Math.random() * moves.size());
        return moves.get(randIndex);
    }

    public PieceColor getColor() {
        return color;
    }

    public String toString() {
        String result = "Type: Random player\n";
        result += "Number of moves: \n";
        result += "Nodes examined: \n";
        result += "Average nodes per move: \n";
        result += "Average time to make a move: \n";
        result += "Score: " + board.getScore(color); 
        return result;
    }
}
