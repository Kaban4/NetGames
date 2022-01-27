package Pong;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class PaddleTest {

    Paddle paddle = new Paddle(1,1,1);

    @Test
    public void testPaddle(){
        Paddle testPaddle = new Paddle(1,1,1);
        Assert.assertEquals(1,testPaddle.getId());
        Assert.assertEquals(new Rectangle(1, 1, 10, 50),testPaddle.getPaddle());
    }

    @Test
    public void testGetPaddle(){
        Rectangle rc = new Rectangle();
        paddle.setPaddle(rc);
        Assert.assertEquals(rc, paddle.getPaddle());
    }

    @Test
    public void testSetY(){
        paddle.setY(2);
        Assert.assertEquals(2,paddle.getY());
    }
}