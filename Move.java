//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

//This class defines a move that can be performed on a Board.
public class Move {

    private MoveType moveType; //either para drop or death blitz
    private PieceColor playerColor; //which player is doing the move
    private int xLoc; //location of the piece the player is putting down
    private int yLoc;

    //Constructor sets all the members above
    public Move(MoveType mt, PieceColor pc, int x, int y) {
        moveType = mt;
        playerColor = pc;
        xLoc = x;
        yLoc = y;
    }

    //returns move type
    public MoveType getMoveType() {
        return moveType;
    }

    //Returns which color the player is who is doing this move
    public PieceColor getPlayerColor() {
        return playerColor;
    }

    //get x coordinate of the piece being put down
    public int getX() {
        return xLoc;
    }

    //get y coordinate of the piece being put down
    public int getY() {
        return yLoc;
    }
}
