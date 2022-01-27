package SeaBattleOffline;

public abstract class PlayerOffline{
    PlayerOffline opponent;
    FieldOffline fieldOffline;
    boolean myTurn;
    int shipsToKill = 10;
    ShootResult shootResult;

    PlayerOffline() {
        fieldOffline = new FieldOffline();
    }

    public boolean moves(){
        return myTurn;
    }

    public int getShipsToKill() {
        return shipsToKill;
    }

    public FieldOffline getField() {
        return fieldOffline;
    }

    public void setOpponent(PlayerOffline opponent) {
        this.opponent = opponent;
    }


    void changeTurn(){
        myTurn = !myTurn;
        opponent.myTurn = !opponent.myTurn;
    }

    public enum ShootResult {
        MISSED,
        INJURED,
        KILLED
    }

    ShootResult getShot(int x, int y){
        CellOffline cell = fieldOffline.getCells()[x][y];
        if (cell.getState() == CellOffline.CellState.NULL || cell.getState() == CellOffline.CellState.ALIVE) {
            cell.getShot();
            switch (cell.getState()) {
                case MISSED:
                    return ShootResult.MISSED;
                case INJURED:
                    return ShootResult.INJURED;
                case KILLED:
                    return ShootResult.KILLED;
                default:
                    return null;
            }
        } return null;
    }


    abstract void afterShootingHandling(int x, int y);

    public void reset(){
        fieldOffline.resetCells();
        shipsToKill = 10;
    }
}
