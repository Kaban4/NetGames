package SeaBattleOffline;

import java.util.ArrayList;

public class ShipOffline {
    private ArrayList<CellOffline> cells;
    private ArrayList<CellOffline> borders;

    public ArrayList<CellOffline> getCells() {
        return cells;
    }


    public void die(){
        for (CellOffline cell : cells)
            cell.setState(CellOffline.CellState.KILLED);

        for (CellOffline cell : borders)
            cell.setState(CellOffline.CellState.MISSED);
    }


    public void setCells(ArrayList<CellOffline> cells) {
        this.cells = cells;
    }


    public void setBorders(ArrayList<CellOffline> borders) {
        this.borders = borders;
    }
}
