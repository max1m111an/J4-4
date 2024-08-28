package Minesweeper;

import javax.swing.*;
import java.awt.*;

//класс кнопок-тайлов (клетки связаны внутри клеток)
class MineTile extends JButton {
    private int r, c;

    public MineTile(int r, int c) {
        /*this.setBackground(Color.BLACK);
        this.setBorderPainted(true);*/
        this.r = r;
        this.c = c;
    }

    public void pressed() {
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
    }

    public void not_pressed() {
        this.setBackground(Color.BLACK);
        this.setForeground(Color.WHITE);
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        if(r >= 0) {
            this.r = r;
        }
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        if(c >= 0) {
            this.c = c;
        }
    }
}
