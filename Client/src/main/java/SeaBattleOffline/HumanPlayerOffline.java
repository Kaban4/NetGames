package SeaBattleOffline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HumanPlayerOffline extends PlayerOffline implements ActionListener {

    public HumanPlayerOffline() {
        super();
        myTurn = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (myTurn) {
            CellOffline cell = (CellOffline) e.getSource();
            shoot(cell.getXCo(), cell.getYCo());
        }
    }

    public void setActionListnerToCells(){
        CellOffline[][] cells = opponent.getField().getCells();
        for (int i = FieldOffline.minXY; i <= FieldOffline.maxXY; i++) {
            for (int j = FieldOffline.minXY; j <= FieldOffline.maxXY; j++) {
                cells[j][i].addActionListener(this);
            }
        }
    }

    @Override
    public void setOpponent(PlayerOffline opponent) {
        super.setOpponent(opponent);
        setActionListnerToCells();
    }

    @Override
    void afterShootingHandling(int x, int y) {
        if (shootResult == ShootResult.KILLED) shipsToKill--;
        MessageManagerOffline.getInstance().getMessage(true, shootResult);
    }

    @Override
    public void reset() {
        super.reset();
        myTurn = true;
        shipsToKill = 10;
    }

    private void shoot(int x, int y) {
        shootResult = opponent.getShot(x, y);
        if (shootResult == ShootResult.MISSED){
            changeTurn();
        }
        afterShootingHandling(0, 0);
    }
}
