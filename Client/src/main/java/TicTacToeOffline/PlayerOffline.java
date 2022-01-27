package TicTacToeOffline;

public class PlayerOffline extends AGamerOffline {
    MainGameFieldOffline gameField;
    int isShotReady = 1;

    public PlayerOffline(String sign) {
        this.sign = sign;
    }

    boolean shot(int x, int y) {
        gameField = MainGameFieldOffline.getInstance();
        if (!gameField.isCellBusy(x, y)) {
            gameField.cell[x][y] = sign;
            return true;
        }
        return false;
    }

    boolean win() {
        gameField = MainGameFieldOffline.getInstance();
        return gameField.checkWin(this.sign);
    }
}