package TicTacToe;

import org.junit.Assert;
import org.junit.Test;

public class TestMainGameField {
    MainGameField mainGameField = MainGameField.getInstance();
    @Test
    public void testPlayerSea() {
        Assert.assertEquals("X",mainGameField.player1.sign);
        Assert.assertFalse(mainGameField.gameOver);
    }

    @Test
    public void testStartNewGame() {
        MainGameField mainGameField = MainGameField.getInstance();
        mainGameField.startNewGame();
        Assert.assertFalse(mainGameField.gameOver);
        Assert.assertEquals(150,mainGameField.cellSize);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assert.assertEquals("*",mainGameField.cell[i][j]);
            }
        }
    }

    @Test
    public void testIsFieldFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mainGameField.cell[i][j].equals(mainGameField.NOT_SIGN)){
                    Assert.assertFalse(mainGameField.isFieldFull());
                    return;
                }
            }
        }
        Assert.fail();
    }

    @Test
    public void testCheckWin() {
        MainGameField mainGameField = MainGameField.getInstance();
        mainGameField.startNewGame();
        for (int i = 0; i < 3; i++) {
            // проверяем строки
            if (mainGameField.checkLine(i, 0, 0, 1, "X"))
                Assert.assertTrue(mainGameField.checkWin("X"));
            // проверяем столбцы
            if (mainGameField.checkLine(0, i, 1, 0, "X"))
                Assert.assertTrue(mainGameField.checkWin("X"));
        }
        // проверяем диагонали
        if (mainGameField.checkLine(0, 0, 1, 1, "X"))
            Assert.assertTrue(mainGameField.checkWin("X"));
        if (mainGameField.checkLine(0, 3 - 1, 1, -1, "X"))
            Assert.assertTrue(mainGameField.checkWin("X"));
        Assert.assertFalse(mainGameField.checkWin("X"));
    }
}
