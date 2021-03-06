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
        if (args.length < 3 ) {
            System.err.println("Not enough arguments. Args: gameboardfile, player1, player2.");
            System.exit(1);
        }

        //Read board values from file in arguments
        int[][] boardVals = readBoardFile(args[0]);

        //Create new board from those values
        Board b = new Board(boardVals);

        if (args.length >= 4 && args[3].equalsIgnoreCase("multi")) {
            multiRun(b, args[1], args[2]);
        }
        else {
        
            //Create player objects according to arguments
            Player player1 = getPlayer(args[1], b, PieceColor.BLUE);
            Player player2 = getPlayer(args[2], b, PieceColor.GREEN);
            //Check that the player type is valid
            if (player1 == null || player2 == null) {
                System.err.println("Unknown player type. Types include R, MM, AB.");
                System.exit(1);
            }
            //Play the game and print the results.
            MatchManager mm = new MatchManager(b, player1, player2);
            mm.runGame();
            System.out.println(mm.getStats());
        }
    }

    //This function allows multiple runs of games during the same session
    //The stats from these games are averaged together.
    public static void multiRun(Board origb, String player1type, String player2type) {
        int totalBlueScore = 0;
        int totalGreenScore = 0;
        double totalBlueMoveTime = 0;
        double totalGreenMoveTime = 0; //variables to save stats in
        System.out.print("Running... ");
        for (int i = 0; i < 50; i++) {
            Board b = origb.copy();
            Player player1 = getPlayer(player1type, b, PieceColor.BLUE);
            Player player2 = getPlayer(player2type, b, PieceColor.GREEN);
            //Check that the player type is valid
            if (player1 == null || player2 == null) {
                System.err.println("Unknown player type. Types include R, MM, AB.");
                System.exit(1);
            }

            //Run the game
            MatchManager mm = new MatchManager(b, player1, player2);
            mm.runGame();
            totalBlueScore += player1.getScore();
            totalGreenScore += player2.getScore();
            totalBlueMoveTime += player1.getAvgMoveTime();
            totalGreenMoveTime += player2.getAvgMoveTime();
        }
        //Print the results
        System.out.println();
        System.out.println("Avg blue score: " + (totalBlueScore / 50));
        System.out.println("Avg green score: " + (totalGreenScore / 50));
        System.out.println("Avg avg blue time: " + (totalBlueMoveTime / 50));
        System.out.println("Avg avg green time: " + (totalGreenMoveTime / 50));

    }

    //Get a player object according to what the argument is
    public static Player getPlayer(String arg, Board b, PieceColor color) {
        if (arg.equalsIgnoreCase("r")) // Random Player 
            return new RandomPlayer(b, color);
        else if (arg.equalsIgnoreCase("mm")) //Minimax Player
            return new MinimaxPlayer(b, color, 4);
        else if (arg.equalsIgnoreCase("ab")) // AlphaBeta Player
            return new AlphaBetaPlayer(b, color, 5);
        else return null;
    }

    //Read from the file into a 2d-array. 
    //Will print errors and exit if something goes wrong.
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
