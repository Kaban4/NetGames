package SeaBattleOffline;

public class FieldOffline {
    public static final int maxXY = 9, minXY = 0;

    private CellOffline[][] cells = new CellOffline[maxXY + 1][maxXY + 1];

    public FieldOffline() {
        for (int i = minXY; i <= maxXY; i++) {
            for (int j = minXY; j <= maxXY; j++) {
                cells[j][i] = new CellOffline(j, i, CellOffline.CellState.NULL, null);
            }
        }

        FieldFactoryOffline.setBufferField(cells);
        FieldFactoryOffline.createShips(this);
    }

    public CellOffline[][] getCells() {
        return cells;
    }

    public void setCells(CellOffline[][] cells) {
        this.cells = cells;
    }

    public void resetCells(){
        for (int i = minXY; i <= maxXY; i++) {
            for (int j = minXY; j <= maxXY; j++) {
                cells[j][i].setState(CellOffline.CellState.NULL);
            }
        }

        FieldFactoryOffline.setBufferField(cells);
        FieldFactoryOffline.createShips(this);
    }
}
