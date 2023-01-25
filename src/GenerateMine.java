import java.util.HashMap;

// Generate mines and numbers
public class GenerateMine {

    public static HashMap<Integer, Boolean> mineLoc = new HashMap<>();

    // randomly generate mines
    public GenerateMine() {

        mineLoc.clear();
        BasicComponents.BOTTOM_MAP = new int[BasicComponents.WIDTH][BasicComponents.HEIGHT];
        BasicComponents.TOP_MAP = new int[BasicComponents.WIDTH][BasicComponents.HEIGHT];

        int i = 0;
        while (i < BasicComponents.NUM_MINE) {

            int x = (int) (Math.random() * BasicComponents.WIDTH);
            int y = (int) (Math.random() * BasicComponents.HEIGHT);

            // to avoid overlap
            if (mineLoc.getOrDefault(x * 11 + y, true)) {
                mineLoc.put(x * 11 + y, false);
                BasicComponents.BOTTOM_MAP[x][y] = -1;
                i += 1;
            }
        }

        countMineAround();

        // Reset start time for each round
        BasicComponents.START_TIME = System.currentTimeMillis();
    }

    // calculate the number of mines in the 3x3 grid
    private void countMineAround() {

        for (int i = 0; i < BasicComponents.WIDTH; i++) {
            for (int j = 0; j < BasicComponents.HEIGHT; j++) {

                if (BasicComponents.BOTTOM_MAP[i][j] == -1) {
                    continue;
                }

                int count = 0;
                for (int a = 0; a < 4; a++) {

                    // to visit neighbours on the same row and col
                    int x1 = i + BasicComponents.trick1[a];
                    int y1 = j + BasicComponents.trick1[a + 1];

                    if ((x1 >= 0) && (x1 < BasicComponents.WIDTH) && (y1 >= 0) && (y1 < BasicComponents.HEIGHT)
                            && (BasicComponents.BOTTOM_MAP[x1][y1] == -1)) {
                        count++;
                    }

                    // on diagonals
                    int x2 = i + BasicComponents.trick2[a];
                    int y2 = j + BasicComponents.trick2[a + 1];

                    if ((x2 >= 0) && (x2 < BasicComponents.WIDTH) && (y2 >= 0) && (y2 < BasicComponents.HEIGHT)
                            && (BasicComponents.BOTTOM_MAP[x2][y2] == -1)) {
                        count++;
                    }
                }

                BasicComponents.BOTTOM_MAP[i][j] = count;
            }
        }
    }
}
