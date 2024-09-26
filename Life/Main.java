package Life_v2;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class Main {

    JFrame jf = new JFrame(Const.Name);
    JPanel text_stuff = new JPanel();
    JPanel panel = new JPanel();
    JPanel infoSet = new JPanel();

    JLabel text = new JLabel();
    JButton Restart = new JButton("Restart retard");
    JButton Start = new JButton("Start fart");
    JButton Draw = new JButton("Draw hoe");

    Cell[][] cc = new Cell[Const.Rows][Const.Cols];
    public final static ArrayList<Cell> liveCells = new ArrayList<>();
    public static ArrayList<Cell> copyCells = new ArrayList<>();

    static boolean toDraw = true;

    JButton SpeedUp = new JButton("Speed Up");
    JButton SpeedDown = new JButton("Speed Down");
    JLabel gen = new JLabel();
    static int generation = 0;
    static int periodSize = 0;
    static int speed = 100;

    Timer time;

    Main() {
        time = new Timer(speed, e3 -> {
            if (!Game_Over()) {
                start();
                /*if(periodSize == liveCells.size()){
                    time.stop();
                    text.setText(Const.txt + ": Game Over!");
                }*/
                periodSize = liveCells.size();
            } else {
                text.setText(Const.txt + ": Game Over!");
            }

        });

        //Инициализация текстовых окон и игрового поля
        jf.setSize(Const.Width, Const.Height+100);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        //jf.setResizable(false);
        jf.setLayout(new BorderLayout());
        jf.setVisible(true);

        text.setFont(new Font("Impact", Font.PLAIN, 25));
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setText(Const.txt);
        text.setOpaque(true);
        text_stuff.setLayout(new GridLayout(1, 4));

        Restart.setBackground(Color.BLACK);
        Restart.setForeground(Color.WHITE);
        Restart.setFont(new Font("Impact", Font.PLAIN, 25));

        SpeedUp.setBackground(Color.BLACK);
        SpeedUp.setForeground(Color.WHITE);
        SpeedUp.setFont(new Font("Impact", Font.PLAIN, 25));

        SpeedDown.setBackground(Color.BLACK);
        SpeedDown.setForeground(Color.WHITE);
        SpeedDown.setFont(new Font("Impact", Font.PLAIN, 25));

        gen.setFont(new Font("Impact", Font.PLAIN, 25));
        gen.setHorizontalAlignment(JLabel.CENTER);
        gen.setText(Const.info);
        gen.setOpaque(true);

        Start.setBackground(Color.BLACK);
        Start.setForeground(Color.WHITE);
        Start.setFont(new Font("Impact", Font.PLAIN, 25));
        Start.setEnabled(false);

        Draw.setBackground(Color.BLACK);
        Draw.setForeground(Color.WHITE);
        Draw.setFont(new Font("Impact", Font.PLAIN, 25));

        infoSet.setLayout(new GridLayout(1, 3));
        infoSet.add(gen);
        infoSet.add(SpeedUp);
        infoSet.add(SpeedDown);

        text_stuff.add(text);
        text_stuff.add(Restart);
        text_stuff.add(Start);
        text_stuff.add(Draw);
        jf.add(text_stuff, BorderLayout.NORTH);
        jf.add(infoSet, BorderLayout.SOUTH);
        panel.setLayout(new GridLayout(Const.Rows, Const.Cols, 0, 0));

        jf.add(panel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Увеличение/уменьшение скорости генерации
        SpeedDown.addActionListener(e1 -> {
            time.setDelay(speed + 20);
            speed += 20;
        });

        SpeedUp.addActionListener(e2 -> {
            if(time.getDelay() >= 20) {
                time.setDelay(speed - 20);
                speed -= 20;
            }
        });

        //Кнопка перезапуска
        Restart.addActionListener(e3 -> {
            //очистка панели клеток
            //обновление рисования
            time.stop();
            panel.removeAll();
            //panel.repaint();
            panel.revalidate();
            toDraw = true;
            text.setText(Const.txt);
            gen.setText(Const.info);
            Start.setEnabled(false);
            copyCells.clear();
            liveCells.clear();
            generation = 0;
            speed = 100;
            Const.randCellsAlive = new Random().nextInt(Const.Rows * Const.Cols / 5)
                    + Const.Rows * Const.Cols / 10;
            DrawLayout();
        });

        //Кнопка запуска
        Start.addActionListener(e4 -> {
            if (Start.isEnabled()) {
                time.start();
                Start.setEnabled(false);
                Draw.setEnabled(false);
                toDraw = false;
                disable();
            }
        });

        //рандомный рисунок
        Draw.addActionListener(e5 -> {
            if (Draw.isEnabled()) {
             /*JFrame randLife = new JFrame("How many are alive?");
                    randLife.setSize(300, 300);
                    randLife.setLayout(new BorderLayout());

                    JTextField alive = new JTextField();
                    alive.setSize(100, 100);

                    randLife.add(alive, BorderLayout.CENTER);
                    randLife.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    randLife.setVisible(true);*/
                randGen();
                text.setText(Const.txt + ":  " + liveCells.size());
            }
        });

        DrawLayout();
        jf.setVisible(true);
    }

    //"Смерть" клетки, если соседей НЕ 2 или НЕ 3
    void Die(Cell c) {
        liveCells.remove(c);
        c.setBackground(Const.NORM);
    }

    //Клетка становится "живой", если рядом 3 "живых" клетки
    void Born(Cell c) {
        liveCells.add(c);
        c.setBackground(Color.BLACK);
    }

    //смена состояниа клетки на противоположное (отрицание)
    void Inverse(Cell c){
        if (liveCells.contains(c)) {
            Die(c);
        } else {
            Born(c);
        }
    }

    //Проверка на "жизнь" клетки
    public int checkLife(int x, int y) {
        if (x < 0 || x >= Const.Rows || y < 0 || y >= Const.Cols) {
            return 0;
        }
        if (liveCells.contains(cc[x][y])) {
            return 1;
        }
        return 0;
    }

    //копия поля живых клеток
    public int copyCheckLife(int x, int y) {
        if (x < 0 || x >= Const.Rows || y < 0 || y >= Const.Cols) {
            return 0;
        }
        if (copyCells.contains(cc[x][y])) {
            return 1;
        }
        return 0;
    }

    //проверка жизни клетки
    public void check(int x, int y) {

        if (x < 0 || x >= Const.Rows || y < 0 || y >= Const.Cols) return;

        Cell c = cc[x][y];
        int count_of_cells = 0;

        count_of_cells += copyCheckLife(x - 1, y - 1);
        count_of_cells += copyCheckLife(x - 1, y + 1);
        count_of_cells += copyCheckLife(x - 1, y);
        count_of_cells += copyCheckLife(x + 1, y - 1);
        count_of_cells += copyCheckLife(x + 1, y + 1);
        count_of_cells += copyCheckLife(x + 1, y);
        count_of_cells += copyCheckLife(x, y - 1);
        count_of_cells += copyCheckLife(x, y + 1);

        //System.out.print(count_of_cells!=0 ? count_of_cells+"\n":"");
        if (count_of_cells == 3 && !copyCells.contains(c)) {
            Born(c);
        }else if ((count_of_cells != 2 && count_of_cells != 3) && copyCells.contains(c)) {
                Die(c);
            }
    }

    public void disable() {
        for (Cell[] c : cc) {
            for (Cell ll : c) {
                ll.setEnabled(false);
            }
        }
    }

    //Пользователь рисует изначальную схему, по которой прогоняется
    //алгоритм жизни
    public void DrawLayout() {
        for (int i = 0; i < Const.Rows; i++) {
            for (int j = 0; j < Const.Cols; j++) {
                Cell c = new Cell(j, i);
                cc[i][j] = c;
                c.setFocusable(false);
                c.setBackground(Const.NORM);
                c.setBorder(null);
                c.setMargin(new Insets(0, 0, 0, 0));
                c.setEnabled(true);
                c.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Cell c = (Cell) e.getSource();
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (c.isEnabled() && toDraw) {
                                if (c.getBackground().equals(Const.NORM)) {
                                    Born(c);
                                    text.setText(Const.txt + ":  " + liveCells.size());
                                    Start.setEnabled(true);
                                } else {
                                    Die(c);
                                    text.setText(Const.txt + ":  " + liveCells.size());
                                }
                            }
                        }
                    }
                });
                panel.add(c);
            }
        }
        Draw.setEnabled(true);
    }

    //Прекращение игры
    //1. На поле нет живых клеток
    //2. Периодическая конфигурация (сделать потом)
    public boolean Game_Over() {
        return liveCells.size() == 0;
    }

    //рандомная генерация "жизни"
    public void randGen() {
        Draw.setEnabled(false);
        for (int i = 0; i < Const.randCellsAlive; i++) {
            int randX = new Random().nextInt(Const.Rows);
            int randY = new Random().nextInt(Const.Cols);
            if(!liveCells.contains(cc[randX][randY])){Born(cc[randX][randY]);}
            else i--;
        }
        Start.setEnabled(true);
    }

    public void printArray(ArrayList<Cell> arr) {
        for (Cell cell : arr) {
            System.out.print("(" + cell.getX() + ", " + cell.getY() + "); ");
        }
    }

    //Основа генерации "жизни"
    public void start() {
        /*Cell Sergey = new Cell(13, 37);
        Sergey.Born();*/
        copyCells = new ArrayList<>(liveCells);
        for (int j = 0; j < Const.Rows; j++) {
            for (int i = 0; i < Const.Cols; i++) {
                check(j, i);
                gen.setText(Const.info + ": " + generation);
                text.setText(Const.txt + ": " + liveCells.size());
            }
        }
        generation++;
    }
}