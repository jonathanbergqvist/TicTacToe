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


    public GameBoard() {
        initialize();
    }

    public static void main(String[] args) {
        showJFrame();
    }



    public static void showJFrame() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setContentPane(new GameBoard().startPagePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(400, 400));
        // Center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((dim.width/2)-frame.getWidth()/2, (dim.height/2)-frame.getHeight()/2);

    }

    private void initialize() {
        try {
            // Read some text from the resource file to display in the JTextArea.
            theRulesOfTicTextArea.read(new FileReader("gameFiles/rulesOfTicTacToe"),null);
            startAGameButton.addActionListener(new ActionListener() {
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
            //startPagePanel.setVisible(false);
            //BufferedImage image = ImageIO.read(new File("gameFiles/tictactoeboard.png"));
            //ImageIcon icon = new ImageIcon(getClass().getResource("gameFiles/tictactoeboard.png"));
            JLabel gameBoardLabel = new JLabel("gameFiles/tictactoeboard.png");
            //gameBoardLabel.setIcon(icon);
            gameBoardPanel.add(gameBoardLabel);
            gameBoardPanel.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
