import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GameBoard extends JPanel {

    private JLabel header;
    private JTextArea theRulesOfTicTextArea;
    private JButton startAGameButton;
    private JPanel startPagePanel;
    private JPanel gameBoardPanel;
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button21;
    private JButton button22;
    private JButton button23;
    private JButton button31;
    private JButton button32;
    private JButton button33;
    private JFrame frame;
    private final static String rulesFilepath = "gameFiles/rulesOfTicTacToe";
    private final static String boardFilepath = "gameFiles/tictactoeboard.png";
    private final static String oIconFilepath = "gameFiles/tictactoeO.png";
    private final static String xIconFilepath = "gameFiles/tictactoeX.png";
    private final static Integer windowWidth = 400;
    private final static Integer windowHeight = 400;
    int roundCounter = 0;



    // CUSTOM CLASSES /////////////////////
    class GamePanel extends JPanel {
        private Image background;
        public GamePanel(String fileName) throws IOException {
            background = ImageIO.read(new File(fileName)).getScaledInstance(windowWidth-13, windowHeight-29, Image.SCALE_SMOOTH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, this);
        }

    }

    /////////////////////////////////

    public class GameBoardButton extends JButton{

        String coordinates;
        Image oImage;
        Image xImage;
        Image playerIcon;
        Character playerCharacter;


        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Set selected button and enter coordinates

                if (roundCounter == 0 && coordinates.equals("2,2")) {
                    return;
                }


                setContentAreaFilled(false);
                setBorderPainted(false);
                removeMouseListener(mouseListener);
                removeActionListener(buttonListener);

                if (roundCounter % 2 == 0) {
                    playerIcon = xImage;
                    playerCharacter = 'X';
                } else {
                    playerIcon = oImage;
                    playerCharacter = 'O';
                }
                setIcon(new ImageIcon(playerIcon));

                Main.userInputGUI(coordinates, playerCharacter);
                Main.checkIfVictory();

                if (Main.gameComplete) {
                    // TODO: Show a winner message allowing no more moves. Remove System.exit(0).

                    System.exit(0);
                } else if (roundCounter == 8 && !Main.gameComplete) {
                    // TODO: Show a tie message allowing no more moves. Remove System.exit(0).

                    System.out.println("Game over. No Winner.");
                    System.exit(0);
                }
                roundCounter++;

            }
        };

        MouseAdapter mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setContentAreaFilled(true); // Sets the color
                setBackground(Color.BLACK);
                setForeground(Color.BLACK);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setContentAreaFilled(false);
            }
        };

        GameBoardButton(String coordinates){
            super();
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            addActionListener(buttonListener);
            addMouseListener(mouseListener);
            this.coordinates = coordinates;
            //System.out.println("PATH: " + new File(oIconFilepath).getAbsolutePath());

            try {
                oImage = ImageIO.read(new File(oIconFilepath)).getScaledInstance(130, 130, Image.SCALE_SMOOTH);
                xImage = ImageIO.read(new File(xIconFilepath)).getScaledInstance(130, 130, Image.SCALE_SMOOTH);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /////////////////////////////////

    public GameBoard() {
        initialize();
    }

    public void main(String[] args) {
        System.out.println("SHOW JFRAME");
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

            // Create the game board.
            this.gameBoardPanel = new GamePanel(boardFilepath);
            this.gameBoardPanel.setLayout(new GridLayout(3,3));

            // Read the text file onto the start/rules page.
            theRulesOfTicTextArea.read(new FileReader(rulesFilepath),null);

            // Await the start button.
            this.startAGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("START GAME");
                    startGame();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        try {
            this.startPagePanel.setVisible(false);

            this.button11 = new GameBoardButton("1,1");
            //String coor = ((GameBoardButton) this.button11).getCoordinates();
            //System.out.println("COOR: " + coor);
            this.gameBoardPanel.add(this.button11);
            this.button12 = new GameBoardButton("1,2");
            this.gameBoardPanel.add(this.button12);
            this.button13 = new GameBoardButton("1,3");
            this.gameBoardPanel.add(this.button13);
            this.button21 = new GameBoardButton("2,1");
            this.gameBoardPanel.add(this.button21);
            this.button22 = new GameBoardButton("2,2");
            this.gameBoardPanel.add(this.button22);
            this.button23 = new GameBoardButton("2,3");
            this.gameBoardPanel.add(this.button23);
            this.button31 = new GameBoardButton("3,1");
            this.gameBoardPanel.add(this.button31);
            this.button32 = new GameBoardButton("3,2");
            this.gameBoardPanel.add(this.button32);
            this.button33 = new GameBoardButton("3,3");
            this.gameBoardPanel.add(this.button33);

            this.gameBoardPanel.setVisible(true);

            this.frame.setContentPane(this.gameBoardPanel);

            //awaitUserInput();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void awaitUserInput() {



        /*this.button11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("BUTTON FOUND");
            }
        });

        button11.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button11.setContentAreaFilled(true);
                button11.setBackground(Color.BLACK);
            }
            public void mouseExited(MouseEvent e) {
                button11.setContentAreaFilled(false);

            }
        });*/
    }
}
