package Minesweeper;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

//Главный класс - игра "Сапёр"
public class Main {

    /*добавить уровни сложности
     * 1 - 8*8
     * 2 - 12*12
     * 3 - 15*15
     *прописать формулу количества мин
     * */
    final static short Tile_size = 70;
    final static String headline = "Minesweeper";

    static short columns = 8;
    static short rows = columns; //squared
    static short count_of_mines = 10;

    static short count_of_flags = count_of_mines;
    static short mouseClicks = 1;
    static boolean flags_on_mines = true;

    JFrame jf = new JFrame(headline);
    JPanel panel = new JPanel();
    JPanel boardPanel = new JPanel();
    JLabel text = new JLabel();
    JButton Restart = new JButton("Restart retard");

    JPanel difficulties = new JPanel();
    JLabel txt = new JLabel("Choose your difficult: ");
    JButton EASY = new JButton("Доминник Де Коко");
    JButton NORMAL = new JButton("Антонио Маргаретттиии");
    JButton HARD = new JButton("Энцо Горломи");
    static boolean your_choice_is_made = false;

    //список мин и клеток поля
    ArrayList<MineTile> mt;
    MineTile[][] bb = new MineTile[rows][columns];

    Audio aud_WinGame = new Audio(true);
    Audio aud_GameOver = new Audio(false);

    Main() {
        //окно приложения +лейблы
        jf.setSize(Tile_size * columns, Tile_size * rows + 100);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);
        jf.setLayout(new BorderLayout());

        //режимы сложности
        /*EASY.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    columns = 8;
                    rows = 8;
                    count_of_mines = 10;
                    your_choice_is_made = true;
                    System.out.println("GO FUCK YOURSELF, KID");
                }
            }
        });

        NORMAL.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    columns = 12;
                    rows = 12;
                    count_of_mines = 17;
                    your_choice_is_made = true;
                    System.out.println("LIGMA BALLS IABB 420 MLG");
                }
            }
        });

        HARD.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    columns = 15;
                    rows = 15;
                    count_of_mines = 20;
                    your_choice_is_made = true;
                    System.out.println("LATEX ENTHUSIAST");
                }
            }
        });

        /*HARD.addActionListener(e ->{
            columns = 15;
            rows = 15;
            count_of_mines = 20;
            your_choice_is_made = true;
            System.out.println("LATEX ENTHUSIAST");
        });*/

        /*txt.setFont(new Font("Impact", Font.PLAIN, 25));
        difficulties.add(txt);
        difficulties.add(EASY);
        difficulties.add(NORMAL);
        difficulties.add(HARD);
        jf.add(difficulties);*/

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

        //иницилизация рестарта
        Restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //очистка панели тайлов
                    boardPanel.removeAll();
                    boardPanel.revalidate();
                    boardPanel.repaint();

                    //обновление переменных
                    count_of_flags = count_of_mines;
                    mouseClicks = 1;
                    flags_on_mines = true;
                    text.setText("Mines: " + (count_of_flags));

