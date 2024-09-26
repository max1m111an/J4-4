package Life_v2;

import java.awt.*;
import java.util.Random;

public class Const {
    public static final String info = "Generation Z";
    public static final String Name = "Game of Life";
    public static final String txt = "\\__1984__/";
    public static final int Cell_Size = 10;
    public static final int Cols = 50; //minimum size
    public static final int Rows = Cols - 10;
    public static final int Width = Cell_Size * Cols;
    public static final int Height = Cell_Size * Rows;
    public static int randCellsAlive = new Random().nextInt(Rows*Cols/5) + Rows*Cols/5;
    public static final Color NORM = Color.WHITE;
}
