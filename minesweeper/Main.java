package Minesweeper;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

//Главный класс - игра "Сапёр"
public class Main{



    /*добавить уровни сложности
    * 1 - 8*8
    * 2 - 12*12
    * 3 - 15*15
    *прописать формулу количества мин
    * */
    static int Tile_size = 70;
    static int columns = 8;
    static int rows = 8;
    static String headline = "Minesweeper";
    static int count_of_mines = 10;
    static int count_of_flags = count_of_mines;
    static int mouseClicks = 1;
    static boolean flags_on_mines = true;

    JFrame jf = new JFrame(headline);
    JPanel panel = new JPanel();
    JPanel boardPanel = new JPanel();
    JLabel text = new JLabel();
    JButton Restart = new JButton("Restart retard");

    //список мин и клеток поля
    ArrayList<MineTile> mt;
    MineTile[][] bb = new MineTile[rows][columns];

    //Audio aud = new Audio();

    Main(){
        //окно приложения +лейблы
        //добавить кнопку рестарта
        jf.setSize(Tile_size * columns, Tile_size * rows + 100);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);
        jf.setLayout(new BorderLayout());

        text.setFont(new Font("Impact", Font.PLAIN, 25));
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setText("Mines: " + count_of_mines);
        text.setOpaque(true);

        panel.setLayout(new GridLayout(1, 2));
        panel.add(text);
        panel.add(Restart);
        jf.add(panel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(rows, columns));
        jf.add(boardPanel);

        Restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JButton r = (JButton) e.getSource();
                if(e.getButton() == MouseEvent.BUTTON1){
                    boardPanel.removeAll();
                    boardPanel.revalidate();
                    boardPanel.repaint();

                    restart();
                    Mines();
                }
            }
        });
        restart();
        Mines();
        jf.setVisible(true);
    }

    //количество мин вокруг открытой клетки +первый клик
    public void Check(int x, int y){
        if(x < 0 || x >= rows || y < 0 || y >= columns){
            return;
        }

        MineTile til = bb[x][y];
        if(!til.isEnabled()) return;

        til.setEnabled(false);
        int count = 0;
        count += minesAround(x - 1, y - 1);
        count += minesAround(x - 1, y + 1);
        count += minesAround(x - 1, y);
        count += minesAround(x + 1 , y - 1);
        count += minesAround(x + 1, y + 1);
        count += minesAround(x + 1, y);
        count += minesAround(x, y - 1);
        count += minesAround(x, y + 1);

        if(count > 0){
            til.setText(String.valueOf(count));
        }else{
            til.setText("");
            Check(x - 1, y - 1);
            Check(x - 1, y + 1);
            Check(x - 1, y);
            Check(x + 1 , y - 1);
            Check(x + 1, y + 1);
            Check(x + 1, y);
            Check(x, y - 1);
            Check(x, y + 1);
        }
    }

    //раскрытие мин, конец игры
    public void Reveal(){
        MineTile t;
        for (int i = 0; i < mt.size(); i++) {
            t = mt.get(i);
            t.setText("💣");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                t = bb[i][j];
                t.setEnabled(false);
            }
        }
        return;
    }

    //расстановка мин
    public void Mines(){
        mt = new ArrayList<>();

        for (int i = 0; i < count_of_mines; i++) {
            int rand = new Random().nextInt(rows);
            int ran = new Random().nextInt(columns);
            if(!mt.contains(bb[rand][ran])) mt.add(bb[rand][ran]);
            else i--;
        }

    }

    public void restart(){
        /*System.out.println("FUCK YOU");
        Audio.playSound(Audio.METAL_PIPE);*/
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //расстановка тайлов на поле
                MineTile t = new MineTile(i, j);
                bb[i][j] = t;
                t.setFocusable(false);
                t.setMargin(new Insets(0,0,0,0));
                t.setFont(new Font("Arial Uniscode MS", Font.PLAIN, 45));
                t.setText("");
                /* клики мышью
                 *  лкм - раскрытие
                 *  пкм - установка флага
                 * */
                t.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        MineTile t = (MineTile) e.getSource();

                        //левая кнопка мыши
                        if(e.getButton() == MouseEvent.BUTTON1){
                            if(mouseClicks == 1){
                                Check(t.getR(), t.getC());
                            }else{
                                if (t.getText() == "") {
                                    if (mt.contains(t)) {
                                        Reveal();
                                        text.setText("Game Over!");
                                        Audio.playRandomSound_GameOver();
                                    } else {
                                        Check(t.getR(), t.getC());
                                    }
                                }
                            }
                            mouseClicks++;
                        }
                        //правая кнопка мыши
                        if(e.getButton() == MouseEvent.BUTTON3 && t.isEnabled()){
                            if(count_of_flags >= 0) {
                                if (t.getText() == "") {
                                    t.setText("✡");
                                    count_of_flags--;
                                } else if (t.getText() == "✡") {
                                    t.setText("");
                                    count_of_flags++;
                                }
                                if(count_of_flags == 0){
                                    for (int k = 0; k < rows; k++){
                                        for (int l = 0; l < columns; l++) {
                                            if(!mt.contains(bb[k][l]) && bb[k][l].getText() == "✡") flags_on_mines = false;//соответствие каждой клетки с флагом с каждой клеткой с миной
                                        }
                                    }
                                    Reveal();
                                    if(flags_on_mines){
                                        text.setText("You win!");
                                        Audio.playRandomSound_WinGame();

                                    }else{
                                        text.setText("Game Over!");
                                        Audio.playRandomSound_GameOver();
                                    }
                                    return;
                                    //добавить отображение "неправильных" флагов
                                    //убрать ограничение на флаги
                                }
                                text.setText("Mines: " + (count_of_flags));
                            }
                        }
                    }
                });
                boardPanel.add(t);
            }
        }
    }

    //проверка мин вокруг клетки
    public int minesAround(int x, int y){
        if(x < 0 || x >= rows || y < 0 || y >= columns){
            return 0;
        }
        if(mt.contains(bb[x][y])){
            return 1;
        }
        return 0;
    }

    /*public void mineTilesCoordinates(ArrayList<MineTile> mm){
        for (int i = 0; i < mm.size(); i++) {
            System.out.println("(" + mm.get(i).getR() + ", " + mm.get(i).getC() + ")");
        }
    }*/
}