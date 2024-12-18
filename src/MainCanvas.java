
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainCanvas extends JPanel implements Runnable {

    public static ArrayList<Tween> tweenList = new ArrayList<>();

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    public MainCanvas() {}

    public static void main(String[] args) {
        // Run Frame
        JFrame frame = new JFrame("Minesweeper Java 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(MySettings.Resolution.x, MySettings.Resolution.y);
        frame.add(new MainCanvas()); // Add the canvas to the frame
        frame.setVisible(true);
    }

    @Override
    public void run() {
        repaint();
    }
}