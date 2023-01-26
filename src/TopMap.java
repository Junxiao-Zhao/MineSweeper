import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;

/*
 * Draw the top of the map
 * Including flags, wrong flags and covers
 */
public class TopMap {

    private int mouseX, mouseY, stateX, stateY;
    private Queue<int[]> emptyGrids;
    private HashMap<Integer, Boolean> flagLoc;
    private GenerateBoard board;
    private int[] trick1 = { -1, 0, 1, 0, -1 };
    private int[] trick2 = { -1, -1, 1, 1, -1 };
    private int[] basics;
    private int WIDTH, HEIGHT, NUM_MINE, MARGIN, GRID_LENGTH;

    public TopMap(GenerateBoard b) {
        board = b;
        flagLoc = new HashMap<>();
        updateBasics();
    }

    private void updateBasics() {
        basics = BasicComponents.getBasicInfo();
        WIDTH = basics[0];
        HEIGHT = basics[1];
        NUM_MINE = basics[2];
        MARGIN = BasicComponents.MARGIN;
        GRID_LENGTH = BasicComponents.GRID_LENGTH;
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
            board.setTop(i, j, -1);

            // Do not uncover neighbours of nonempty grid
            if (board.getBottom(i, j) != 0)
                continue;

            for (int a = 0; a < 4; a++) {

                // to visit neighbours on the same row and col
                int x1 = i + trick1[a];
                int y1 = j + trick1[a + 1];

                // Not mine and covered
                if ((x1 >= 0) && (x1 < WIDTH) && (y1 >= 0) && (y1 < HEIGHT)
                        && (board.getTop(x1, y1) == 0) && (board.getBottom(x1, y1) != -1)) {
                    emptyGrids.add(new int[] { x1, y1 });
                }

                // on diagonals
                int x2 = i + trick2[a];
                int y2 = j + trick2[a + 1];

                // Not mine and covered
                if ((x2 >= 0) && (x2 < WIDTH) && (y2 >= 0) && (y2 < HEIGHT)
                        && (board.getTop(x2, y2) == 0) && (board.getBottom(x2, y2) != -1)) {
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
            int x1 = i + trick1[a];
            int y1 = j + trick1[a + 1];

            // Count flags
            if ((x1 >= 0) && (x1 < WIDTH) && (y1 >= 0) && (y1 < HEIGHT)
                    && (board.getTop(x1, y1) == 1)) {
                countFlag++;
            }

            // along diagonals
            int x2 = i + trick2[a];
            int y2 = j + trick2[a + 1];

            // Count flags
            if ((x2 >= 0) && (x2 < WIDTH) && (y2 >= 0) && (y2 < HEIGHT)
                    && (board.getTop(x2, y2) == 1)) {
                countFlag++;
            }
        }

        // Num of flags match num of mines, uncover surrounding grids
        if (countFlag == board.getBottom(i, j)) {
            for (int a = 0; a < 4; a++) {

                // to visit neighbours on the same row and col
                int x1 = i + trick1[a];
                int y1 = j + trick1[a + 1];

                // uncover
                if ((x1 >= 0) && (x1 < WIDTH) && (y1 >= 0) && (y1 < HEIGHT)
                        && (board.getTop(x1, y1) == 0)) {
                    bfsOpen(new int[] { x1, y1 });
                }

                // along diagonals
                int x2 = i + trick2[a];
                int y2 = j + trick2[a + 1];

                // uncover
                if ((x2 >= 0) && (x2 < WIDTH) && (y2 >= 0) && (y2 < HEIGHT)
                        && (board.getTop(x2, y2) == 0)) {
                    bfsOpen(new int[] { x2, y2 });
                }
            }
        }
    }

    // Determine if successed
    // true: success; false: not success
    private Boolean success() {
        int x, y;

        if (flagLoc.size() == NUM_MINE) {
            for (Integer val : flagLoc.keySet()) {
                y = val % WIDTH;
                x = (val - y) / WIDTH;

                // flag not mine
                if (board.getMinePos().getOrDefault(x * WIDTH + y, true)) {
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

        for (Integer val : board.getMinePos().keySet()) {
            y = val % WIDTH;
            x = (val - y) / WIDTH;

            // uncovered
            if (board.getTop(x, y) == -1) {
                return true;
            }
        }

        return false;
    }

    // Show the final state
    // open all covered and mark wrong flags
    private void showFinal(Boolean fail) {
        int x, y;

        // Mark wrong flags
        if (fail) {
            for (Integer val : flagLoc.keySet()) {
                y = val % WIDTH;
                x = (val - y) / WIDTH;

                // flag not mine
                if (board.getBottom(x, y) != -1) {
                    board.setTop(x, y, 2);
                }
            }
        }

        // Uncover all grids
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (board.getTop(i, j) == 0) {
                    board.setTop(i, j, -1);
                }
            }
        }

    }

    // Reset the game after success or failure
    public void reset() {
        board.GenerateMine();
        flagLoc.clear();
        updateBasics();
    }

    // Update due to left or right click
    private void mouseAction() {

        // Covert the coordinates into grid position
        mouseX = ((BasicComponents.getMousePos(0) - MARGIN) / GRID_LENGTH);
        mouseY = ((BasicComponents.getMousePos(1) - 3 * MARGIN - 30) / GRID_LENGTH);

        // State image positon
        stateX = BasicComponents.getMousePos(0)
                - (((int) (WIDTH / 2)) * GRID_LENGTH + MARGIN);
        stateY = BasicComponents.getMousePos(1) - MARGIN - 30;

        // Take actions only when within the grid
        if (mouseX >= 0 && mouseX < WIDTH && mouseY >= 0 && mouseY < HEIGHT) {

            // Left click
            if (BasicComponents.getMouseClick(0)) {
                // Uncover itself and surroundings
                if (board.getTop(mouseX, mouseY) == 0) {
                    bfsOpen(new int[] { mouseX, mouseY });
                }
                BasicComponents.setMouseClick(0, false);
            }

            // Right click
            if (BasicComponents.getMouseClick(1)) {
                // Set flag
                if (board.getTop(mouseX, mouseY) == 0) {
                    board.setTop(mouseX, mouseY, 1);
                    flagLoc.put(mouseX * WIDTH + mouseY, true);
                }
                // Unset flag
                else if (board.getTop(mouseX, mouseY) == 1) {
                    board.setTop(mouseX, mouseY, 0);
                    flagLoc.remove(mouseX * WIDTH + mouseY);
                }
                // Uncover surroundings
                else if (board.getTop(mouseX, mouseY) == -1
                        && board.getBottom(mouseX, mouseY) != 0) {
                    numOpen(mouseX, mouseY);
                }
                BasicComponents.setMouseClick(1, false);
            }
        }
        // Reset the state
        else if (BasicComponents.getMouseClick(0) && stateX >= 0 && stateX < GRID_LENGTH
                && stateY >= 0 && stateY < GRID_LENGTH) {
            BasicComponents.setState(0);
            reset();
            BasicComponents.setMouseClick(0, false);
        }

    }

    // Draw the Cover, count remaining mines, and show time
    public void drawTopMap(Graphics graphics) {
        mouseAction();

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                // Don't draw on uncovered grid
                if (board.getTop(i, j) == -1)
                    continue;

                graphics.drawImage(BasicComponents.getCoverImage(board.getTop(i, j)),
                        MARGIN + i * GRID_LENGTH + 1,
                        3 * MARGIN + j *
                                GRID_LENGTH + 1,
                        GRID_LENGTH - 2, GRID_LENGTH - 2, null);
            }
        }

        // Set remaining mines number
        // Remaining mines = total mine - num of flags
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 30));
        graphics.drawString("" + (NUM_MINE - flagLoc.size()), MARGIN, 2 * MARGIN);

        // update time when on game
        if (BasicComponents.getState() == 0)
            BasicComponents.setEnd(System.currentTimeMillis());
        // Show time spent
        graphics.drawString("" + (BasicComponents.getEnd() - BasicComponents.getStart()) / 1000,
                GRID_LENGTH * WIDTH, 2 * MARGIN);

        // Failed
        if (fail()) {
            BasicComponents.setState(1);
            showFinal(true);
        }

        // Successed
        if (success()) {
            BasicComponents.setState(2);
            showFinal(false);
        }
    }
}
