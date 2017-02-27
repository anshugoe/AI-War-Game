//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

public class MatchManager {

    private Board board;
    private Player player1blue; //This player goes first, and has the color blue
    private Player player2green; //This player goes second, and has the color green

    //Constructor for a match manager.
    //One player needs to be blue, and the other one green.
    //The blue player will always go first.
    public MatchManager(Board b, Player player1, Player player2) {
        board = b;
        //make sure the blue one is save in player1, and the green one in player2
        this.player1blue = (player1.getColor() == PieceColor.BLUE)? player1 : player2;
        this.player2green = (player2.getColor() == PieceColor.GREEN)? player2 : player1;
    }

    //Use a loop to run the game until someone wins.
    public void runGame() {
        Player currentTurnPlayer = player1blue; //blue always goes first
        while (!board.gameOver()) { //keep going until someone wins
            Move currentMove = currentTurnPlayer.getNextMove(); //get next move and apply
            board.makeMove(currentMove);
            //Switch to other player's turn
            currentTurnPlayer = (currentTurnPlayer == player1blue)? player2green : player1blue;
        }
    }

    //Returns a string that contains stats about each player, along with who won.
    public String getStats() {
        String result = "";
        result += board.toString() + "\n--------------------------------------\n";
        result += "Player 1: Blue\n" + player1blue.toString() + "\n";
        result += "--------------------------------------\n";
        result += "Player 2: Green\n" + player2green.toString() + "\n";
        result += "--------------------------------------\n";
        result += "Winner: " + (board.getWinner() == PieceColor.BLUE ? "Player 1 (Blue)" : "Player 2 (Green)");
        return result;
    }
}
