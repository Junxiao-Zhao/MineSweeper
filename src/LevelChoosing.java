import java.awt.*;

// Choose the level
public class LevelChoosing {

    private int mouseX, mouseY;
    private String[] levelsText = { "Easy~", "Medium?", "Hard!" };

    // Whether selected level
    public boolean selected() {
        mouseX = BasicComponents.getMousePos(0);
        if (mouseX >= 100 && mouseX < 400) {
            mouseY = BasicComponents.getMousePos(1);

            // easy
            if (mouseY >= 50 && mouseY < 150) {
                BasicComponents.setLevel(0);
                BasicComponents.setState(0);
                return true;
            }
            // medium
            else if (mouseY >= 200 && mouseY < 300) {
                BasicComponents.setLevel(1);
                BasicComponents.setState(0);
                return true;
            }
            // hard
            else if (mouseY >= 350 && mouseY < 450) {
                BasicComponents.setLevel(2);
                BasicComponents.setState(0);
                return true;
            }
        }

        return false;
    }

    public void setGame() {
        switch (BasicComponents.getLevel()) {
            case 0:
                BasicComponents.setBasicInfo(9, 9, 10);
                break;
            case 1:
                BasicComponents.setBasicInfo(16, 16, 40);
                break;
            case 2:
                BasicComponents.setBasicInfo(30, 16, 99);
                break;
        }
    }

    // Display level selection
    public void drawLevel(Graphics graphics) {

        graphics.setColor(Color.black);
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 30));

        for (int i = 0; i < 3; i++) {
            graphics.drawRect(100, 50 + 150 * i, 300, 100);
            graphics.drawString(levelsText[i], 220, 100 + 150 * i);
        }

    }
}
