import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MineSweeper {

    public static void main(String[] args) throws Exception {

        // Create the frame
        JFrame frame = new JFrame("Mine Sweeper");

        // Create the panel
        JPanel panel = new JPanel() {

            BottomMap bottomMap = new BottomMap();
            TopMap topMap = new TopMap(bottomMap.getBoard());

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

        frame.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // left click
                if (e.getButton() == 1) {
                    BasicComponents.mousePos[0] = e.getX();
                    BasicComponents.mousePos[1] = e.getY();
                    BasicComponents.mouseClick[0] = true;
                }

                if (e.getButton() == 3) {
                    BasicComponents.mousePos[0] = e.getX();
                    BasicComponents.mousePos[1] = e.getY();
                    BasicComponents.mouseClick[1] = true;
                }
            }
        });

        // Refresh the window
        while (true) {
            frame.repaint();
            Thread.sleep(50);
        }

    }
}
