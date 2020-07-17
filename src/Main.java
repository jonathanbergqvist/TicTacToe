import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<ArrayList<Byte>> emptyMatrix = new ArrayList<>();


    public static void main(String[] args) {
        ArrayList<ArrayList<Byte>> emptyMatrix = createEmptyBoard();
    }

    public static ArrayList<ArrayList<Byte>> createEmptyBoard(){
        ArrayList<Byte> emptyRow = new ArrayList<Byte>();
        for (int i = 0; i < 3; i++) {
            emptyRow.add(null);
        }
        for (int i = 0; i < 3; i++) {
            emptyMatrix.add(emptyRow);
        }
        return emptyMatrix;

    }

    public static void enterSlot(boolean player) {
        String playerIcon = "O";
        if (player == true) {
            playerIcon = "X";
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Player " + playerIcon + " select a spot on the grid");
        System.out.println("Enter input as X,Y where X and Y is 1-3.");
        String userInput = scanner.nextLine();
        if (userInput.length() == 3 && userInput.charAt(1) == ',') {
            String[] coordinates = userInput.split(",");
            int x_coord = Integer.parseInt(coordinates[0]);
            int y_coord = Integer.parseInt(coordinates[1]);

            if (x_coord >= 1 && x_coord <= 3 && y_coord >= 1 && y_coord <= 3) {

            } else {
                System.out.println("Please enter a valid input position.");
                userInput = scanner.nextLine();
            }


        }



    }




}
