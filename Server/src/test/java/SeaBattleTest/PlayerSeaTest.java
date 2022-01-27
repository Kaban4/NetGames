package SeaBattleTest;

import SeaBattle.PlayerSea;
import org.junit.Assert;
import org.junit.Test;

public class PlayerSeaTest {

    @Test
    public void testPlayerSea() {
        PlayerSea playerSea = new PlayerSea(true);
        Assert.assertTrue(playerSea.getMove());
    }

    @Test
    public void testGetMove() {
        PlayerSea playerSea = new PlayerSea(true);
        playerSea.setMove(false);
        Assert.assertFalse(playerSea.getMove());
    }

    @Test
    public void testInit() {
        PlayerSea playerSea = new PlayerSea(true);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Assert.assertTrue(playerSea.getPlayerField(i,j) == 0 || playerSea.getPlayerField(i,j) == 2);
            }
        }
    }

    @Test
    public void testGenerateShips() {
        PlayerSea playerSea = new PlayerSea(true);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if(playerSea.getPlayerField(i,j) == 2) {
                    Assert.assertTrue(true);
                    return;
                }
                if(playerSea.getPlayerField(i,j) == 1)
                    Assert.fail();
            }
        }
    }

    @Test
    public void testCheckDefeat() {
        PlayerSea playerSea = new PlayerSea(true);
        Assert.assertFalse(playerSea.checkDefeat());
    }

    @Test
    public void testCheckHit() {
        PlayerSea playerSea = new PlayerSea(true);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Assert.assertEquals(playerSea.getPlayerField(i,j) == 2,playerSea.checkHit(i, j));
            }
        }
    }

    @Test
    public void testHitSamePlace() {
        PlayerSea playerSea = new PlayerSea(true);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if(playerSea.getPlayerField(i,j) == 2){
                    Assert.assertFalse(playerSea.hitSamePlace(i, j));
                }
                if(playerSea.getPlayerField(i,j) == 1 || playerSea.getPlayerField(i,j) == 3){
                    Assert.assertTrue(playerSea.hitSamePlace(i, j));
                }
            }
        }
    }

    @Test
    public void testCheckForEmptinessCell() {
        PlayerSea playerSea = new PlayerSea(true);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (playerSea.getPlayerField(i, j) == 0) {
                    Assert.assertTrue(playerSea.CheckForEmptinessCell(i, j));
                } else
                    Assert.assertFalse(playerSea.CheckForEmptinessCell(i, j));
            }
        }
    }

    @Test
    public void testShotInCell() {
        PlayerSea playerSea = new PlayerSea(true);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int cell = playerSea.getPlayerField(i,j);
                playerSea.shotInCell(i,j);
                switch (cell) {
                    case 2 -> Assert.assertEquals(3, playerSea.getPlayerField(i, j));
                    case 0 -> Assert.assertEquals(1, playerSea.getPlayerField(i, j));
                }
            }
        }
    }
}