                    restart();
                }
            }
        });
        restart();
        jf.setVisible(true);
    }

    public void restart() {
        /*System.out.println("FUCK YOU");
        Audio.playSound(Audio.METAL_PIPE);*/
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //расстановка тайлов на поле
                MineTile t = new MineTile(i, j);
                bb[i][j] = t;
                t.setFocusable(false);
                t.setMargin(new Insets(0, 0, 0, 0));
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
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            //System.out.println("("+t.getR() + ", " + t.getC()+")");
                            //первый клик
                            if (mouseClicks == 1) {
                                if(mt.contains(t)){
                                    //printArrayMines(mt);
                                    mt.remove(t);
                                    //printArrayMines(mt);
                                    //перестановка мины, если та была на первом клике
                                    while(true) {
                                        int randR = new Random().nextInt(rows);
                                        int randC = new Random().nextInt(columns);
                                        int x = t.getR();
                                        int y = t.getC();

                                        //проверка перестановки
                                        //НЕ на мину
                                        //НЕ рядом с первым кликом
                                        if(     !mt.contains(bb[randR][randC])
                                                //&& !(randR < 0 || randR >= rows || randC < 0 || randC >= columns)
                                                && randR != x && randC != y
                                                && randR != x - 1 && randC != y - 1
                                                && randR != x + 1 && randC != y + 1
                                        ){
                                            MineTile mmt = bb[randR][randC];
                                            mt.add(mmt);
                                            /*printArrayMines(mt);
                                            System.out.println("MINA("+randR + ", " + randC+")");*/
                                            break;
                                        }
                                    }
                                }
                                Check(t.getR(), t.getC());
                            } else {
                                if (t.getText() == "") {
                                    if (mt.contains(t)) {
                                        Reveal();
                                        text.setText("Game Over!");
                                        //new Thread(aud_GameOver).start();
                                        Audio.playRandomSound_GameOver();
                                    } else {
                                        Check(t.getR(), t.getC());
                                        //РОМАН ВКЛЮЧАЙТЕСЬ В ПРОЦЕСС ПОМОЩИ ПОЖАЛУЙСТА
                                    }
                                }
                            }
                            mouseClicks++;
                        }
                        //правая кнопка мыши
                        if (e.getButton() == MouseEvent.BUTTON3 && t.isEnabled()) {
                            if (count_of_flags >= 0) {
                                if (t.getText() == "") {
                                    t.setText("✡");
                                    count_of_flags--;
                                } else if (t.getText() == "✡") {
                                    t.setText("");
                                    //☢☢☢☢☢
                                    count_of_flags++;
                                }
                                //если потратил все флаги то проиграл (хардкор)
                                if (count_of_flags == 0) {
                                    //оптимизировать в один цикл (while)
                                    for (int k = 0; k < rows; k++) {
                                        for (int l = 0; l < columns; l++) {
                                            //соответствие каждой клетки с флагом с каждой клеткой с миной
                                            if (!mt.contains(bb[k][l]) && bb[k][l].getText() == "✡")
                                                flags_on_mines = false;
                                        }
                                    }
                                    Reveal();
                                    if (flags_on_mines) {
                                        text.setText("You win!");
                                        //new Thread(aud_WinGame).start();
                                        Audio.playRandomSound_WinGame();

                                    } else {
                                        text.setText("Game Over!");
                                        //new Thread(aud_GameOver).start();
                                        Audio.playRandomSound_GameOver();
                                    }
                                    return;
                                }
                                text.setText("Mines: " + (count_of_flags));
                            }
                        }
                    }
                });
                boardPanel.add(t);
            }
        }
        Mines();
    }

    //количество мин вокруг открытой клетки
    public void Check(int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= columns) {
            return;
        }

        MineTile til = bb[x][y];
        if (!til.isEnabled()) return;

        til.setEnabled(false);
        int count = 0;
        count += minesAround(x - 1, y - 1);
        count += minesAround(x - 1, y + 1);
        count += minesAround(x - 1, y);
        count += minesAround(x + 1, y - 1);
        count += minesAround(x + 1, y + 1);
        count += minesAround(x + 1, y);
        count += minesAround(x, y - 1);
        count += minesAround(x, y + 1);

        if (count > 0) {
            til.setText(String.valueOf(count));
        } else {
            til.setText("");
            Check(x - 1, y - 1);
            Check(x - 1, y + 1);
            Check(x - 1, y);
            Check(x + 1, y - 1);
            Check(x + 1, y + 1);
            Check(x + 1, y);
            Check(x, y - 1);
            Check(x, y + 1);
        }
    }

    //раскрытие мин, конец игры
    public void Reveal() {
        MineTile t;
        for (int i = 0; i < mt.size(); i++) {
            t = mt.get(i);
            if(!t.getText().equals("✡")) t.setText("💣");

        }
        checkMinesAndFlags();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                bb[i][j].setEnabled(false);
            }
        }
        return;
    }

    //проверка мин вокруг клетки
    public int minesAround(int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= columns) {
            return 0;
        }
        if (mt.contains(bb[x][y])) {
            return 1;
        }
        return 0;
    }

    //расстановка мин
    public void Mines() {
        mt = new ArrayList<>();

        for (int i = 0; i < count_of_mines; i++) {
            int randR = new Random().nextInt(rows);
            int randC = new Random().nextInt(columns);
            if (!mt.contains(bb[randR][randC])) mt.add(bb[randR][randC]);
            else i--;
        }
    }

    //проверка неправильных и правильных флагов
    public void checkMinesAndFlags(){
        String rightFlag = "🔯";
        String wrongFlag = "❌";

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(bb[i][j].getText().equals("✡")){
                    if(mt.contains(bb[i][j])) {

                        bb[i][j].setText(rightFlag);
                    }else bb[i][j].setText(wrongFlag);
                }
            }
        }
    }

    //кладбище методов
    //для проверки/тестирования кода
    /*public void printArrayMines(ArrayList<MineTile> mm){
        for (int i = 0; i < mm.size(); i++) {
            System.out.print("("+mm.get(i).getR() + ", " + mm.get(i).getC()+"); ");
        }
        System.out.println("");
    }*/

    /*public void mineTilesCoordinates(ArrayList<MineTile> mm){
        for (int i = 0; i < mm.size(); i++) {
            System.out.println("(" + mm.get(i).getR() + ", " + mm.get(i).getC() + ")");
        }
    }*/
}