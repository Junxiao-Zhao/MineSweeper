import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;

/*
 * Draw the top of the map
 * Including flags, wrong flags and covers
 * 
 */
public class TopMap {

    private int mouseX, mouseY, stateX, stateY;
    private Queue<int[]> emptyGrids;
    private HashMap<int[], Boolean> flagLoc;

    public TopMap() {

        flagLoc = new HashMap<>();
    }

    // Open the cover using BFS
    private void bfsOpen(int[] pos) {
        emptyGrids = new LinkedList<>();
        emptyGrids.add(pos);

        while (emptyGrids.size() > 0) {
            int[] curPos = emptyGrids.poll();
            int i = curPos[0];
            int j = curPos[1];

            // Uncover current grid
            BasicComponents.TOP_MAP[i][j] = -1;

            // Do not uncover neighbours of nonempty grid
            if (BasicComponents.BOTTOM_MAP[i][j] != 0)
                continue;

            for (int a = 0; a < 4; a++) {

                // to visit neighbours on the same row and col
                int x1 = i + BasicComponents.trick1[a];
                int y1 = j + BasicComponents.trick1[a + 1];

                // Not mine and covered
                if ((x1 >= 0) && (x1 < BasicComponents.WIDTH) && (y1 >= 0) && (y1 < BasicComponents.HEIGHT)
                        && (BasicComponents.TOP_MAP[x1][y1] == 0) && (BasicComponents.BOTTOM_MAP[x1][y1] != -1)) {
                    emptyGrids.add(new int[] { x1, y1 });
                }

                // on diagonals
                int x2 = i + BasicComponents.trick2[a];
                int y2 = j + BasicComponents.trick2[a + 1];

                // Not mine and covered
                if ((x2 >= 0) && (x2 < BasicComponents.WIDTH) && (y2 >= 0) && (y2 < BasicComponents.HEIGHT)
                        && (BasicComponents.TOP_MAP[x2][y2] == 0) && (BasicComponents.BOTTOM_MAP[x2][y2] != -1)) {
                    emptyGrids.add(new int[] { x2, y2 });
                }
            }

        }
    }

    // Open the unflagged grid after right click the number grid
    private void numOpen(int i, int j) {
        int countFlag = 0;

        for (int a = 0; a < 4; a++) {

            // to visit neighbours on the same row and col
            int x1 = i + BasicComponents.trick1[a];
            int y1 = j + BasicComponents.trick1[a + 1];

            // Count flags
            if ((x1 >= 0) && (x1 < BasicComponents.WIDTH) && (y1 >= 0) && (y1 < BasicComponents.HEIGHT)
                    && (BasicComponents.TOP_MAP[x1][y1] == 1)) {
                countFlag++;
            }

            // along diagonals
            int x2 = i + BasicComponents.trick2[a];
            int y2 = j + BasicComponents.trick2[a + 1];

            // Count flags
            if ((x2 >= 0) && (x2 < BasicComponents.WIDTH) && (y2 >= 0) && (y2 < BasicComponents.HEIGHT)
                    && (BasicComponents.TOP_MAP[x2][y2] == 1)) {
                countFlag++;
            }
        }

        // Num of flags match num of mines, uncover surrounding grids
        if (countFlag == BasicComponents.BOTTOM_MAP[i][j]) {
            for (int a = 0; a < 4; a++) {

                // to visit neighbours on the same row and col
                int x1 = i + BasicComponents.trick1[a];
                int y1 = j + BasicComponents.trick1[a + 1];

                // uncover
                if ((x1 >= 0) && (x1 < BasicComponents.WIDTH) && (y1 >= 0) && (y1 < BasicComponents.HEIGHT)
                        && (BasicComponents.TOP_MAP[x1][y1] == 0)) {
                    bfsOpen(new int[] { x1, y1 });
                }

                // along diagonals
                int x2 = i + BasicComponents.trick2[a];
                int y2 = j + BasicComponents.trick2[a + 1];

                // uncover
                if ((x2 >= 0) && (x2 < BasicComponents.WIDTH) && (y2 >= 0) && (y2 < BasicComponents.HEIGHT)
                        && (BasicComponents.TOP_MAP[x2][y2] == 0)) {
                    bfsOpen(new int[] { x2, y2 });
                }
            }
        }
    }

