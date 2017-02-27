//Dylan Wulf
//CSC380: Artificial Intelligence
//Project 2: War Game
//February 26, 2017

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        int[][] boardVals = readBoardFile(args[0]);
        Board b = new Board(boardVals);
        Player player1 = new MinimaxPlayer(b, PieceColor.GREEN, 4);
        Player player2 = new RandomPlayer(b, PieceColor.BLUE);
        MatchManager mm = new MatchManager(b, player1, player2);
        mm.runGame();
        System.out.println(mm.getStats());
    }

    public static int[][] readBoardFile(String filename) {
        File f = null;
        Scanner sc = null;
        int[][] board = new int[5][5];
        try {
            f = new File(filename);
            sc = new Scanner(f);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    board[i][j] = sc.nextInt();
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("Error: file " + filename + " not found.");
            System.exit(1);
        }
        catch (InputMismatchException e) {
            System.err.println("Error: file " + filename + " contains something other than integers and whitespace.");
            System.exit(1);
        }
        catch (NoSuchElementException e) {
            System.err.println("Error: the board in file " + filename + " is smaller than 5x5.");
            System.exit(1);
        }
        sc.close();
        return board;
    }

}
