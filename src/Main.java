import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<ArrayList<Character>> playArea = new ArrayList<>();
    static ArrayList<Character> row_0 = new ArrayList<>();
    static ArrayList<Character> row_1 = new ArrayList<>();
    static ArrayList<Character> row_2 = new ArrayList<>();
    static ArrayList<Character> column_0 = new ArrayList<>();
    static ArrayList<Character> column_1 = new ArrayList<>();
    static ArrayList<Character> column_2 = new ArrayList<>();
    static ArrayList<Character> diagonal_tlbr = new ArrayList<>(); // Top left to bottom right
    static ArrayList<Character> diagonal_trbl = new ArrayList<>(); // Top right to bottom left
    static ArrayList<Character> xPlayerReference = new ArrayList<>();
    static ArrayList<Character> oPlayerReference = new ArrayList<>();
    final static Character emptySlot = ' ';
    static int selected_x_cord;
    static int selected_y_cord;
    static boolean gameComplete = false;
    static int roundCounter = 0;

    // TODO: Check if the user input results in game won.
    public static void main(String[] args) {
        playArea = createEmptyBoard();
        while (gameComplete == false) {
            for (ArrayList<Character> playAreaRow : playArea) {
                System.out.println(playAreaRow);
            }
            checkIfVictory();
            turnOfGame();
            roundCounter++;
        }

    }

    public static ArrayList<ArrayList<Character>> createEmptyBoard(){
        for (int i = 0; i < 3; i++) {
            row_0.add(emptySlot);
            row_1.add(emptySlot);
            row_2.add(emptySlot);
            xPlayerReference.add('X');
            oPlayerReference.add('O');
        }
        playArea.add(row_0);
        playArea.add(row_1);
        playArea.add(row_2);

        return playArea;

    }

    public static void updateRowsColsAndDiags() {
        // Update the rows, columns and diagonals with their current representations.
        int diagonalIndex = 0;

        // Rows
        row_0 = playArea.get(0);
        row_1 = playArea.get(1);
        row_2 = playArea.get(2);

        for (ArrayList<Character> row : playArea) {
            //System.out.println("ROW: " + row);
            // Columns
            // Create initial columns
            //System.out.println("COL: " + column_0.size());
            if (column_0.size() < 3 || column_1.size() < 3 || column_2.size() < 3) {
                column_0.add(row.get(0));
                column_1.add(row.get(1));
                column_2.add(row.get(2));
            } else {
                // Update columns
                for (int i = 0; i < 3; i++) {
                    column_0.set(i, row.get(i));
                    column_1.set(i, row.get(i));
                    column_2.set(i, row.get(i));
                }
            }

            if (diagonal_tlbr.size() < 3 || diagonal_trbl.size() < 3) {
                diagonal_tlbr.add(row.get(diagonalIndex));
                diagonal_trbl.add(row.get(2-diagonalIndex));
            } else {
                diagonal_tlbr.set(diagonalIndex, row.get(diagonalIndex));
                diagonal_trbl.set(2-diagonalIndex, row.get(2-diagonalIndex));
            }


        }
    }

    public static void checkIfVictory() {

        System.out.println("R0: " + row_0);
        System.out.println("R1: " + row_1);
        System.out.println("R2: " + row_2);
        System.out.println("C0: " + column_0);
        System.out.println("C1: " + column_1);
        System.out.println("C2: " + column_2);
        System.out.println("D0: " + diagonal_trbl);
        System.out.println("D1: " + diagonal_trbl);

        // TODO: Fix columns and diagonals.
        if (row_0.equals(xPlayerReference) || row_1.equals(xPlayerReference) || row_2.equals(xPlayerReference) ||
                column_0.equals(xPlayerReference) || column_1.equals(xPlayerReference) || column_2.equals(xPlayerReference) ||
                diagonal_tlbr.equals(xPlayerReference) || diagonal_trbl.equals(xPlayerReference)) {
            gameComplete = true;
            System.out.println("The winner is PLAYER X!");
            System.exit(0);
        } else if (row_0.equals(oPlayerReference) || row_1.equals(oPlayerReference) || row_2.equals(oPlayerReference) ||
                column_0.equals(oPlayerReference) || column_1.equals(oPlayerReference) || column_2.equals(oPlayerReference) ||
                diagonal_tlbr.equals(oPlayerReference) || diagonal_trbl.equals(oPlayerReference)) {
            gameComplete = true;
            System.out.println("The winner is PLAYER O!");
            System.exit(0);
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
        //System.out.println("Counter: " + roundCounter);
        System.out.println("Player " + playerIcon + " select a spot on the grid");
        boolean selectedUserInput = userInput(playerIcon);
        while (!selectedUserInput) {
            System.out.println("The input was not valid, please try again.");
            selectedUserInput = userInput(playerIcon);
        }

    }

     public static boolean userInput(Character playerIcon) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter input as X,Y where X and Y is 1-3.");
        String userInput = scanner.nextLine();
        if (userInput.length() == 3 && userInput.charAt(1) == ',') {
            String[] coordinates = userInput.split(",");
            try {
                selected_x_cord = Integer.parseInt(coordinates[0]);
                selected_y_cord = Integer.parseInt(coordinates[1]);
            } catch (Exception e) {
                return false;
            }
            if (selected_x_cord >= 1 && selected_x_cord <= 3 && selected_y_cord >= 1 && selected_y_cord <= 3) {
                // Avoid first move in center of board.
                if (roundCounter == 0 && selected_x_cord == 2 && selected_y_cord == 2) {
                    return false;
                }

                // Check if empty non-played spot
                if (playArea.get(selected_y_cord-1).get(selected_x_cord-1) == emptySlot) {
                    // Selected slot allowed.
                    playArea.get(selected_y_cord-1).set(selected_x_cord-1, playerIcon);
                    updateRowsColsAndDiags();
                    return true;
                } else {
                    // Select new position
                    return false;
                }
            } else {
                return false;
            }
        }
         return false;
     }




}
