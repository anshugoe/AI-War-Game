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
    private int emptySpaces;

    //Constructor which allows values to be set
    //Sets all pieces to BLANK for a new game
    public Board(int[][] boardVals) {
        blueScore = 0;
        greenScore = 0;
        emptySpaces = 0;

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
                emptySpaces++;
            }
        }
    }

    //Constructor which allows both values and current piece locations
    //to be assigned
    public Board(int[][] boardVals, PieceColor[][] pieces) {
        blueScore = 0;
        greenScore = 0;
        emptySpaces = 0;

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
                else if (pieces[i][j] == PieceColor.GREEN)
                    greenScore += boardVals[i][j];
                else
                    emptySpaces++;
            }
        }
    }
    
    //Creates a copy of this board.
    //Changing the values in the copied board will not affect
    //the objects in this board.
    public Board copy() {
        return new Board(boardVals, pieces);
    }

    //Gets a list of moves that can possibly be made on this board
    //by the player of the specified color.
    //if this returns an empty list, then the game has already ended.
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

    //Takes the moves returned from the getPossibleMoves function
    //And creates a separate child board for each one.
    //returns all these child boards in a linked list.
    public LinkedList<Board> getChildBoards(PieceColor playerColor) {
        LinkedList<Move> moves = getPossibleMoves(playerColor);
        LinkedList<Board> children = new LinkedList<Board>();
        for (Move m : moves) {
            Board child = this.copy();
            boolean moveSuccess = child.makeMove(m);
            if (moveSuccess)
                children.add(child);
            else
                child = null;
        }
        return children;
    }

    //Performs the actual action of making the specified move on this board.
    //This board will then be updated to represent the new piece locations
    //and the player scores will be updated accordingly.
    //returns true if the move was valid and successful,
    //false if the move was invalid
    public boolean makeMove(Move move) {
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

        //Check for invalid moves
        if (move.getMoveType() == MoveType.C_PARA_DROP && pieces[y][x] != PieceColor.BLANK)
            return false;
        if (move.getMoveType() == MoveType.M1_DEATH_BLITZ && !canDeathBlitz(x, y, player))
            return false;

        pieces[y][x] = player; //set this piece to belong to player, no matter which move type
        playerGain += boardVals[y][x];
        emptySpaces--; //reduce counter of empty spaces
        //Check for pieces to conquer if this is death blitz attack
        if (move.getMoveType() == MoveType.M1_DEATH_BLITZ) { 
            //Check up for opponent piece to conquer
            if (y - 1 >= 0 && pieces[y-1][x] == opp) {
                pieces[y-1][x] = player;
                playerGain += boardVals[y-1][x];
                oppLoss += boardVals[y-1][x];
            }
            //Check down for opponent piece to conquer
            if (y + 1 < pieces.length && pieces[y+1][x] == opp) {
                pieces[y+1][x] = player;
                playerGain += boardVals[y+1][x];
                oppLoss += boardVals[y+1][x];
            }
            //Check left for opponent piece to conquer
            if (x - 1 >= 0 && pieces[y][x-1] == opp) {
                pieces[y][x-1] = player;
                playerGain += boardVals[y][x-1];
                oppLoss += boardVals[y][x-1];
            }
            //Check right for opponent piece to conquer
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
        return true;
    }

    //Checks if the game is over
    //if the game is over and no more moves can be made, return true
    //if there are still moves that can be made, return false
    public boolean gameOver() {
        return emptySpaces == 0;
    }

    //Return the score of the blue player
    public int getBlueScore() {
        return blueScore;
    }

    //Return the score of the green player
    public int getGreenScore() {
        return greenScore;
    }

    //Return the score of the specified color player
    public int getScore(PieceColor color) {
        return (color == PieceColor.BLUE)? blueScore : greenScore;
    }

    public PieceColor[][] getPieces() {
        return pieces;
    }

    public int[][] getValues() {
        return boardVals;
    }

    //Returns the color of the winner
    //or blank if it was a tie
    public PieceColor getWinner() {
        if (blueScore > greenScore)
            return PieceColor.BLUE;
        else if (blueScore < greenScore)
            return PieceColor.GREEN;
        else return PieceColor.BLANK;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == PieceColor.BLUE)
                    result += "(" + boardVals[i][j] + ")\t";
                else if (pieces[i][j] == PieceColor.GREEN)
                    result += "[" + boardVals[i][j] + "]\t";
                else
                    result += boardVals[i][j] + "\t";
            }
            result += "\n";
        }
        return result;
    }

    //private function that calculates if the specified player color can
    //death blitz at the specified coordinates
    //(Note: pieces is indexed by [y][x])
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
