package SeaBattleOffline;

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayerOffline extends PlayerOffline {

    private CellOffline[][] enemyCells;
    private ArrayList<Integer> leftAims = new ArrayList();
    private ArrayList<Integer> rightAims = new ArrayList();
    private ArrayList<Integer> downAims = new ArrayList();
    private ArrayList<Integer> upAims = new ArrayList();
    private ArrayList<Integer> currentAims;
    private boolean shootToPotentialAims;
    private boolean aimsCalculated;
    private int lastX;
    private int lastY;

    public ComputerPlayerOffline() {
        super();
        myTurn = false;
        setEnemiField();
    }

    public void shoot(){
        while (myTurn) {
            try {Thread.sleep(500);} catch (InterruptedException ignored) {}
            if (shipsToKill == 0) return;
            int x, y;
            do {
                int[] coordinates = getCoordinates();
                x = coordinates[0];
                y = coordinates[1];
                shootResult = opponent.getShot(x, y);
            } while (shootResult == null);
            afterShootingHandling(x, y);
        }
    }

    private int[] getCoordinates(){
        int[] coordinates;
        if (shootToPotentialAims)
            coordinates = getNextCoordinates();
        else
            coordinates = getRandomCoordinates();

        return coordinates;
    }

    private int[] getRandomCoordinates(){
        int[] coordinate = new int[2];
        Random random = new Random();
        int x, y;
        do{
            x = random.nextInt(10);
            y = random.nextInt(10);
        }while (enemyCells[x][y].getState() != CellOffline.CellState.NULL);

        coordinate[0] = x;
        coordinate[1] = y;

        lastX = x;
        lastY = y;

        return coordinate;
    }

    private int[] getNextCoordinates() {
        if (!aimsCalculated) calculateAims();
        int[] res = new int[2];
        if(!leftAims.isEmpty()) {
            currentAims = leftAims;
            res[0] = leftAims.get(0);
            res[1] = lastY;
            leftAims.remove(0);
        } else if(!rightAims.isEmpty()) {
            currentAims = rightAims;
            res[0] = rightAims.get(0);
            res[1] = lastY;
            rightAims.remove(0);
        } else if(!downAims.isEmpty()) {
            currentAims = downAims;
            res[0] = lastX;
            res[1] = downAims.get(0);
            downAims.remove(0);
        } else if(!upAims.isEmpty()) {
            currentAims = upAims;
            res[0] = lastX;
            res[1] = upAims.get(0);
            upAims.remove(0);
        }
        return res;
    }

    private void calculateAims() {
        clearAims();
        int x = lastX, y = lastY;
        aimsCalculated = true;
        for(int i = x + 1; i < x + 4; ++i) {
            if(i < 10) {
                if (enemyCells[i][y].getState() == CellOffline.CellState.MISSED) break;
                if (enemyCells[i][y].getState() == CellOffline.CellState.NULL) rightAims.add(i);
            } else break;

        }
        for(int i = x - 1; i > x - 4; --i) {
            if(i >= 0) {
                if (enemyCells[i][y].getState() == CellOffline.CellState.MISSED) break;
                if (enemyCells[i][y].getState() == CellOffline.CellState.NULL) leftAims.add(i);
            } else break;
        }
        for(int i = y - 1; i > y - 4; --i) {
            if(i >= 0) {
                if (enemyCells[x][i].getState() == CellOffline.CellState.MISSED) break;
                if (enemyCells[x][i].getState() == CellOffline.CellState.NULL) upAims.add(i);
            } else break;
        }
        for(int i = y + 1; i < y + 4; ++i) {
            if(i < 10) {
                if (enemyCells[x][i].getState() == CellOffline.CellState.MISSED) break;
                if (enemyCells[x][i].getState() == CellOffline.CellState.NULL) downAims.add(i);
            } else break;
        }
    }

    private void clearAims(){
        leftAims.clear();
        rightAims.clear();
        downAims.clear();
        upAims.clear();
    }

    @Override
    void afterShootingHandling(int x, int y){
        boolean sleep = false;
        switch (shootResult){
            case MISSED:
                if (shootToPotentialAims) currentAims.clear();
                enemyCells[x][y].setState(CellOffline.CellState.MISSED);
                changeTurn();
                break;
            case INJURED:

                shootToPotentialAims = true;
                enemyCells[x][y].setState(CellOffline.CellState.INJURED);
                sleep = true;
                break;
            case KILLED:
                markKilled(x, y);
                sleep = true;
                shipsToKill --;
                shootToPotentialAims = false;
                aimsCalculated = false;
                break;
        }
        MessageManagerOffline.getInstance().getMessage(false, shootResult);
        try {
            if (sleep) Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

    private void markKilled(int x, int y){
        enemyCells[x][y].setState(CellOffline.CellState.INJURED);
        for (int i = FieldOffline.minXY; i <= FieldOffline.maxXY; i++) {
            for (int j = FieldOffline.minXY; j <= FieldOffline.maxXY; j++) {
                CellOffline cell = enemyCells[j][i];
                if (cell.getState() == CellOffline.CellState.INJURED){
                    cell.setState(CellOffline.CellState.KILLED);
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            if ((k >= FieldOffline.minXY) && (k <= FieldOffline.maxXY) &&
                                    (l >= FieldOffline.minXY) && (l <= FieldOffline.maxXY)){
                                if (enemyCells[l][k].getState() == CellOffline.CellState.NULL)
                                    enemyCells[l][k].setState(CellOffline.CellState.MISSED);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setEnemiField(){
        enemyCells = new CellOffline[FieldOffline.maxXY + 1][FieldOffline.maxXY + 1];
        for (int i = FieldOffline.minXY; i <= FieldOffline.maxXY; i++) {
            for (int j = FieldOffline.minXY; j <= FieldOffline.maxXY; j++) {
                enemyCells[j][i] = new CellOffline(j, i, CellOffline.CellState.NULL, null);
            }
        }
    }
    @Override
    public void reset() {
        super.reset();
        myTurn = false;
        setEnemiField();
    }

}