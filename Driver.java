//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        int[][] boardVals = null;
        try {
            boardVals = readBoardFile(args[0]);
        }
        catch (FileNotFoundException e) {
            System.err.println("File " + args[0] + " not found.");
            System.exit(1);
        }

        Board b = new Board(boardVals);
        Player player1 = new MinimaxPlayer(b, PieceColor.GREEN, 4);
        Player player2 = new RandomPlayer(b, PieceColor.BLUE);
        MatchManager mm = new MatchManager(b, player1, player2);
        mm.runGame();
    }

    public static int[][] readBoardFile(String filename) throws FileNotFoundException {
        File f = new File(filename);
        Scanner sc = new Scanner(f);
        int[][] board = new int[5][5];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = sc.nextInt();
            }
        }
        sc.close();
        return board;
    }

}
