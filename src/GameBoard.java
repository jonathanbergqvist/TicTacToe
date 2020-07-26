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
        this.frame.setSize(new Dimension(400, 400));
        // Center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation((dim.width/2)-this.frame.getWidth()/2, (dim.height/2)-this.frame.getHeight()/2);

    }

    public void showJFrame(JPanel contentPane) {
        this.frame = new JFrame("Tic Tac Toe");
        if (contentPane == null) {
            this.frame.setContentPane(this.startPagePanel);
        } else {
            this.frame.setContentPane(contentPane);
        }
        this.frame.setContentPane(this.startPagePanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setSize(new Dimension(400, 400));
        // Center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation((dim.width/2)-this.frame.getWidth()/2, (dim.height/2)-this.frame.getHeight()/2);

    }

    private void initialize() {
        try {
            // Read some text from the resource file to display in the JTextArea.
            theRulesOfTicTextArea.read(new FileReader("gameFiles/rulesOfTicTacToe"),null);
            startAGameButton.addActionListener(new ActionListener() {
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

    private static void DisplayImage(JPanel jp, String url) {
        JLabel jl = new JLabel();
        //System.out.println("PATH: " + GameBoardTest.class.getResource(boardFilepath).getPath());
        jl.setIcon(new javax.swing.ImageIcon(GameBoardTest.class.getResource(url)));
        jp.add(jl);
    }

    private void startGame() {
        try {
            this.startPagePanel.setVisible(false);
            //BufferedImage image = ImageIO.read(new File("gameFiles/tictactoeboard.png"));
            //ImageIcon icon = new ImageIcon(getClass().getResource("gameFiles/tictactoeboard.png"));
            //JLabel gameBoardLabel = new JLabel("gameFiles/tictactoeboard.png");
            //gameBoardLabel.setIcon(icon);
            //gameBoardPanel.add(gameBoardLabel);
            this.gameBoardPanel = new JPanel();
            DisplayImage(this.gameBoardPanel, this.boardFilepath);
            this.gameBoardPanel.setVisible(true);
            this.frame.setContentPane(this.gameBoardPanel);
            //this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //this.frame.pack();
            //this.frame.setVisible(true);
            //this.frame.setSize(new Dimension(400, 400));
            // Center window
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.frame.setLocation((dim.width/2)-this.frame.getWidth()/2, (dim.height/2)-this.frame.getHeight()/2);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
