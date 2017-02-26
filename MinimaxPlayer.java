//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

import java.util.LinkedList;

public class MinimaxPlayer implements Player {

    Board board;
    PieceColor color;
    PieceColor opponentColor;
    int minimaxDepth;

    public MinimaxPlayer(Board board, PieceColor color, int minimaxDepth) {
        this.board = board;
        this.color = color;
        opponentColor = (color == PieceColor.BLUE)? PieceColor.GREEN : PieceColor.BLUE;
        this.minimaxDepth = minimaxDepth;
    }

    public Move decideMove() {
        int currentHigh = Integer.MIN_VALUE;
        Move highScoreMove = null;

        LinkedList<Move> moves = board.getPossibleMoves(color);
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
        
        return highScoreMove;
    }

    public int minimax(Board b, int depth, boolean maximizing) {
        if (depth == 0 || b.gameOver())
            return evaluate(b);
        if (maximizing) {
            int bestValue = Integer.MIN_VALUE;
            for (Board child : b.getChildBoards(color)) {
                int v = minimax(child, depth - 1, false);
                bestValue = Math.max(bestValue, v);
            }
            return bestValue;
        }
        else {
            int bestValue = Integer.MAX_VALUE;
            for (Board child : b.getChildBoards(opponentColor)) {
                int v = minimax(child, depth - 1, true);
                bestValue = Math.min(bestValue, v);
            }
            return bestValue;
        }
    }

    public PieceColor getColor() {
        return color;
    }

    private int evaluate(Board b) {
        if (b.gameOver() && b.getWinner() == color)
            return Integer.MAX_VALUE;
        if (b.gameOver() && b.getWinner() == opponentColor)
            return Integer.MIN_VALUE;
        if (b.gameOver() && b.getWinner() == PieceColor.BLANK)
            return 0;

        return b.getScore(color);
    }

    private int evaluate3(Board b) {
        if (b.gameOver() && b.getWinner() == color)
            return Integer.MAX_VALUE;
        if (b.gameOver() && b.getWinner() == opponentColor)
            return Integer.MIN_VALUE;
        if (b.gameOver() && b.getWinner() == PieceColor.BLANK)
            return 0;

        return b.getScore(color) - b.getScore(opponentColor);
    }

    //-------Note: won't work well with game boards that have 0s, but we don't have any of those.
    //Score + total value of all conquerable squares
    private int evaluate2(Board b) {
        if (b.gameOver() && b.getWinner() == color)
            return Integer.MAX_VALUE;
        if (b.gameOver() && b.getWinner() == opponentColor)
            return Integer.MIN_VALUE;
        if (b.gameOver() && b.getWinner() == PieceColor.BLANK)
            return 0;

        int[][] vals = b.getValues();
        PieceColor[][] pieces = b.getPieces();
        int score = b.getScore(color);
        for (int y = 0; y < pieces.length; y++) {
            for (int x = 0; x < pieces[y].length; x++) {
                boolean foundP = false;
                int surroundingOppVal = 0;
                if (pieces[y][x] == PieceColor.BLANK) {
                    if (y - 1 >= 0) {
                        if (pieces[y-1][x] == color)
                            foundP = true;
                        else if (pieces[y-1][x] == opponentColor)
                            surroundingOppVal += vals[y-1][x];
                    }
                    if (y + 1 < pieces.length) {
                        if (pieces[y+1][x] == color)
                            foundP = true;
                        else if (pieces[y+1][x] == opponentColor)
                            surroundingOppVal += vals[y+1][x];
                    }
                    if (x - 1 >= 0) {
                        if (pieces[y][x-1] == color)
                            foundP = true;
                        else if (pieces[y][x-1] == opponentColor)
                            surroundingOppVal += vals[y][x-1];
                    }
                    if (x + 1 < pieces[y].length) {
                        if (pieces[y][x+1] == color)
                            foundP = true;
                        else if (pieces[y][x+1] == opponentColor)
                            surroundingOppVal += vals[y][x+1];
                    }
                }
                if (foundP && surroundingOppVal > 0) score += surroundingOppVal;
            }
        }
        return score;
    }
}
