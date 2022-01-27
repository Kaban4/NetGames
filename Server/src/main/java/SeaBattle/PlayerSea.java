package SeaBattle;

import com.company.*;
import java.util.Random;

public class PlayerSea {
    final int FIELD_SIZE = 10;
    private boolean move;
    public int[][] playerField;
    public Server.clientThread player;
    private Random random;

    public PlayerSea(Server.clientThread player, boolean move) {
        this.player = player;
        this.move = move;
        init();
    }

    public PlayerSea(boolean move) {
        this.move = move;
        init();
    }

    public boolean getMove(){
        return move;
    }

    public void setMove(boolean move){
        this.move = move;
    }

    void init() {
        playerField = new int[FIELD_SIZE][FIELD_SIZE];
        random = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                playerField[i][j] = 0;
            }
        }

        generateShips();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(playerField[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public int getPlayerField(int i, int j) {
        return playerField[i][j];
    }

    public boolean isFree(int x, int y) {
        boolean check;
        int[][] auxiliaryArray = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        int dx, dy;
        if ((x >= 0) && (x < 10) && (y >= 0) && (y < 10) && (playerField[x][y] == 0)) {
            for (int i = 0; i < 8; i++) {
                dx = x + auxiliaryArray[i][0];
                dy = y + auxiliaryArray[i][1];
                if ((dx >= 0) && (dx < 10) && (dy >= 0) && (dy < 10) && (playerField[dx][dy] == 2)) {
                    check = false;
                    return check;
                }
            }
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public void generateShips() {
        int x, y, kx, ky;
        boolean check;
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j <= 3 - i; j++) {
                do {
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                    kx = random.nextInt(2);
                    if (kx == 0) {
                        ky = 1;
                    } else {
                        ky = 0;
                    }
                    check = false;
                    for (int k = 0; k <= i; k++) {
                        if (!(isFree(x + kx * k, y + ky * k))) {
                            check = true;
                            break;
                        }
                    }
                    if (!check) {
                        for (int k = 0; k <= i; k++) {
                            playerField[x + kx * k][y + ky * k] = 2;
                        }
                    }
                } while (check);
            }
        }
    }

    public boolean checkDefeat() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (playerField[i][j] == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkHit(int x, int y) {
        if ((playerField[x][y] == 2)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hitSamePlace(int x, int y) {
        if ((playerField[x][y] == 1) || (playerField[x][y] == 3)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean CheckForEmptinessCell(int x, int y) {
        if (playerField[x][y] == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void shotInCell(int x, int y) {
        int cell = playerField[x][y];
        switch (cell) {
            case 2 -> playerField[x][y] = 3;
            case 0 -> playerField[x][y] = 1;
        }
    }
}
