import java.util.ArrayList;

public class Main {

    static ArrayList<ArrayList<Character>> playArea = new ArrayList<>();
    static String xPlayerReference = "";
    static String oPlayerReference = "";
    final static Character emptySlot = ' ';
    static int selected_x_cord;
    static int selected_y_cord;
    static boolean gameComplete = false;
    static int roundCounter = 0;
    static GameBoard game;

    public static void main(String[] args) {
        playAGame();
    }


    public static void playAGame() {
        // GUI
        game = new GameBoard();
        game.main(null);

    }

    public static ArrayList<ArrayList<Character>> createEmptyBoard(){
        for (int i = 0; i < game.getGameBoardSize(); i++) {
            ArrayList<Character> line = new ArrayList<>();
            for (int j = 0; j < game.getGameBoardSize(); j++) {
                line.add(emptySlot);
            }
            playArea.add(line);
        }

        return playArea;

    }

    public static void checkIfVictory() {

        // The Strings for rows, columns and diagonals was inspired by MeneXia (Xavi Ablaza) at https://gist.github.com/xaviablaza-zz/3844825

        // TODO: Check why victory on middle row won't register
        // Check rows
        System.out.println(roundCounter);
        for (ArrayList<Character> line : playArea) {
            System.out.println(line);
            String row = "";
            for (Character singleCharacter : line) {
                row = row + singleCharacter.toString();
            }
            System.out.println("row: " + row);
            if (row.equals(xPlayerReference) || row.equals(oPlayerReference)) {
                gameComplete = true;
                return;
            }
        }

        // Check columns
        String column = "";
        for (int i = 0; i < game.getGameBoardSize(); i++) {
            for (ArrayList<Character> line : playArea) {
                column = column + line.get(0);
            }

            System.out.println("column: " + column);
            if (column.equals(xPlayerReference) || column.equals(oPlayerReference)) {
                gameComplete = true;
                return;
            }
        }

        // Check diagonals
        String diagonal_tlbr = "";
        String diagonal_trbl = "";
        for (int i = 0; i < game.getGameBoardSize(); i++) {
            Character lineChar_tlbr = playArea.get(i).get(i);
            diagonal_tlbr = diagonal_tlbr + lineChar_tlbr;

            Character lineChar_trbl = playArea.get(i).get(game.getGameBoardSize() - 1 - i);
            diagonal_trbl = diagonal_trbl + lineChar_trbl;
        }

        if (diagonal_tlbr.equals(xPlayerReference) || diagonal_trbl.equals(xPlayerReference)) {
            gameComplete = true;
        } else if (diagonal_tlbr.equals(oPlayerReference) || diagonal_trbl.equals(oPlayerReference)) {
            gameComplete = true;
        }
    }

    public static boolean userInputGUI(String coordinates, Character player) {
        try {
            // Set the user input with user icon.
            String[] splitCoordinates = coordinates.split(",");
            selected_x_cord = Integer.parseInt(splitCoordinates[0]);
            selected_y_cord = Integer.parseInt(splitCoordinates[1]);
            playArea.get(selected_y_cord-1).set(selected_x_cord-1, player);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
