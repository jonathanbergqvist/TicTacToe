import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GameBoard extends JPanel {

    private JLabel header;
    private JTextArea theRulesOfTicTextArea;
    private JButton startAGameButton;
    private JLabel gameBoardLabel;
    private JPanel startPagePanel;
    private JPanel gameBoardPanel;
    private JFrame frame;
    private final static String boardFilepath = "tictactoeboard.png";
    private final static Integer windowWidth = 400;
    private final static Integer windowHeight = 400;



    public GameBoard() {
        initialize();
    }

    public void main(String[] args) {
        System.out.println("SHOW JFRAME");
        this.showJFrame();
    }

    public void showJFrame() {
        this.frame = new JFrame("Tic Tac Toe");
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
            // Read some text from the resource file to display in the JTextArea.
            this.gameBoardPanel = new JPanel();
            theRulesOfTicTextArea.read(new FileReader("gameFiles/rulesOfTicTacToe"),null);
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

    private static void DisplayImage(JPanel jp, String url) throws IOException {
        JLabel jl = new JLabel();
        Image image = ImageIO.read(new File("gameFiles/tictactoeboard.png")).getScaledInstance(windowWidth-50, windowHeight-50, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        jl.setIcon(icon);
        //jl.setIcon(new javax.swing.ImageIcon(GameBoardTest.class.getResource(url)));
        jp.add(jl);
    }

    private void startGame() {
        try {
            this.startPagePanel.setVisible(false);
            DisplayImage(this.gameBoardPanel, this.boardFilepath);
            this.gameBoardPanel.setVisible(true);
            this.frame.setContentPane(this.gameBoardPanel);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
