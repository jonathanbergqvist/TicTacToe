import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameBoard extends JPanel {

    private JPanel emptyPanel;
    private JLabel header;
    private JTextArea theRulesOfTicTextArea;
    private JButton startAGameButton;

    public GameBoard() {
        initialize();
    }

    public static void main(String[] args) {
        GameBoard.showJFrame();
    }



    public static void showJFrame() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setContentPane(new GameBoard().emptyPanel);
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
            theRulesOfTicTextArea.read(new FileReader("textFiles/rulesOfTicTacToe"),null);
            startAGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    theRulesOfTicTextArea.hide();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
