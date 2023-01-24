import javax.swing.*;
import java.awt.*;

public class MineSweeper {

    public static void main(String[] args) throws Exception {

        // Create the frame
        JFrame frame = new JFrame("Mine Sweeper");

        // Create the panel
        JPanel panel = new JPanel() {

            BottomMap bottomMap = new BottomMap();
            TopMap topMap = new TopMap();

            // Set panel size
            int width = BasicComponents.WIDTH * BasicComponents.GRID_LENGTH + 2 * BasicComponents.MARGIN;
            int height = BasicComponents.HEIGHT * BasicComponents.GRID_LENGTH + 4 * BasicComponents.MARGIN;

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }

            @Override
            public void paint(Graphics graphics) {
                bottomMap.drawBottomMap(graphics);
                topMap.drawTopMap(graphics);
            }
        };

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Refresh the window
        while (true) {
            frame.repaint();
            Thread.sleep(50);
        }

    }
}
