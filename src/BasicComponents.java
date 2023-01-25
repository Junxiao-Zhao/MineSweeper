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
    static int WIDTH = 11;
    static int HEIGHT = 11;
    static int MARGIN = 45;
    static int GRID_LENGTH = 50;
    static int NUM_MINE = 15;

    // Create the map
    // -1: mine; 0-8: number of mine in 3x3 grid
    static int[][] BOTTOM_MAP;

    // Cover
    // -1: uncovered; 0: covered; 1: flag correctly; 2: flag uncorrectly
    static int[][] TOP_MAP;

    // Images
    // Bottom images
    static Image mineImage = Toolkit.getDefaultToolkit().getImage("imgs/mine.jpg");
    static Image[] numImages = new Image[9];
    static {
        for (Integer i = 0; i < 9; i++) {
            numImages[i] = Toolkit.getDefaultToolkit().getImage(String.format("imgs/%s.png", i.toString()));
        }
    }
    // Cover images
    static Image blankImage = Toolkit.getDefaultToolkit().getImage("imgs/blank.png");
    static Image flagImage = Toolkit.getDefaultToolkit().getImage("imgs/flag.png");
    static Image wrongFlagImage = Toolkit.getDefaultToolkit().getImage("imgs/wrongflag.png");
    static Image[] coverImages = { blankImage, flagImage, wrongFlagImage };
    // State images
    static Image onGame = Toolkit.getDefaultToolkit().getImage("imgs/smile.png");
    static Image fail = Toolkit.getDefaultToolkit().getImage("imgs/fail.jpg");
    static Image success = Toolkit.getDefaultToolkit().getImage("imgs/success.png");
    static Image[] stateImages = { onGame, fail, success };

    // Mouse attributes
    static int[] mousePos = new int[2];
    static Boolean[] mouseClick = { false, false };

    // Game State
    // 0: on game; 1: fail; 2: success
    static int state = 0;

    // Game Time
    static long START_TIME, END_TIME;

    // Others
    static int[] trick1 = { -1, 0, 1, 0, -1 };
    static int[] trick2 = { -1, -1, 1, 1, -1 };

}
