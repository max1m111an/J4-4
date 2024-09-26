package Life_v2;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton{
    private int x, y;
    private JPanel panel = null;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Cell(){
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if(x >= 0) {
            this.x = x;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if(y >= 0) {
            this.y = y;
        }
    }
}
