/*
 * REFERENCE:
 * 
 * Number images and Flag image are from https://commons.wikimedia.org/w/index.php?search=File%3AMinesweeper&title=Special:MediaSearch&go=Go&type=image
 * Mine image is from https://apprecs.com/ios/451931111/minesweeper-go
 * Wrong Flag image is from https://www.reddit.com/r/Minesweeper/comments/b5nnld/be_prepared_for_the_number_of_posts_in_this_sub/
 * OnGame image is from https://zhidao.baidu.com/question/456361406224349925.html
 * Fail image is from https://en.emblemsbf.com/emblem-70529.html
 * Success image is from https://www.zcool.com.cn/work/ZMjIwOTkzODA=.html
 */

import java.awt.*;

// Basic Components of the Game
public class BasicComponents {

    // Map attributes
    private static int WIDTH = 11;
    private static int HEIGHT = 11;
    private static int NUM_MINE = 15;
    final static int MARGIN = 45;
    final static int GRID_LENGTH = 50;

    // Images
    // Bottom images
    final private static Image mineImage = Toolkit.getDefaultToolkit().getImage("imgs/mine.jpg");
    final private static Image[] numImages = new Image[9];
    static {
        for (Integer i = 0; i < 9; i++) {
            numImages[i] = Toolkit.getDefaultToolkit().getImage(String.format("imgs/%s.png", i.toString()));
        }
    }
    // Cover images
    final private static Image blankImage = Toolkit.getDefaultToolkit().getImage("imgs/blank.png");
    final private static Image flagImage = Toolkit.getDefaultToolkit().getImage("imgs/flag.png");
    final private static Image wrongFlagImage = Toolkit.getDefaultToolkit().getImage("imgs/wrongflag.png");
    final private static Image[] coverImages = { blankImage, flagImage, wrongFlagImage };
    // State images
    final private static Image onGame = Toolkit.getDefaultToolkit().getImage("imgs/smile.png");
    final private static Image fail = Toolkit.getDefaultToolkit().getImage("imgs/fail.jpg");
    final private static Image success = Toolkit.getDefaultToolkit().getImage("imgs/success.png");
    final private static Image[] stateImages = { onGame, fail, success };

    // Mouse attributes
    private static int[] mousePos = new int[2];
    private static Boolean[] mouseClick = { false, false };

    // Game State
    // 0: on game; 1: fail; 2: success; 3: level choosing
    private static int state = 3;

    // Game begin (after selection)
    private static boolean begin = false;

    // Game level
    // 0: easy; 1: medium; 2: hard
    private static int level;

    // Game Time
    private static long START_TIME, END_TIME;

    // Getters
    public static int[] getBasicInfo() {
        return new int[] { WIDTH, HEIGHT, NUM_MINE };
    }

    public static Image getMineImage() {
        return mineImage;
    }

    public static Image getNumImage(int i) {
        return numImages[i];
    }

    public static Image getCoverImage(int i) {
        return coverImages[i];
    }

    public static Image getStateImage(int i) {
        return stateImages[i];
    }

    public static int getMousePos(int i) {
        return mousePos[i];
    }

    public static Boolean getMouseClick(int i) {
        return mouseClick[i];
    }

    public static int getState() {
        return state;
    }

    public static boolean getBegin() {
        return begin;
    }

    public static int getLevel() {
        return level;
    }

    public static long getStart() {
        return START_TIME;
    }

    public static long getEnd() {
        return END_TIME;
    }

    // Setters
    public static void setBasicInfo(int width, int height, int num) {
        WIDTH = width;
        HEIGHT = height;
        NUM_MINE = num;
    }

    public static void setMousePos(int x, int y) {
        mousePos[0] = x;
        mousePos[1] = y;
    }

    public static void setMouseClick(int i, Boolean state) {
        mouseClick[i] = state;
    }

    public static void setState(int s) {
        state = s;
    }

    public static void setBegin(boolean s) {
        begin = s;
    }

    public static void setLevel(int l) {
        level = l;
    }

    public static void setStart(long s) {
        START_TIME = s;
    }

    public static void setEnd(long e) {
        END_TIME = e;
    }

}
