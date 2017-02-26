//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

public class Move {

    private MoveType moveType;
    private PieceColor playerColor;
    int xLoc;
    int yLoc;

    public Move(MoveType mt, PieceColor pc, int x, int y) {
        moveType = mt;
        playerColor = pc;
        xLoc = x;
        yLoc = y;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public PieceColor getPlayerColor() {
        return playerColor;
    }

    public int getX() {
        return xLoc;
    }

    public int getY() {
        return yLoc;
    }
}
