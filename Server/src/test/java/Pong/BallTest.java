package Pong;

import org.junit.Assert;
import org.junit.Test;

public class BallTest {
    Ball ball = new Ball(250, 200);

    @Test
    public void testGetX(){
        Assert.assertEquals(250, ball.getX());
    }

    @Test
    public void testGetY(){
        Assert.assertEquals(200, ball.getY());
    }

    @Test
    public void testGetXDirection(){
        Assert.assertEquals(-1, ball.getXDirection());
    }

    @Test
    public void testGetYDirection(){
        Assert.assertEquals(-1, ball.getYDirection());
    }

    @Test
    public void testGetScore1(){
        Assert.assertEquals(0, ball.getScore1());
    }

    @Test
    public void testGetScore2(){
        Assert.assertEquals(0, ball.getScore2());
    }

    @Test
    public void testSetXDirection(){
        ball.setXDirection(2);
        Assert.assertEquals(2, ball.getXDirection());
    }

    @Test
    public void testSetYDirection(){
        ball.setYDirection(2);
        Assert.assertEquals(2, ball.getYDirection());
    }

    @Test
    public void testGetP1(){
        Paddle p1 = new Paddle(1,1,1);
        ball.setP1(p1);
        Assert.assertEquals(p1, ball.getP1());
    }

    @Test
    public void testGetP2(){
        Paddle p1 = new Paddle(1,1,1);
        ball.setP2(p1);
        Assert.assertEquals(p1, ball.getP2());
    }

    @Test
    public void testSetPadY1(){
        ball.setXDirection(2);
        Assert.assertEquals(2, ball.getXDirection());
    }

    @Test
    public void testSetPadY2(){
        ball.setXDirection(2);
        Assert.assertEquals(2, ball.getXDirection());
    }
}
