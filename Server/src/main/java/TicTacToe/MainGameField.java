package TicTacToe;

import javax.swing.*;
import java.awt.*;

public class MainGameField extends JPanel {
    private static MainGameField instance = null;
    public static final int FIELD_SIZE = 450;
    public final String NOT_SIGN = "*";
    public boolean gameOver = false;
    public String gameOverMessage = "";
    public static int linesCount = 3;
    public int cellSize;
    public int x;
    public int y;
    public boolean nextTurn = false;
    public Player player1;
    public Player player2;
    public int gameMode = 1;
    public int aiLevel = 0;
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
        repaint();
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                cell[i][j] = NOT_SIGN;
            }
        }
        setVisible(true);
    }

    private MainGameField() {
        setVisible(false);
        player1 = new Player("X");
        player2 = new Player("O");
    }

    boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > linesCount - 1 || y > linesCount - 1) {
            return false;
        }
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
        if (checkLine(0, linesCount - 1, 1, -1, sign))
            return true;
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <= this.linesCount; i++) {
            g.drawLine(0, i * this.cellSize, FIELD_SIZE, i * this.cellSize);
            g.drawLine(i * this.cellSize, 0, i * this.cellSize, FIELD_SIZE);
        }
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (!cell[i][j].equals(NOT_SIGN)) {
                    if (cell[i][j].equals("X")) {
                        g.setColor(Color.RED);
                        g.drawLine((i * cellSize), (j * cellSize), (i + 1) * cellSize, (j + 1) * cellSize);
                        g.drawLine((i + 1) * cellSize, (j * cellSize), (i * cellSize), (j + 1) * cellSize);
                    }
                    if (cell[i][j].equals("O")) {
                        g.setColor(Color.BLUE);
                        g.drawOval((i * cellSize), (j * cellSize), cellSize, cellSize);
                    }
                }
            }
        }

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.fillRect(0, FIELD_SIZE / 2, FIELD_SIZE, FIELD_SIZE / 8);
            g.setColor(Color.RED);
            g.setFont(new Font("Tahoma", 10, 40));
            g.drawString(gameOverMessage, 0, 19 * FIELD_SIZE / 32);
        }
    }
}
