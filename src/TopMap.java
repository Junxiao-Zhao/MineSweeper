import java.awt.*;

/*
 * Draw the top of the map
 * Including flags, wrong flags and covers
 * 
 */
public class TopMap {

    public void drawTopMap(Graphics graphics) {
        int WIDTH = BasicComponents.WIDTH;
        int HEIGHT = BasicComponents.HEIGHT;
        int MARGIN = BasicComponents.MARGIN;
        int GRID_LENGTH = BasicComponents.GRID_LENGTH;

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (BasicComponents.TOP_MAP[i][j] == -1)
                    continue;

                graphics.drawImage(BasicComponents.coverImages[BasicComponents.TOP_MAP[i][j]],
                        MARGIN + i * GRID_LENGTH + 1,
                        3 * MARGIN + j *
                                GRID_LENGTH + 1,
                        GRID_LENGTH - 2, GRID_LENGTH - 2, null);
            }
        }
    }
}
