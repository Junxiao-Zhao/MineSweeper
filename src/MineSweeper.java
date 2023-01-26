import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MineSweeper {

    static LevelChoosing levelchose = new LevelChoosing();
    static BottomMap bottomMap = new BottomMap();
    static TopMap topMap = new TopMap(bottomMap.getBoard());

    private static JPanel getPanel() {
        JPanel boardPanel = new JPanel() {

            int width = 500;
            int height = 500;
            {
                if (BasicComponents.getState() != 3) {
                    int[] basics = BasicComponents.getBasicInfo();
                    width = basics[0] * BasicComponents.GRID_LENGTH + 2 * BasicComponents.MARGIN;
                    height = basics[1] * BasicComponents.GRID_LENGTH + 4 * BasicComponents.MARGIN;
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }

            @Override
            public void paint(Graphics graphics) {

                if (BasicComponents.getState() == 3) {
                    levelchose.drawLevel(graphics);
                } else {
                    bottomMap.drawBottomMap(graphics);
                    topMap.drawTopMap(graphics);
                }
            }
        };

        return boardPanel;
    }

    public static void main(String[] args) throws Exception {

        // Create the frame
        JFrame frame = new JFrame("Mine Sweeper");
        JPanel boardPanel = getPanel();

        frame.add(boardPanel);
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
                    BasicComponents.setMousePos(e.getX(), e.getY());
                    BasicComponents.setMouseClick(0, true);

                    // level selected
                    if (BasicComponents.getState() == 3 && levelchose.selected()) {
                        BasicComponents.setBegin(true);
                        levelchose.setGame();
                        BasicComponents.setMouseClick(0, false);
                    }
                }

                // right click
                if (e.getButton() == 3) {
                    BasicComponents.setMousePos(e.getX(), e.getY());
                    BasicComponents.setMouseClick(1, true);
                }
            }
        });

        // Refresh the window
        while (true) {
            if (BasicComponents.getBegin()) {
                BasicComponents.setBegin(false);
                BasicComponents.setState(0);
                boardPanel = getPanel();
                topMap.reset();
            }
            frame.repaint();
            Thread.sleep(50);
        }

    }
}
