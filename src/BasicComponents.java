/*
 * Number images and Flag image are from https://commons.wikimedia.org/w/index.php?search=File%3AMinesweeper&title=Special:MediaSearch&go=Go&type=image
 * Mine image is from https://apprecs.com/ios/451931111/minesweeper-go
 * Wrong Flag image is from https://en.emblemsbf.com/emblem-70529.html
 */

import java.awt.*;

// Basic Components of the Game
public class BasicComponents {

    static int WIDTH = 11;
    static int HEIGHT = 11;
    static int MARGIN = 45;
    static int GRID_LENGTH = 50;
    static int NUM_MINE = 5;

    // Create the map
    // -1: mine; 0-8: number of mine in 3x3 grid
    static int[][] BOTTOM_MAP = new int[WIDTH][HEIGHT];

    // Cover
    // -1: uncovered; 0: covered; 1: flag correctly; 2: flag uncorrectly
    static int[][] TOP_MAP = new int[WIDTH][HEIGHT];

    // Images
    static Image[] numImages = new Image[9];
    {
        for (Integer i = 0; i < 9; i++) {
            numImages[i] = Toolkit.getDefaultToolkit().getImage(String.format("imgs/%s.png", i.toString()));
        }
    }
    static Image mineImage = Toolkit.getDefaultToolkit().getImage("imgs/mine.jpg");
    static Image blankImage = Toolkit.getDefaultToolkit().getImage("imgs/blank.png");
    static Image flagImage = Toolkit.getDefaultToolkit().getImage("imgs/flag.png");
    static Image wrongFlagImage = Toolkit.getDefaultToolkit().getImage("imgs/wrongflag.jpg");
    static Image[] coverImages = { blankImage, flagImage, wrongFlagImage };

}
