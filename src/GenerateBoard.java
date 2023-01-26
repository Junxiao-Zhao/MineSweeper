import java.util.HashMap;

// Generate mines and numbers
public class GenerateBoard {

    // Create the map
    // -1: mine; 0-8: number of mine in 3x3 grid
    private int[][] BOTTOM_MAP;

    // Cover
    // -1: uncovered; 0: covered; 1: flag correctly; 2: flag uncorrectly
    private int[][] TOP_MAP;

    // Record the mine location
    private HashMap<Integer, Boolean> mineLoc = new HashMap<>();

    // Basic Info
    private int[] basics;
    private int WIDTH, HEIGHT, NUM_MINE;

    // Tricks
    private int[] trick1 = { -1, 0, 1, 0, -1 };
    private int[] trick2 = { -1, -1, 1, 1, -1 };

    // Get and Set
    public int getBottom(int i, int j) {
        return BOTTOM_MAP[i][j];
    }

    public void setBottom(int i, int j, int val) {
        BOTTOM_MAP[i][j] = val;
    }

    public int getTop(int i, int j) {
        return TOP_MAP[i][j];
    }

    public void setTop(int i, int j, int val) {
        TOP_MAP[i][j] = val;
    }

    public HashMap<Integer, Boolean> getMinePos() {
        return mineLoc;
    }

    // randomly generate mines
    public void GenerateMine() {

        mineLoc.clear();

        basics = BasicComponents.getBasicInfo();
        WIDTH = basics[0];
        HEIGHT = basics[1];
        NUM_MINE = basics[2];

        BOTTOM_MAP = new int[WIDTH][HEIGHT];
        TOP_MAP = new int[WIDTH][HEIGHT];

        int i = 0;
        while (i < NUM_MINE) {

            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);

            // to avoid overlap
            if (mineLoc.getOrDefault(x * WIDTH + y, true)) {
                mineLoc.put(x * WIDTH + y, false);
                BOTTOM_MAP[x][y] = -1;
                i += 1;
            }
        }

        countMineAround();

        // Reset start time for each round
        BasicComponents.setStart(System.currentTimeMillis());
    }

    // calculate the number of mines in the 3x3 grid
    private void countMineAround() {

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                if (BOTTOM_MAP[i][j] == -1) {
                    continue;
                }

                int count = 0;
                for (int a = 0; a < 4; a++) {

                    // to visit neighbours on the same row and col
                    int x1 = i + trick1[a];
                    int y1 = j + trick1[a + 1];

                    if ((x1 >= 0) && (x1 < WIDTH) && (y1 >= 0) && (y1 < HEIGHT)
                            && (BOTTOM_MAP[x1][y1] == -1)) {
                        count++;
                    }

                    // on diagonals
                    int x2 = i + trick2[a];
                    int y2 = j + trick2[a + 1];

                    if ((x2 >= 0) && (x2 < WIDTH) && (y2 >= 0) && (y2 < HEIGHT)
                            && (BOTTOM_MAP[x2][y2] == -1)) {
                        count++;
                    }
                }

                BOTTOM_MAP[i][j] = count;
            }
        }
    }
}
