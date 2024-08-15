package Minesweeper;

import javax.swing.*;

//класс кнопок-тайлов (клетки связаны внутри клеток)
class MineTile extends JButton {
    private int r, c;

    public MineTile(int r, int c) {
        this.r = r;
        this.c = c;
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
