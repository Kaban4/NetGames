package TicTacToeOnline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.company.Paths.*;

public class MainGameField extends JPanel {
    private static MainGameField instance = null;
    public static final int FIELD_SIZE = 450;
    public final String NOT_SIGN = "*";
    public boolean gameOver = false;//public поставил
    public String gameOverMessage = "";//public поставил
    public static int linesCount = 3;//public поставил
    public int cellSize;//public поставил
    public int x;//public поставил
    public int y;//public поставил
    public boolean nextTurn = false;//public поставил
    public Player player1;//public поставил
    public Player player2;//public поставил
    public int gameMode = 1;//public поставил
    public String[][] cell;

    public static synchronized MainGameField getInstance() {
        if (instance == null)
            instance = new MainGameField();
        return instance;
    }

    public void startNewGame() {
        gameOver = false;
        gameOverMessage = "";
        cellSize = FIELD_SIZE / linesCount;
        cell = new String[linesCount][linesCount];
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                cell[i][j] = NOT_SIGN;
            }
        }
        setVisible(true);
        repainting();
    }

    private MainGameField() {
        //setV
        player1 = new Player("X");
        player2 = new Player("O");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                x = e.getX() / cellSize;
                y = e.getY() / cellSize;
                System.out.println("Mouse clicked on " + e.getX() + " " + e.getY());

                if (!gameOver) {
                    twoPlayersMode();
                }
            }
        });
    }

    void twoPlayersMode() {
        if (player1.isShotReady == 1) {
            nextTurn = true;
            player2.isShotReady = 0;
            System.out.println("Player 1 shot!");
            player1.shot(x,y);
        }
        repainting();
    }

    boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > linesCount - 1 || y > linesCount - 1) {
            return false;
        }
        return !cell[x][y].equals(NOT_SIGN);
    }

    public void repainting() {
        removeAll();
        repaint();
        if (gameOver) {
            removeAll();
            JLabel label = new JLabel();
            label.setLayout(null);
            System.out.println(gameOverMessage);
            if(gameOverMessage.contains("Win")){
                setComponentForPanel(pathCrown,450,450,0,0,label);
            }
            if(gameOverMessage.contains("Lose")){
                setComponentForPanel(pathKakaha,450,450,0,0,label);
            }
            if(gameOverMessage.contains("Draw")){
                setComponentForPanel(pathHandshake,450,450,0,0,label);
            }
            instance = null;
            add(label);
        }
        else {
            for (int i = 0; i < linesCount; i++) {
                for (int j = 0; j < linesCount; j++) {
                    if (!cell[i][j].equals(NOT_SIGN)) {
                        if (cell[i][j].equals("X")) {
                            JLabel label = new JLabel();
                            setComponentForPanel(pathX, cellSize, cellSize, i * cellSize, j * cellSize, label);
                            add(label);
                        }
                        if (cell[i][j].equals("O")) {
                            JLabel label = new JLabel();
                            setComponentForPanel(pathO, cellSize, cellSize, i * cellSize, j * cellSize, label);
                            add(label);
                        }
                    }
                }
            }
            for (int i = 0; i <= linesCount; i++) {
                JPanel panel = new JPanel();
                panel.setBounds(0, i * cellSize, linesCount * cellSize, 2);
                panel.setBackground(Color.BLACK);
                add(panel);

                JPanel panel2 = new JPanel();
                panel2.setBounds(i * cellSize, 0, 2, linesCount * cellSize);
                panel2.setBackground(Color.BLACK);
                add(panel2);
            }
        }
        repaint();
    }

    public void firstPaint(){
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (!cell[i][j].equals(NOT_SIGN)) {
                    if (cell[i][j].equals("X")) {
                        JLabel label = new JLabel();
                        setComponentForPanel(pathX, cellSize, cellSize, i * cellSize, j * cellSize, label);
                        add(label);
                    }
                    if (cell[i][j].equals("O")) {
                        JLabel label = new JLabel();
                        setComponentForPanel(pathO, cellSize, cellSize, i * cellSize, j * cellSize, label);
                        add(label);
                    }
                }
            }
        }
        for (int i = 0; i <= linesCount; i++) {
            JPanel panel = new JPanel();
            panel.setBounds(0, i * cellSize, linesCount * cellSize, 2);
            panel.setBackground(Color.BLACK);
            add(panel);

            JPanel panel2 = new JPanel();
            panel2.setBounds(i * cellSize, 0, 2, linesCount * cellSize);
            panel2.setBackground(Color.BLACK);
            add(panel2);
        }
        repaint();
    }

    public void setComponentForPanel(String path, int width, int height, int x, int y, JLabel component){
        Image im = new ImageIcon(path).getImage();
        im = im.getScaledInstance(width,height,Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(im);
        component.setBounds(x,y,width,height);
        component.setIcon(image);
        image.setImageObserver(component);
    }
}
