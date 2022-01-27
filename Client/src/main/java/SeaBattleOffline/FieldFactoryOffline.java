package SeaBattleOffline;

import java.util.ArrayList;
import java.util.Random;

public class FieldFactoryOffline {
    private static CellOffline[][] bufferField;

    private enum Orientation{
        HORIZONTAL,
        VERTICAL
    }

    public static void setBufferField(CellOffline[][] bufferField) {
        FieldFactoryOffline.bufferField = bufferField;
    }

    public static void createShips(FieldOffline fieldOffline){
        ArrayList<ShipOffline> shipOfflines = new ArrayList<>();
        Random random = new Random();

        for (int shipSize = 4; shipSize >= 1 ; --shipSize) {
            int shipsCount = 5 - shipSize;
            for (int i = 0; i < shipsCount; ++i) {
                Orientation orientation;
                int x, y;
                do {
                    orientation = getNextOrientation();
                    if (orientation == Orientation.HORIZONTAL){
                        x = random.nextInt(10 - shipSize + 1);
                        y = random.nextInt(10);
                    } else {
                        x = random.nextInt(10);
                        y = random.nextInt(10 - shipSize + 1);
                    }

                } while (!validPlaceForShip(x, y, shipSize, orientation));

                shipOfflines.add(createShip(x, y, shipSize, orientation));
            }

        }

        fieldOffline.setCells(bufferField);
        bufferField = null;
    }

    public static void initializeBuffer(CanvasOffline canvas){
        bufferField = new CellOffline[FieldOffline.maxXY + 1][FieldOffline.maxXY + 1];
        for (int i = FieldOffline.minXY; i <= FieldOffline.maxXY; i++) {
            for (int j = FieldOffline.minXY; j <= FieldOffline.maxXY; j++) {
                bufferField[j][i] = new CellOffline(j, i, CellOffline.CellState.NULL, null);
            }
        }
    }


    private static boolean validPlaceForShip(int x, int y, int size, Orientation orientation){
        int xFrom = x - 1,
            yFrom = y - 1,
            xTo, yTo;

        if (orientation == Orientation.VERTICAL){
            xTo = x + size;
            yTo = y + 1;
            if (xTo - 1 > FieldOffline.maxXY) return false;

        } else {
            xTo = x + 1;
            yTo = y + size;
            if (yTo - 1 > FieldOffline.maxXY) return false;
        }

        if (xFrom < FieldOffline.minXY) xFrom = FieldOffline.minXY;
        if (yFrom < FieldOffline.minXY) yFrom = FieldOffline.minXY;
        if (yTo > FieldOffline.maxXY) yTo = FieldOffline.maxXY;
        if (xTo > FieldOffline.maxXY) xTo = FieldOffline.maxXY;

        for (int i = xFrom; i <= xTo; i++) {
            for (int j = yFrom; j <= yTo; j++) {
                if (bufferField[j][i].isAlive()) return false;
            }
        }
        return true;
    }

    private static ShipOffline createShip(int x, int y, int size, Orientation orientation){
        int xTo, yTo;

        if (orientation == Orientation.VERTICAL){
            xTo = x + size - 1;
            yTo = y;
        } else {
            xTo = x;
            yTo = y + size - 1;
        }

        ArrayList<CellOffline> cells = new ArrayList<>();
        ArrayList<CellOffline> borders = new ArrayList<>();
        ShipOffline shipOffline = new ShipOffline();
        for (int i = x; i <= xTo; i++) {
            for (int j = y; j <= yTo; j++) {
                bufferField[j][i].inititalizeState(CellOffline.CellState.ALIVE);
                bufferField[j][i].setShip(shipOffline);
                cells.add(bufferField[j][i]);
            }
        }

        for (int k = x - 1; k <= xTo + 1; k++) {
            for (int l = y - 1; l <= yTo + 1; l++) {
                if (k >= FieldOffline.minXY && k <= FieldOffline.maxXY && l >= FieldOffline.minXY && l <= FieldOffline.maxXY)
                    if (bufferField[l][k].getState() != CellOffline.CellState.ALIVE)
                        if (!borders.contains(bufferField[l][k]))
                            borders.add(bufferField[l][k]);
            }
        }

        shipOffline.setCells(cells);
        shipOffline.setBorders(borders);
        return shipOffline;
    }

    private static Orientation getNextOrientation(){
        Random rnd = new Random();
        if (rnd.nextBoolean())
            return Orientation.HORIZONTAL;
        else
            return Orientation.VERTICAL;
    }

}
