//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

public class MatchManager {

    private Board board;
    private Player player1; //This player goes first, and has the color blue
    private Player player2; //This player goes second, and has the color green

    //Constructor for a match manager.
    //One player needs to be blue, and the other one green.
    //The blue player will always go first.
    public MatchManager(Board b, Player player1, Player player2) {
        board = b;
        this.player1 = (player1.getColor() == PieceColor.BLUE)? player1 : player2;
        this.player2 = (player2.getColor() == PieceColor.GREEN)? player2 : player1;
    }

    public void runGame() {
        Player currentTurnPlayer = player1;
        while (!board.gameOver()) {
            Move currentMove = currentTurnPlayer.decideMove();
            board.makeMove(currentMove);
            currentTurnPlayer = (currentTurnPlayer == player1)? player2 : player1;
        }
        PieceColor winner = board.getWinner();
        if (winner == PieceColor.BLUE)
            System.out.println("Blue won!");
        else
            System.out.println("Green won!");
    }
}
