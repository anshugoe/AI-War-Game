//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

import java.util.LinkedList;

//Contains logic for player using the minimax algorithm
public class MinimaxPlayer extends Player {

    private Board board;
    private PieceColor color; //this player's color
    private PieceColor opponentColor; //opponent's color
    private int minimaxDepth; //how deep to search
    private int nodesExamined; //how many nodes have been examined so far

    //Constructor takes arguments for the game board, color this player will be,
    //and how deep the minimax algorithm should go
    public MinimaxPlayer(Board board, PieceColor color, int minimaxDepth) {
        this.board = board;
        this.color = color;
        opponentColor = (color == PieceColor.BLUE)? PieceColor.GREEN : PieceColor.BLUE;
        this.minimaxDepth = minimaxDepth;
        nodesExamined = 0;
        movesMade = 0;
    }

    //Decide on the best move to make. This function essentially
    //calculates the first level of the minimax tree; the rest of the calculation
    //is done in the minimax function below.
    protected Move decideMove() {
        LinkedList<Move> moves = board.getPossibleMoves(color);
        nodesExamined += moves.size(); //Each possible move looked at is an examined node
        int currentHigh = Integer.MIN_VALUE; //using this instead of neg inf
        Move highScoreMove = moves.getFirst();

        for (Move m : moves) {
            Board child = board.copy();
            if (child.makeMove(m)) {
                //using false here because this method (decideMove) technically counts as the
                //first level, which maximizes the result. So the next step is to minimize.
                int moveScore = minimax(child, minimaxDepth - 1, false);
                if (moveScore > currentHigh) {
                    currentHigh = moveScore;
                    highScoreMove = m;
                }
            }
        }
        //return the max move
        return highScoreMove;
    }

    //Performs the minimax algorithm on the board for this player
    public int minimax(Board b, int depth, boolean maximizing) {
        if (depth == 0 || b.gameOver())
            return evaluate(b);
        if (maximizing) { //if this is a maximizing iteration, maximize everything
            int bestValue = Integer.MIN_VALUE;
            LinkedList<Board> childBoards = b.getChildBoards(color);
            nodesExamined += childBoards.size();
            for (Board child : childBoards) {
                int v = minimax(child, depth - 1, false); //next iteration will minimize
                bestValue = Math.max(bestValue, v);
            }
            return bestValue;
        }
        else { //if this is not a maximizing iteration, minimize everything
            int bestValue = Integer.MAX_VALUE;
            LinkedList<Board> childBoards = b.getChildBoards(opponentColor);
            nodesExamined += childBoards.size();
            for (Board child : childBoards) {
                int v = minimax(child, depth - 1, true); //next iteration will maximize
                bestValue = Math.min(bestValue, v);
            }
            return bestValue;
        }
    }

    //Return color of this player
    public PieceColor getColor() {
        return color;
    }

    //Returns string containing stats and data about this player
    public String toString() {
        String result = "Type: Minimax Player ";
        result += "(Evaluator: total value of captured squares; depth: " + minimaxDepth + ")\n";
        result += "Number of moves: " + movesMade + "\n";
        result += "Nodes examined: " + nodesExamined + "\n";
        result += "Average nodes per move: " + ((float)nodesExamined / movesMade) + "\n";
        result += "Average time to make a move: " + getAvgMoveTime() + " ms\n";
        result += "Score: " + board.getScore(color);
        return result;
    }

    //return this player's score in the game so far
    public int getScore() {
        return board.getScore(color);
    }

    //This is the evaluate function used by the minimax algorithm.
    //if the board is a win for this player, return max int value
    //if the board is a loss for this player, return min int value
    //if the board is a tie for this player, return 0
    //if not a leaf node, estimate how likely we are to win by returning this player's score,
    //which is a sum of the values of all the squares captured by this player.
    private int evaluate(Board b) {
        if (b.gameOver() && b.getWinner() == color)
            return Integer.MAX_VALUE;
        if (b.gameOver() && b.getWinner() == opponentColor)
            return Integer.MIN_VALUE;
        if (b.gameOver() && b.getWinner() == PieceColor.BLANK)
            return 0;

        return b.getScore(color);
    }

    //Another evaluate function that I tried, which returns this player's score minus
    //the opponent's score. This one seems to work slightly worse that the above 
    //evaluate function.
    private int evaluate2(Board b) {
        if (b.gameOver() && b.getWinner() == color)
            return Integer.MAX_VALUE;
        if (b.gameOver() && b.getWinner() == opponentColor)
            return Integer.MIN_VALUE;
        if (b.gameOver() && b.getWinner() == PieceColor.BLANK)
            return 0;

        return b.getScore(color) - b.getScore(opponentColor);
    }
}
