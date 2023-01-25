import java.awt.*;

/*
 * Draw the bottom of the map
 * Including grids, mines, and numbers
 */
public class BottomMap {

    private GenerateBoard board;

    public BottomMap() {
        board = new GenerateBoard();
        board.GenerateMine();
    }

    public GenerateBoard getBoard() {
        return board;
    }

    public void drawBottomMap(Graphics graphics) {
        graphics.setColor(Color.gray);

        int WIDTH = BasicComponents.WIDTH;
        int HEIGHT = BasicComponents.HEIGHT;
        int MARGIN = BasicComponents.MARGIN;
        int GRID_LENGTH = BasicComponents.GRID_LENGTH;

        // Draw vetical lines
        for (int i = 0; i <= WIDTH; i++) {
            graphics.drawLine(MARGIN + GRID_LENGTH * i, 3 * MARGIN, MARGIN + GRID_LENGTH * i,
                    3 * MARGIN + HEIGHT * GRID_LENGTH);
        }

        // Draw horizontal lines
        for (int i = 0; i <= HEIGHT; i++) {
            graphics.drawLine(MARGIN, 3 * MARGIN + GRID_LENGTH * i, MARGIN + WIDTH * GRID_LENGTH,
                    3 * MARGIN + GRID_LENGTH * i);
        }

        // Place mines and numbers
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                Image curImage;
                if (board.getBottom(i, j) == -1) {
                    curImage = BasicComponents.mineImage;
                } else {
                    curImage = BasicComponents.numImages[board.getBottom(i, j)];
                }

                graphics.drawImage(curImage, MARGIN + i * GRID_LENGTH + 1, 3 * MARGIN + j *
                        GRID_LENGTH + 1,
                        GRID_LENGTH - 2, GRID_LENGTH - 2, null);
            }
        }

        // Draw the state image
        graphics.drawImage(BasicComponents.stateImages[BasicComponents.state],
                BasicComponents.MARGIN + ((int) (BasicComponents.WIDTH / 2)) * BasicComponents.GRID_LENGTH,
                BasicComponents.MARGIN, GRID_LENGTH, GRID_LENGTH, null);

    }
}
