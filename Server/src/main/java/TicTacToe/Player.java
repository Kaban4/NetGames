package TicTacToe;

public class Player extends AGamer {
    public MainGameField gameField;
    public int isShotReady = 1;

    public Player(String sign) {
        this.sign = sign;
    }

    public String getSign(){ // возвращает чем ходит этот пользователь
        return this.sign;
    }

    public boolean win() {
        gameField = MainGameField.getInstance();
        return gameField.checkWin(this.sign);
    }
}