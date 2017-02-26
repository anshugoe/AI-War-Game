//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

public class MatchManager {

    private Board board;
    private Player player1; //This player goes first, and has the color blue
    private Player player2; //This player goes second, and has the color green

    public MatchManager(Board b, Player player1, Player player2) {
        board = b;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void runGame() {
        Player currentTurnPlayer = player1;
        while (!board.gameOver()) {
            Move currentMove = currentTurnPlayer.decideMove();
            board.makeMove(currentMove);
            currentTurnPlayer = (currentTurnPlayer == player1)? player2 : player1;
        }
    }
}
