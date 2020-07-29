import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<ArrayList<Character>> playArea = new ArrayList<>();
    static ArrayList<Character> row_0 = new ArrayList<>();
    static ArrayList<Character> row_1 = new ArrayList<>();
    static ArrayList<Character> row_2 = new ArrayList<>();
    final static String xPlayerReference = "XXX";
    final static String oPlayerReference = "OOO";
    final static Character emptySlot = ' ';
    static int selected_x_cord;
    static int selected_y_cord;
    static boolean gameComplete = false;
    static int roundCounter = 0;
    static GameBoard game;

    public static void main(String[] args) throws IOException {
        // Create the game board.
        playArea = createEmptyBoard();
        // Print initial board

        ////////////

        // GUI

        game = new GameBoard();
        game.main(null);
        //GameBoardTest gameTest = new GameBoardTest();
        //gameTest.main(null);

        ////////////

        /*printGameBoard();
        // While no winner is found, loop.
        while (!gameComplete) {
            // Get user position if board not full (then ends game).
            turnOfGame();
            // Check if victory is possible.
            checkIfVictory();
            // Print the game board.
            printGameBoard();
            // Increase the round counter to allow check if game board is full without a winner.
            roundCounter++;

        }*/
    }

    public static ArrayList<ArrayList<Character>> createEmptyBoard(){
        for (int i = 0; i < 3; i++) {
            row_0.add(emptySlot);
            row_1.add(emptySlot);
            row_2.add(emptySlot);
        }
        playArea.add(row_0);
        playArea.add(row_1);
        playArea.add(row_2);

        return playArea;

    }

    public static void printGameBoard() {
        for (ArrayList<Character> playAreaRow : playArea) {
            System.out.println("+-----------+");
            System.out.println("| " + playAreaRow.get(0) + " | " + playAreaRow.get(1) + " | " + playAreaRow.get(2) + " |");

        }
        System.out.println("+-----------+");
    }

    public static void checkIfVictory() {

        // The Strings for rows, columns and diagonals was inspired by MeneXia (Xavi Ablaza) at https://gist.github.com/xaviablaza-zz/3844825

        // Check rows
        for (ArrayList<Character> line : playArea) {
            String row = line.get(0).toString() + line.get(1).toString() + line.get(2).toString();
            if (row.equals(xPlayerReference)) {
                System.out.println("Player X won!");
                gameComplete = true;
                return;
            } else if (row.equals(oPlayerReference)) {
                System.out.println("Player O won!");
                gameComplete = true;
                return;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            String column = row_0.get(i).toString() + row_1.get(i).toString() + row_2.get(i).toString();
            if (column.equals(xPlayerReference)) {
                System.out.println("Player X won!");
                gameComplete = true;
                return;
            } else if (column.equals(oPlayerReference)) {
                System.out.println("Player O won!");
                gameComplete = true;
                return;
            }
        }

        // Check diagonals
        String diagonal_tlbr = row_0.get(0).toString() + row_1.get(1).toString() + row_2.get(2).toString();
        String diagonal_trbl = row_0.get(2).toString() + row_1.get(1).toString() + row_2.get(0).toString();

        if (diagonal_tlbr.equals(xPlayerReference) || diagonal_trbl.equals(xPlayerReference)) {
            System.out.println("Player X won!");
            gameComplete = true;
        } else if (diagonal_tlbr.equals(oPlayerReference) || diagonal_trbl.equals(oPlayerReference)) {
            System.out.println("Player O won!");
            gameComplete = true;
        }
    }

    public static void turnOfGame() {
        Character playerIcon = 'O';
        if (roundCounter % 2 == 0) {
            playerIcon = 'X';
        }
        if (roundCounter == 9) {
            System.out.println("Game over. No Winner.");
            System.exit(0);
        }
        System.out.println("Player " + playerIcon + " select a spot on the grid");
        boolean selectedUserInput = userInput(playerIcon);
        while (!selectedUserInput) {
            System.out.println("The input was not valid, please try again.");
            selectedUserInput = userInput(playerIcon);
        }
    }

    public static boolean userInputGUI(String coordinates, Character player) {
        try {
            String[] splitCoordinates = coordinates.split(",");
            selected_x_cord = Integer.parseInt(splitCoordinates[0]);
            selected_y_cord = Integer.parseInt(splitCoordinates[1]);
            playArea.get(selected_y_cord-1).set(selected_x_cord-1, player);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean userInput(Character playerIcon) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter input as X,Y where X and Y is 1-3,");
        System.out.println("where X=1 and Y=1 is top left corner.");
        String userInput = scanner.nextLine();
        if (userInput.length() == 3 && userInput.charAt(1) == ',') {
            try {
                String[] coordinates = userInput.split(",");
                selected_x_cord = Integer.parseInt(coordinates[0]);
                selected_y_cord = Integer.parseInt(coordinates[1]);
            } catch (Exception e) {
                // Incorrect input
                return false;
            }
            // Check if the selected position is within the game board.
            if (selected_x_cord >= 1 && selected_x_cord <= 3 && selected_y_cord >= 1 && selected_y_cord <= 3) {
                // Avoid first move in center of board.
                if (roundCounter == 0 && selected_x_cord == 2 && selected_y_cord == 2) {
                    return false;
                }

                // Check if user input is an empty non-played spot.
                if (playArea.get(selected_y_cord-1).get(selected_x_cord-1) == emptySlot) {
                    // Selected slot allowed.
                    playArea.get(selected_y_cord-1).set(selected_x_cord-1, playerIcon);
                    return true;
                } else {
                    // To select new position not already played.
                    return false;
                }
            } else {
                // Input outside allowed of range. Try a new input.
                return false;
            }
        }
        // General incorrect input. Try again.
        return false;
    }
}