    // Determine if successed
    // true: success; false: not success
    private Boolean success() {
        int x, y;

        if (flagLoc.size() == BasicComponents.NUM_MINE) {
            for (int[] loc : flagLoc.keySet()) {
                x = loc[0];
                y = loc[1];

                // flag not mine
                if (GenerateMine.mineLoc.getOrDefault(x * 11 + y, true)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    // Determine if failed
    // true: fail; false: not fail
    private Boolean fail() {
        int x, y;

        for (Integer val : GenerateMine.mineLoc.keySet()) {
            y = val % 11;
            x = (val - y) / 11;

            // uncovered
            if (BasicComponents.TOP_MAP[x][y] == -1) {
                return true;
            }
        }

        return false;
    }

    // Show mines without flags on it
    private void showUnflagged() {
        int x, y;
        for (Integer val : GenerateMine.mineLoc.keySet()) {
            y = val % 11;
            x = (val - y) / 11;

            // without flag on it
            if (BasicComponents.TOP_MAP[x][y] != 1) {
                BasicComponents.TOP_MAP[x][y] = -1;
            }
        }

        for (int[] loc : flagLoc.keySet()) {
            x = loc[0];
            y = loc[1];

            // flag not mine
            if (BasicComponents.BOTTOM_MAP[x][y] != -1) {
                BasicComponents.TOP_MAP[x][y] = 2;
            }
        }
    }

    // Reset the game after success or failure
    private void reset() {
        new GenerateMine();
        flagLoc.clear();
    }

    // Update due to left or right click
    private void mouseAction() {

        // Covert the coordinates into grid position
        mouseX = ((BasicComponents.mousePos[0] - BasicComponents.MARGIN) / BasicComponents.GRID_LENGTH);
        mouseY = ((BasicComponents.mousePos[1] - 3 * BasicComponents.MARGIN - 30) / BasicComponents.GRID_LENGTH);

        // State image positon
        stateX = BasicComponents.mousePos[0]
                - (((int) (BasicComponents.WIDTH / 2)) * BasicComponents.GRID_LENGTH + BasicComponents.MARGIN);
        stateY = BasicComponents.mousePos[1] - BasicComponents.MARGIN - 30;

        // Take actions only when within the grid
        if (mouseX >= 0 && mouseX < BasicComponents.WIDTH && mouseY >= 0 && mouseY < BasicComponents.HEIGHT) {

            // Left click
            if (BasicComponents.mouseClick[0]) {
                // Uncover itself and surroundings
                if (BasicComponents.TOP_MAP[mouseX][mouseY] == 0) {
                    bfsOpen(new int[] { mouseX, mouseY });
                }
                BasicComponents.mouseClick[0] = false;
            }

            // Right click
            if (BasicComponents.mouseClick[1]) {
                // Set flag
                if (BasicComponents.TOP_MAP[mouseX][mouseY] == 0) {
                    BasicComponents.TOP_MAP[mouseX][mouseY] = 1;
                    flagLoc.put(new int[] { mouseX, mouseY }, true);
                }
                // Unset flag
                else if (BasicComponents.TOP_MAP[mouseX][mouseY] == 1) {
                    BasicComponents.TOP_MAP[mouseX][mouseY] = 0;
                    flagLoc.remove(new int[] { mouseX, mouseY });
                }
                // Uncover surroundings
                else if (BasicComponents.TOP_MAP[mouseX][mouseY] == -1
                        && BasicComponents.BOTTOM_MAP[mouseX][mouseY] != 0) {
                    numOpen(mouseX, mouseY);
                }
                BasicComponents.mouseClick[1] = false;
            }
        }
        // Reset the state
        else if (BasicComponents.mouseClick[0] && stateX >= 0 && stateX < BasicComponents.GRID_LENGTH
                && stateY >= 0 && stateY < BasicComponents.GRID_LENGTH) {
            BasicComponents.state = 0;
            reset();
            BasicComponents.mouseClick[0] = false;
        }

    }

    // Draw the Cover
    public void drawTopMap(Graphics graphics) {
        mouseAction();

        int WIDTH = BasicComponents.WIDTH;
        int HEIGHT = BasicComponents.HEIGHT;
        int MARGIN = BasicComponents.MARGIN;
        int GRID_LENGTH = BasicComponents.GRID_LENGTH;

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                // Don't draw on uncovered grid
                if (BasicComponents.TOP_MAP[i][j] == -1)
                    continue;

                graphics.drawImage(BasicComponents.coverImages[BasicComponents.TOP_MAP[i][j]],
                        MARGIN + i * GRID_LENGTH + 1,
                        3 * MARGIN + j *
                                GRID_LENGTH + 1,
                        GRID_LENGTH - 2, GRID_LENGTH - 2, null);
            }
        }

        if (fail()) {
            BasicComponents.state = 1;
            showUnflagged();
        }

        if (success()) {
            BasicComponents.state = 2;
        }
    }
}
