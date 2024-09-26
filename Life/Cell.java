package Life_v2;

import javax.swing.*;

public class Cell extends JButton{
    private int x, y;
    //public boolean nextBorn = false;

    public Cell(int x, int y){
        if(x >=0 && y >= 0) {
            this.x = x;
            this.y = y;
        }
    }

    public Cell(){
        this.x = 0;
        this.y = 0;
    }

    /*//"Смерть" клетки, если соседей НЕ 2 или НЕ 3
    void Die() {
        this.alive = false;
        this.setBackground(Color.WHITE);
    }

    //Клетка становится "живой", если рядом 3 "живых" клетки
    void Born() {
        this.alive = true;
        this.setBackground(Color.BLACK);
    }*/

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

    /*public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }*/
}
