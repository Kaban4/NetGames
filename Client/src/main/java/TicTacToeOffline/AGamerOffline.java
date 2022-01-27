package TicTacToeOffline;

public abstract class AGamerOffline {
    protected String sign;

    abstract boolean shot(int x, int y);
    abstract boolean win();
}