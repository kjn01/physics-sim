import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Simulator extends JFrame {

    public static void main(String[] args) throws InterruptedException {


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame();
        frame.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new Particles(500));

        frame.setVisible(true);

    }
    
}
