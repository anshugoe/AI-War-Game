//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

import java.util.LinkedList;

public class Board { 

    private int[][] boardVals; //values of the spaces on the board; indexed by [y][x]
    private PieceColor[][] pieces; //colors of pieces currently on board; indexed by [y][x]
    private int blueScore;
    private int greenScore;

    public Board(int[][] boardVals) {
        blueScore = 0;
        greenScore = 0;

        //copy board vals argument into board vals class field
        this.boardVals = new int[boardVals.length][boardVals[0].length];
        for (int i = 0; i < boardVals.length; i++) {
            for (int j = 0; j < boardVals[i].length; j++) {
                this.boardVals[i][j] = boardVals[i][j];
            }
        }

        //make blank pieces array same size as boardVals
        pieces = new PieceColor[boardVals.length][boardVals[0].length];
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                pieces[i][j] = PieceColor.BLANK;
            }
        }
    }

    public Board(int[][] boardVals, PieceColor[][] pieces) {
        blueScore = 0;
        greenScore = 0;

        //copy board vals argument into boardVals class field
        this.boardVals = new int[boardVals.length][boardVals[0].length];
        for (int i = 0; i < boardVals.length; i++) {
            for (int j = 0; j < boardVals[i].length; j++) {
                this.boardVals[i][j] = boardVals[i][j];
            }
        }
        
        //copy pieces vals argument into pieces class field
        this.pieces = new PieceColor[pieces.length][pieces[0].length];
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                this.pieces[i][j] = pieces[i][j];
                if (pieces[i][j] == PieceColor.BLUE)
                    blueScore += boardVals[i][j];
                if (pieces[i][j] == PieceColor.GREEN)
                    greenScore += boardVals[i][j];
            }
        }
    }
    
    public Board copy() {
        return new Board(boardVals, pieces);
    }

    public LinkedList<Move> getPossibleMoves(PieceColor playerColor) {
        LinkedList<Move> moves = new LinkedList<Move>();
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == PieceColor.BLANK)
                    moves.add(new Move(MoveType.C_PARA_DROP, playerColor, j, i));
                if (canDeathBlitz(j, i, playerColor))
                    moves.add(new Move(MoveType.M1_DEATH_BLITZ, playerColor, j, i));
            }
        }
        return moves;
    }

    public void makeMove(Move move) {
        int playerGain = 0;
        int oppLoss = 0;
        PieceColor player = PieceColor.BLUE;
        PieceColor opp = PieceColor.GREEN;
        if (move.getPlayerColor() == PieceColor.GREEN) {
            player = PieceColor.GREEN;
            opp = PieceColor.BLUE;
        }
        int x = move.getX();
        int y = move.getY();
        pieces[y][x] = player;
        playerGain += boardVals[y][x];
        if (move.getMoveType() == MoveType.M1_DEATH_BLITZ) {
            if (y - 1 >= 0 && pieces[y-1][x] == opp) {
                pieces[y-1][x] = player;
                playerGain += boardVals[y-1][x];
                oppLoss += boardVals[y-1][x];
            }
            if (y + 1 < pieces.length && pieces[y+1][x] == opp) {
                pieces[y+1][x] = player;
                playerGain += boardVals[y+1][x];
                oppLoss += boardVals[y+1][x];
            }
            if (x - 1 >= 0 && pieces[y][x-1] == opp) {
                pieces[y][x-1] = player;
                playerGain += boardVals[y][x-1];
                oppLoss += boardVals[y][x-1];
            }
            if (x + 1 < pieces[y].length && pieces[y][x+1] == opp) {
                pieces[y][x+1] = player;
                playerGain += boardVals[y][x+1];
                oppLoss += boardVals[y][x+1];
            }
        }
        if (player == PieceColor.BLUE) {
            blueScore += playerGain;
            greenScore -= oppLoss;
        }
        else {
            greenScore += playerGain;
            blueScore -= oppLoss;
        }
    }

    private boolean canDeathBlitz(int x, int y, PieceColor playerColor) {
        if (pieces[y][x] == PieceColor.BLANK) {
            if (y - 1 >= 0 && pieces[y-1][x] == playerColor)
                return true;
            if (y + 1 < pieces.length && pieces[y+1][x] == playerColor)
                return true;
            if (x - 1 >= 0 && pieces[y][x-1] == playerColor)
                return true;
            if (x + 1 < pieces[y].length && pieces[y][x+1] == playerColor)
                return true;
        }
        return false;
    }

}
