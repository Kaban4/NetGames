package com.company;

import Pong.Pong;
import java.io.IOException;

public class roomPong {
    private final boolean locked;
    private final int key;
    private final Server.clientThread clientFirst;
    private Server.clientThread clientSecond = null;
    private Pong pong;
    private final Server server;
    private boolean endGame = false;
    public boolean mesFromLeftPong = false;
    public boolean mesFromRightPong = false;
    public String FromLeftPong = "";
    public String FromRightPong = "";

    public roomPong(Server.clientThread clientFirstTCP,Server server, boolean locked, int key){
        this.server = server;
        this.key = key;
        this.clientFirst = clientFirstTCP;
        this.locked = locked;
    }

    public Server.clientThread getClientFirst() {
        return clientFirst;
    }

    public Server.clientThread getClientSecond() {
        return clientSecond;
    }

    public int getKey() {
        return key;
    }

    public boolean getEndGame(){
        return endGame;
    }

    public boolean isLocked(){
        return locked;
    }

    public void setClientSecond(Server.clientThread clientSecond){
        this.clientSecond = clientSecond;
    }

    public void startGamePong(){
        pong = new Pong();
        startGamePong st = new startGamePong();
        st.start();
    }

    public void endGame(Server.clientThread client) throws IOException {
        endGame = true;
        if(client == clientFirst){
            clientSecond.out.println("Pong win!");
            clientFirst.out.println("Pong lose!");
            server.OverwritingStats("Pong",false, clientFirst);
            server.OverwritingStats("Pong",true, clientSecond);
            System.out.println("End game!");
        }
        if(client == clientSecond){
            clientFirst.out.println("Pong win!");
            clientSecond.out.println("Pong lose!");
            server.OverwritingStats("Pong",true, clientFirst);
            server.OverwritingStats("Pong",false, clientSecond);
            System.out.println("End game!");
        }
    }

    class startGamePong extends Thread{
        public void run() {
            while(!endGame){
                try {
                    if(pong.b != null) {
                        if (pong.b.gameOver){
                            if(pong.b.getScore1() == 2)
                                endGame(clientSecond);

                            if(pong.b.getScore2() == 2)
                                endGame(clientFirst);
                        }
                        if (pong.b.flag) {
                            clientFirst.out.println("Ball " + pong.b.getX() + " " + pong.b.getY() + " "
                                    + pong.b.getXDirection() + " " + pong.b.getYDirection() + " ");
                            clientSecond.out.println("Ball " + pong.b.getX() + " " + pong.b.getY() + " "
                                    + pong.b.getXDirection() + " " + pong.b.getYDirection() + " ");
                        }
                        if (pong.b.flagScore) {
                            clientFirst.out.println("Score " + pong.b.getScore1() + " " + pong.b.getScore2());
                            clientSecond.out.println("Score " + pong.b.getScore1() + " " + pong.b.getScore2());
                            pong.b.flagScore = false;
                        }
                        if (mesFromLeftPong) {
                            String[] str = FromLeftPong.split(" ");
                            clientSecond.out.println("Paddle Player1 " + str[2] + " " + str[3]);
                            pong.b.setPadY1(Integer.parseInt(str[2]));
                            mesFromLeftPong = false;
                        }
                        if (mesFromRightPong) {
                            String[] str = FromRightPong.split(" ");
                            clientFirst.out.println("Paddle Player2 " + str[2] + " " + str[3]);
                            pong.b.setPadY2(Integer.parseInt(str[2]));
                            mesFromRightPong = false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}