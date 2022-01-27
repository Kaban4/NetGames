package TicTacToeOnline;

public class Player extends AGamer {
    public MainGameField gameField;//public поставил
    public int isShotReady = 1;//public поставил
    public boolean ashot = false;//public поставил
    public int ashotY = 0,ashotX = 0;//public поставил

    public Player(String sign) {
        this.sign = sign;
    }

    public String getSign(){ // возвращает чем ходит этот пользователь
        return this.sign;
    }

    public boolean shot(int x, int y) {//public поставил
        gameField = MainGameField.getInstance();
        if (!gameField.isCellBusy(x, y)) {
            gameField.cell[x][y] = sign;
            ashotX = x;
            ashotY = y;
            ashot = true;
            System.out.println("ashot" + " " + x + " " + y);
            isShotReady = 0;
            return true;
        }
        return false;
    }
}