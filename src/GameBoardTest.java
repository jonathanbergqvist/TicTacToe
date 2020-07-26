import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameBoardTest {



    private JPanel testPanel;
    private JLabel testLabel;
    private final static String boardFilepath = "gameFiles/tictactoeboard.png";


    public GameBoardTest() {
        testPanel = new JPanel();

    }

    public void main(String[] args) throws IOException {

/*        BufferedImage myPicture = ImageIO.read(new File("gameFiles/rulesOfTicTacToe"));
        JLabel jl=new JLabel();
        jl.setIcon(new javax.swing.ImageIcon(getClass().getResource("gameFiles/rulesOfTicTacToe")));
        testPanel.add(jl);
*/
        showJFrame();
        DisplayImage(this.testPanel, this.boardFilepath);

    }

    private static void showJFrame() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setContentPane(new GameBoardTest().testPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JLabel(new ImageIcon(GameBoardTest.class.getResource("tictactoeboard.png")))); // THIS WORKS!
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(400, 400));
        // Center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((dim.width/2)-frame.getWidth()/2, (dim.height/2)-frame.getHeight()/2);

    }

    private static void DisplayImage(JPanel jp, String url) {
        JLabel jl = new JLabel();
        //String file = GameBoardTest.class.getResource(url).getPath();
        System.out.println("PATH: " + GameBoardTest.class.getResource(boardFilepath).getPath());
        jl.setIcon(new javax.swing.ImageIcon(GameBoardTest.class.getResource(url)));
        jp.add(jl);
//        jp.setVisible(true);
    }

}
