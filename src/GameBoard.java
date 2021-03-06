import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameBoard extends JPanel {

    private JLabel header; // Used by GUI.
    private JTextArea theRulesOfTicTextArea;
    private JButton startAGameButton;
    private JPanel startPagePanel;
    private JPanel gameBoardPanel;
    private JFrame frame;
    private static InputStream rulesFilepath;
    private static InputStream boardPieceFilepath;
    private static InputStream oIconFilepath;
    private static InputStream xIconFilepath;
    private static ImageIcon boardIcon;
    private final static Integer windowWidth = 400;
    private final static Integer windowHeight = 400;
    private int gameBoardSize;
    private int roundCounter = 0;

    public int getGameBoardSize() {
        return gameBoardSize;
    }

    // CUSTOM CLASSES /////////////////////
    // Get the game board image as JPanel
    class GamePanel extends JPanel {
        public GamePanel() {
            setBackground(Color.BLACK);
        }
    }

    /////////////////////////////////

    public class GameBoardButton extends JButton {
        // Class for the different game board slots/buttons.

        String coordinates;
        Image oImage;
        Image xImage;
        Image playerIcon;
        Character playerCharacter;

        // Constructor of the custom JButton, i.e. the game board slots.
        GameBoardButton(String coordinates) {
            super();
            int boardSize = getGameBoardSize();
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            addActionListener(buttonListener);
            addMouseListener(mouseListener);
            this.coordinates = coordinates;

            // Get the images for the player icons.
            try {
                // Load the stream of the board pieces.
                boardPieceFilepath = getClass().getClassLoader().getResourceAsStream("gameFiles/tictactoeEmpty.png");
                oIconFilepath = getClass().getClassLoader().getResourceAsStream("gameFiles/tictactoeOBoard.png");
                xIconFilepath = getClass().getClassLoader().getResourceAsStream("gameFiles/tictactoeXBoard.png");

                // Set board icon as own variable as it's used in in MouseListener.
                boardIcon = new ImageIcon(ImageIO.read(boardPieceFilepath).getScaledInstance(windowWidth/gameBoardSize, windowHeight/gameBoardSize, Image.SCALE_SMOOTH));
                setIcon(boardIcon);
                oImage = ImageIO.read(oIconFilepath).getScaledInstance(windowWidth/gameBoardSize, windowHeight/gameBoardSize, Image.SCALE_SMOOTH);
                xImage = ImageIO.read(xIconFilepath).getScaledInstance(windowWidth/gameBoardSize, windowHeight/gameBoardSize, Image.SCALE_SMOOTH);

            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }

        }

        // Create listener for pressing a game board button.
        public ActionListener buttonListener = new ActionListener() {
            private int localGameBoardSize = getGameBoardSize();

            @Override
            public void actionPerformed(ActionEvent e) {
                // To deny start in middle of board when possible.
                if (localGameBoardSize % 2 != 0) {
                    int middleCoordinate = (localGameBoardSize / 2) + 1;
                    String middleCoordinateString = middleCoordinate + "," + middleCoordinate;
                    if (roundCounter == 0 && coordinates.equals(middleCoordinateString)) {
                        return;
                    }
                }

                // Setup the button press.
                setContentAreaFilled(false);
                setBorderPainted(false);

                // Make sure the button can't be pressed again.
                removeMouseListener(mouseListener);
                removeActionListener(buttonListener);

                // Check which users turn.
                if (roundCounter % 2 == 0) {
                    playerIcon = xImage;
                    playerCharacter = 'X';
                } else {
                    playerIcon = oImage;
                    playerCharacter = 'O';
                }
                setIcon(new ImageIcon(playerIcon)); // The player icon, i.e. X or O.

                // Get the user selected game board slot and update the board.
                Main.userInputGUI(coordinates, playerCharacter);

                // Check if the game has a winner.
                Main.checkIfVictory();

                // Get the full board integer.
                int tieInteger = gameBoardSize * gameBoardSize - 1;

                // The game has a winner.
                if (Main.gameComplete) {
                    try {
                        // Start the popup end screen.
                        endGameScreen(true, playerCharacter);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                // The game is a tie.
                } else if (roundCounter == tieInteger && !Main.gameComplete) {
                    try {
                        // Start the popup end screen.
                        endGameScreen(false, playerCharacter);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                // Update the round counter.
                roundCounter++;

            }
        };

        // Create MouseAdapter to handle mouse hovering on game board.
        public MouseAdapter mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Hovering color
                setIcon(new ImageIcon(String.valueOf(Color.BLACK)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Don't hold a color to the non-hovered game board slot
                setIcon(boardIcon);
            }
        };


    }

    /////////////////////////////////

    // Constructor
    public GameBoard() {
        initialize();
    }

    public void main(String[] args) {
        this.showJFrame();
    }

    public void showJFrame() {
        this.frame = new JFrame("Tic Tac Toe");
        this.frame.setResizable(false);
        this.frame.setContentPane(this.startPagePanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setSize(new Dimension(windowWidth, windowHeight));
        // Center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation((dim.width/2)-this.frame.getWidth()/2, (dim.height/2)-this.frame.getHeight()/2);

    }

    private void initialize() {
        try {
            // Read the game rules file as an input stream.
            rulesFilepath = getClass().getClassLoader().getResourceAsStream("gameFiles/rulesOfTicTacToe");
            InputStreamReader rulesFileReader = new InputStreamReader(rulesFilepath);
            theRulesOfTicTextArea.read(rulesFileReader, "");

            // Await the start button.
            this.startAGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        try {
            this.gameBoardSize = setGameBoardSize();
            for (int i = 0; i < this.gameBoardSize; i++) {
                Main.xPlayerReference = Main.xPlayerReference + "X";
                Main.oPlayerReference = Main.oPlayerReference + "O";
            }

            // Create the backend game board.
            Main.playArea = Main.createEmptyBoard();

            // Create the frontend game board.
            this.gameBoardPanel = new GamePanel();
            this.gameBoardPanel.setLayout(new GridLayout(this.gameBoardSize,this.gameBoardSize));

            // Make start page non-visible.
            this.startPagePanel.setVisible(false);

            // Create an ArrayList of GameBoardButtons.
            int buttonCounter = 0;
            ArrayList<GameBoardButton> buttons = new ArrayList<>();
            for (int i = 1; i <= this.gameBoardSize; i++) {
                for (int j = 1; j <= this.gameBoardSize; j++) {
                    String coordinate = i + "," + j;
                    GameBoardButton newButton = new GameBoardButton(coordinate);
                    buttons.add(newButton);
                    this.gameBoardPanel.add(buttons.get(buttonCounter));
                    buttonCounter++;
                }
            }

            // Set the game panel to visible.
            this.gameBoardPanel.setVisible(true);

            // Add the game panel to the JFrame.
            this.frame.setContentPane(this.gameBoardPanel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int setGameBoardSize() {
        JOptionPane sizeOption = new JOptionPane();
        JLabel message = new JLabel("Select a board size between 3 - 9.");
        Object[] possibleGameSizes = {3, 4, 5, 6, 7, 8, 9};
        int userChoice = (Integer) sizeOption.showInputDialog(null, message, "", JOptionPane.PLAIN_MESSAGE, null, possibleGameSizes, possibleGameSizes[0]);

        return userChoice;
    }

    private void endGameScreen(boolean winner, Character player) throws IOException {

        // Make the game buttons non-enabled, i.e. making them non-clickable when game is over (in case user closes the popup with the top-right X button).
        Component[] components = gameBoardPanel.getComponents();
        for (Component component : components) {
            component.setEnabled(false);
        }

        JLabel gameEndingText = new JLabel(); // Create JLabel to add text to.
        JOptionPane gameEndingPopup = new JOptionPane(); // Create a JOptionPane popup screen.

        // If the game ended with a winner.
        if (winner) {
            gameEndingText.setText("The winner is player " + player + "!");
        // If the game ended in a tie.
        } else {
            gameEndingText.setText("The game ended in a draw!");
        }

        // Design the popup screen.
        Object[] options = {"End game"};
        gameEndingPopup.setOptions(options);
        int answer = gameEndingPopup.showOptionDialog(null, gameEndingText, "Tic Tac Toe", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // Handle the popup button press.
        if (answer == JOptionPane.YES_OPTION) { // 0 == 0
            // End software
            System.exit(0);
        }
    }
}
