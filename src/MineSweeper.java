import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MineSweeper {

    static LevelChoosing levelchose = new LevelChoosing();
    static BottomMap bottomMap = new BottomMap();
    static TopMap topMap = new TopMap(bottomMap.getBoard());

    public static void main(String[] args) throws Exception {

        // Create the frame
        JFrame frame = new JFrame("Mine Sweeper");
        JPanel boardPanel = new JPanel() {

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

        // Set level choosing size
        boardPanel.setPreferredSize(new Dimension(500, 500));

        frame.add(boardPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Mouse Actions
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

            // switch to game mode window
            if (BasicComponents.getBegin()) {
                // set game state
                BasicComponents.setBegin(false);
                BasicComponents.setState(0);

                // remove level choosing panel
                topMap.reset();
                frame.remove(boardPanel);

                // set game panel size
                int[] basics = BasicComponents.getBasicInfo();
                int WIDTH, HEIGHT, MARGIN, GRID_LENGTH;
                WIDTH = basics[0];
                HEIGHT = basics[1];
                MARGIN = BasicComponents.MARGIN;
                GRID_LENGTH = BasicComponents.GRID_LENGTH;
                boardPanel.setPreferredSize(
                        new Dimension(GRID_LENGTH * WIDTH + MARGIN * 2, GRID_LENGTH * HEIGHT + MARGIN * 4));

                frame.add(boardPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
            }

            frame.repaint();
            Thread.sleep(50);
        }

    }
}
