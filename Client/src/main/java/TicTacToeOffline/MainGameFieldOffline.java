package TicTacToeOffline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.company.Paths.*;

public class MainGameFieldOffline extends JPanel {
    private static MainGameFieldOffline instance = null;
    public static final int FIELD_SIZE = 450;
    public final String NOT_SIGN = "*";
    boolean gameOver = false, nextTurn = false;
    String gameOverMessage = "";
    static int linesCount = 3;
    int cellSize, x, y,gameMode = 1, aiLevel = 0;
    PlayerOffline player1, player2;
    public String[][] cell;

    public static synchronized MainGameFieldOffline getInstance() {
        if (instance == null)
            instance = new MainGameFieldOffline();
        return instance;
    }

    void startNewGame() {
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

    private MainGameFieldOffline() {
        setVisible(true);
        player1 = new PlayerOffline("X");
        player2 = new PlayerOffline("O");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                x = e.getX() / cellSize;
                y = e.getY() / cellSize;
                System.out.println("Mouse clicked on " + e.getX() + " " + e.getY());

                if (!gameOver) {
                    if (gameMode == 2) {
                        modeAgainstAI();
                    }
                }
            }
        });
    }

    void modeAgainstAI() {
        PlayerOffline player = new PlayerOffline("X");
        AIOffline ai = new AIOffline("O", aiLevel, player.sign);
        if (!gameOver) {
            if (player.shot(x, y)) {
                if (player.win()) {
                    System.out.println("Win");
                    gameOver = true;
                    gameOverMessage = "Win";
                }
                if (isFieldFull() && !ai.win() && !player.win()) {
                    gameOver = true;
                    gameOverMessage = "Draw";
                }
                repainting();
                if (!gameOver) {
                    ai.shot(x, y);
                }
                if (ai.win()) {
                    System.out.println("Lose");
                    gameOver = true;
                    gameOverMessage = "Lose";
                }
                repainting();
                if (isFieldFull() && !ai.win() && !player.win()) {
                    gameOver = true;
                    gameOverMessage = "Ничья";
                }
            }
        }
    }

    boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > linesCount - 1 || y > linesCount - 1)
            return false;
        return !cell[x][y].equals(NOT_SIGN);
    }

    public boolean isFieldFull() {
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (cell[i][j].equals(NOT_SIGN))
                    return false;
            }
        }
        return true;
    }

    public boolean checkLine(int start_x, int start_y, int dx, int dy, String sign) {
        for (int i = 0; i < linesCount; i++) {
            if (!cell[start_x + i * dx][start_y + i * dy].equals(sign))
                return false;
        }
        return true;
    }

    public boolean checkWin(String sign) {
        for (int i = 0; i < linesCount; i++) {
            // проверяем строки
            if (checkLine(i, 0, 0, 1, sign))
                return true;
            // проверяем столбцы
            if (checkLine(0, i, 1, 0, sign))
                return true;
        }
        // проверяем диагонали
        if (checkLine(0, 0, 1, 1, sign))
            return true;
        return checkLine(0, linesCount - 1, 1, -1, sign);
    }

    protected void repainting() {
        removeAll();
        repaint();
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (!cell[i][j].equals(NOT_SIGN)) {
                    if (cell[i][j].equals("X")) {
                        JLabel label = new JLabel();
                        setComponentForPanel(pathX, cellSize,cellSize,i * cellSize,j*cellSize, label);
                        add(label);
                    }
                    if (cell[i][j].equals("O")) {
                        JLabel label = new JLabel();
                        setComponentForPanel(pathO, cellSize,cellSize,i * cellSize,j*cellSize, label);
                        add(label);
                    }
                }
            }
        }
        for(int i = 0; i <= linesCount; i++) {
            JPanel panel = new JPanel();
            panel.setBounds(0,i * cellSize,linesCount * cellSize,2);
            panel.setBackground(Color.BLACK);
            add(panel);

            JPanel panel2 = new JPanel();
            panel2.setBounds(i * cellSize,0,2,linesCount * cellSize);
            panel2.setBackground(Color.BLACK);
            add(panel2);
        }

        if (gameOver) {
            removeAll();
            JLabel label = new JLabel();
            label.setLayout(null);
            System.out.println(gameOverMessage);
            if(gameOverMessage.equals("Win")){
                setComponentForPanel(pathCrown,450,450,0,0,label);
            }
            if(gameOverMessage.equals("Lose")){
                setComponentForPanel(pathKakaha,450,450,0,0,label);
            }
            if(gameOverMessage.equals("Draw")){
                setComponentForPanel(pathHandshake,450,450,0,0,label);
            }
            add(label);
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

