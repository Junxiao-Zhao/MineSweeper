## An imitation of the Windows built-in Minesweeper game

---

### Difficulty Level
- Esay: (9x9 grids with 10 mines)
- Medium: (13x13 grids with 30 mines)
- Hard: (13x26 grids with 50 mines)

---

### General Rules
- Reset the game by **left clicking** the face at the top of the window
- The number on the left top indicates the number of mines left (#mines - #flags)
- The number on the right top records the time
- Uncover the unit grid by **left clicking**
  - If it's empty, automatically uncover the surrounding grids until reach non-empty grids or the boundaries
  - If it's a number, do nothing
  - If it's a mine, game over, uncover all the grids, and mark wrong flags (flags on non-mine grid) if exist
- Set and unset flags on covered grids by **right clicking**
- Uncover the grids surrounding the uncovered number grid by **right clicking**
  - Only works when the number of flags around the number grid matches its number (game over if set wrong flags)
- Success when all mines are correctly marked by flags, uncover all grids

---

### References
  - Number images and Flag image are from https://commons.wikimedia.org/w/index.php?search=File%3AMinesweeper&title=Special:MediaSearch&go=Go&type=image   
  - Mine image is from https://apprecs.com/ios/451931111/minesweeper-go   
  - Wrong Flag image is from https://www.reddit.com/r/Minesweeper/comments/b5nnld/be_prepared_for_the_number_of_posts_in_this_sub/
  - OnGame image is from https://zhidao.baidu.com/question/456361406224349925.html
  - Fail image is from https://en.emblemsbf.com/emblem-70529.html
  - Success image is from https://www.zcool.com.cn/work/ZMjIwOTkzODA=.html

---

## <center>***Wish you enjoy!***</center>
