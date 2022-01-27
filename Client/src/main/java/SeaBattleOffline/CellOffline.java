package SeaBattleOffline;

import javax.swing.*;
import java.util.ArrayList;

public class CellOffline extends JButton{
    private int xCo, yCo;
    private ShipOffline shipOffline;
    private CellState state;

    public CellOffline(int x, int y, CellState cellState, ShipOffline shipOffline) {
        super();
        this.xCo = x;
        this.yCo = y;
        this.state = cellState;
        this.shipOffline = shipOffline;
    }

    public enum CellState {
        NULL,
        MISSED,
        ALIVE,
        INJURED,
        KILLED
    }

    public boolean isAlive(){
        return state == CellState.ALIVE;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state){
        this.state = state;
        CanvasOffline.getInstance().draw();
    }

    public void setShip(ShipOffline shipOffline) {
        this.shipOffline = shipOffline;
    }

    public int getXCo() {
        return xCo;
    }

    public int getYCo() {
        return yCo;
    }

    public void getShot(){
        switch (state){
            case NULL:
                setState(CellState.MISSED);
                break;
            case ALIVE:
                if (leftAliveNeighboors())
                    setState(CellState.INJURED);
                else
                    shipOffline.die();
                break;
        }
    }

    public void inititalizeState(CellState state){
        this.state = state;
    }

    @Override
    public int hashCode() {
        return xCo * 37 + yCo * 37 + 37;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof CellOffline))
            return false;
        CellOffline cell = (CellOffline) obj;

        return (cell.xCo == xCo && cell.yCo == yCo);
    }

    private boolean leftAliveNeighboors(){
        ArrayList<CellOffline> cells = shipOffline.getCells();
        for (CellOffline cell : cells){
            if (!cell.equals(this) && cell.isAlive()) return true;
        }
        return false;
    }
}
