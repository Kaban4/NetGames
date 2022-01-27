package TicTacToe;

import org.junit.Assert;
import org.junit.Test;
public class TestPlayer {

    @Test
    public void testPlayerTic() {
        Player player = new Player("Left");
        Assert.assertEquals("Left", player.sign);
    }

}