package Pong;

import java.awt.Rectangle;

public class Paddle implements Runnable{
    private int id;
    private Rectangle paddle;

    public Paddle(int x, int y, int id){
        this.id = id;
        paddle = new Rectangle(x, y, 10, 50);
    }

    public int getId() {
        return id;
    }

    public Rectangle getPaddle(){
        return paddle;
    }

    public void setPaddle(Rectangle p){
        paddle = p;
    }

    public void setY(int val) {
         paddle.y = val;
    }

    public int getY() {
        return paddle.y;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Thread.sleep(7);
            }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}