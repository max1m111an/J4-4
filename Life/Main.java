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

    JLabel text = new JLabel();
    JButton Restart = new JButton("Restart retard");
    JButton Start = new JButton("Start fart");
    JButton Draw = new JButton("Draw hoe");

    Cell[][] cc = new Cell[Const.Rows][Const.Cols];
    public static ArrayList<Cell> liveCells;

    static boolean toDraw = true;

    Timer time = new Timer(20, e -> {
        if (!Game_Over()) {
            start();
            panel.revalidate();
        } else {
            System.out.println("Game Over!");
            return;
        }
    });

    Main() {
        //Инициализация текстовых окон и игрового поля
        jf.setSize(Const.Width, Const.Height + 200);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        //jf.setResizable(false);
        jf.setLayout(new BorderLayout());
        jf.setVisible(true);

        text.setFont(new Font("Impact", Font.PLAIN, 25));
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setText(Const.txt);
        text.setOpaque(true);

        text_stuff.setLayout(new GridLayout(1, 3));

        Restart.setBackground(Color.BLACK);
        Restart.setForeground(Color.WHITE);
        Restart.setFont(new Font("Impact", Font.PLAIN, 25));

        Start.setBackground(Color.BLACK);
        Start.setForeground(Color.WHITE);
        Start.setFont(new Font("Impact", Font.PLAIN, 25));
        Start.setEnabled(false);

        Draw.setBackground(Color.BLACK);
        Draw.setForeground(Color.WHITE);
        Draw.setFont(new Font("Impact", Font.PLAIN, 25));

        text_stuff.add(text);
        text_stuff.add(Restart);
        text_stuff.add(Start);
        text_stuff.add(Draw);
        jf.add(text_stuff, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(Const.Rows, Const.Cols, 0, 0));

        jf.add(panel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createLineBorder(Color.RED));

        //Кнопка перезапуска
        Restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //очистка панели клеток
                    //обновление рисования
                    time.stop();
                    panel.removeAll();
                    //panel.repaint();
                    panel.revalidate();
                    toDraw = true;
                    text.setText(Const.txt);
                    Start.setEnabled(false);
                    Const.randCellsAlive = new Random().nextInt(Const.Rows * Const.Cols / 5) + Const.Rows * Const.Cols / 10;
                    DrawLayout();
                }
            }
        });

        //Кнопка запуска
        Start.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && Start.isEnabled()) {
                    time.start();
                    Start.setEnabled(false);
                    toDraw = false;
                    disable();
                }
            }
        });

        Draw.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && Draw.isEnabled()) {
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
            }
        });
        DrawLayout();
        jf.setVisible(true);
    }

    //"Смерть" клетки, если соседей НЕ 2 или НЕ 3
    void Die(Cell c) {
        liveCells.remove(c);
        c.setBackground(Color.WHITE);
    }

    //Клетка становится "живой", если рядом 3 "живых" клетки
    void Born(Cell c) {
        liveCells.add(c);
        c.setBackground(Color.BLACK);
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

    //проверка жизни клетки
    public void check(int x, int y) {
        if (x < 0 || x >= Const.Rows || y < 0 || y >= Const.Cols) return;

        Cell c = cc[x][y];
        int count_of_cells = 0;

        count_of_cells += checkLife(x - 1, y - 1);
        count_of_cells += checkLife(x - 1, y + 1);
        count_of_cells += checkLife(x - 1, y);
        count_of_cells += checkLife(x + 1, y - 1);
        count_of_cells += checkLife(x + 1, y + 1);
        count_of_cells += checkLife(x + 1, y);
        count_of_cells += checkLife(x, y - 1);
        count_of_cells += checkLife(x, y + 1);

        //System.out.print(count_of_cells!=0 ? count_of_cells+"\n":"");

        if ((count_of_cells != 2 && count_of_cells != 3) && liveCells.contains(c)) {
            Die(c);
        } else {
            if (count_of_cells == 3 && !liveCells.contains(c)) {
                Born(c);
            }
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
    //алогритм жизни
    public void DrawLayout() {
        liveCells = new ArrayList<>();
        for (int i = 0; i < Const.Rows; i++) {
            for (int j = 0; j < Const.Cols; j++) {
                Cell c = new Cell(j, i);
                cc[i][j] = c;
                c.setFocusable(false);
                c.setBackground(Color.WHITE);
                c.setBorder(null);
                c.setMargin(new Insets(0, 0, 0, 0));
                c.setEnabled(true);
                c.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Cell c = (Cell) e.getSource();
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (c.isEnabled() && toDraw) {
                                if (c.getBackground().equals(Color.WHITE)) {
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

    //рандомная генерация "первой жизни"
    public void randGen() {
        Draw.setEnabled(false);
        liveCells = new ArrayList<>();
        for (int i = 0; i < Const.randCellsAlive; i++) {
            int randX = new Random().nextInt(Const.Rows);
            int randY = new Random().nextInt(Const.Cols);
            Born(cc[randX][randY]);
        }
        Start.setEnabled(true);
    }

    public void printArray(ArrayList<Cell> arr) {
        for (int i = 0; i < arr.size(); i++) {
            System.out.print("(" + arr.get(i).getX() + " " + arr.get(i).getY() + "); ");
        }
    }

    //Основа генерации "жизни"
    public void start() {
        /*Cell Sergey = new Cell(13, 37);
        Sergey.Born();*/
        for (int i = 0; i < Const.Cols; i++) {
            for (int j = 0; j < Const.Rows; j++) {
                check(j, i);
                text.setText(Const.txt + ":  " + liveCells.size());//сделать потоки
            }
        }
    }
}