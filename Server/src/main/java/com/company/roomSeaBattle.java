package com.company;

import Pong.Pong;
import SeaBattle.PlayerSea;

import java.io.IOException;
import java.util.Random;

public class roomSeaBattle {
    private final boolean locked;
    private final Server server;
    private final int key;
    public boolean flagMessageSea = false;
    public String mesSea = "";
    private final Server.clientThread clientFirst;
    private Server.clientThread clientSecond;
    private boolean endGame = false;

    public roomSeaBattle(Server.clientThread clientFirst,Server server, boolean locked, int key){
        this.server = server;
        this.key = key;
        this.clientFirst = clientFirst;
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

    public void startGameSea(){
        startGameSeaBattle st = new startGameSeaBattle(clientFirst, clientSecond);
        st.start();
    }

    public void endGame(Server.clientThread client) throws IOException {
        endGame = true;
        if(client == clientFirst){
            clientSecond.out.println("Sea win!");
            clientFirst.out.println("Sea lose!");
            server.OverwritingStats("Sea",false, clientFirst);
            server.OverwritingStats("Sea",true, clientSecond);
            System.out.println("End game!");
        }
        if(client == clientSecond){
            clientFirst.out.println("Sea win!");
            clientSecond.out.println("Sea lose!");
            server.OverwritingStats("Sea",true, clientFirst);
            server.OverwritingStats("Sea",false, clientSecond);
            System.out.println("End game!");
        }
    }

    class startGameSeaBattle extends Thread{
        private final PlayerSea playerLeft;
        private final PlayerSea playerRight;
        private boolean turnSended = false;

        public startGameSeaBattle(Server.clientThread player1, Server.clientThread player2){
            Random random = new Random();
            int firstTurn =  random.nextInt(2);
            if(firstTurn == 0) {
                playerLeft = new PlayerSea(player1, true);
                playerRight = new PlayerSea(player2, false);
            }
            else{
                playerLeft = new PlayerSea(player2,true);
                playerRight = new PlayerSea(player1,false);
            }
        }

        public void run() {
            System.out.println("----------Start game---------");

            StringBuilder sb = new StringBuilder("MyField");
            for(int i = 0; i < 10; i++){
                for (int j = 0; j < 10; j++){
                    if(playerLeft.playerField[i][j] == 2)
                        sb.append(" ").append(i).append(" ").append(j);
                }
            }
            playerLeft.player.out.println(sb.toString());

            sb = new StringBuilder("MyField");
            for(int i = 0; i < 10; i++){
                for (int j = 0; j < 10; j++){
                    if(playerRight.playerField[i][j] == 2)
                        sb.append(" ").append(i).append(" ").append(j);
                }
            }
            playerRight.player.out.println(sb.toString());

            int x, y;

            while (!endGame) {
                try {
                    Thread.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(playerLeft.getMove() & !turnSended) {
                    playerLeft.player.out.println("Turn");//Your turn
                    turnSended = true;
                }
                if(playerRight.getMove() & !turnSended) {
                    playerRight.player.out.println("Turn");//Your turn
                    turnSended = true;
                }
                if(flagMessageSea && playerLeft.getMove()){
                    String [] str = mesSea.split(" ");
                    x = Integer.parseInt(str[1]);
                    y = Integer.parseInt(str[2]);
                    if (!playerRight.hitSamePlace(x, y)) {//если человек еще не поражал эту точку
                        if (playerRight.checkHit(x, y)) { //поразили цель
                            playerRight.shotInCell(x, y);
                            System.out.println("You hit the target");
                            if (playerRight.checkDefeat()) {//нет выживших кораблей противника
                                try {
                                    playerLeft.player.out.println("You hit " + str[1] + " " + str[2]);
                                    playerRight.player.out.println("Your hit " + str[1] + " " + str[2]);
                                    System.out.println("YOU WIN!");
                                    endGame(playerRight.player);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                playerLeft.player.out.println("You hit " + str[1] + " " + str[2]);
                                playerLeft.player.out.println("Turn");
                                turnSended = true;
                                playerRight.player.out.println("Your hit " + str[1] + " " + str[2]);
                            }
                        } else if (playerRight.CheckForEmptinessCell(x, y)) {//не поразили цель
                            playerRight.shotInCell(x, y);
                            playerLeft.setMove(false);
                            System.out.println("You missed. Enemy turn");
                            playerLeft.player.out.println("You miss " + str[1] + " " + str[2]);
                            playerRight.player.out.println("Turn");
                            playerRight.player.out.println("Your miss " + str[1] + " " + str[2]);
                            playerRight.setMove(true);
                        }
                    }
                    flagMessageSea = false;
                }
                if(flagMessageSea && playerRight.getMove()){
                    String [] str = mesSea.split(" ");
                    x = Integer.parseInt(str[1]);
                    y = Integer.parseInt(str[2]);
                    if (!playerLeft.hitSamePlace(x, y)) {//если человек еще не поражал эту точку
                        if (playerLeft.checkHit(x, y)) { //поразили цель
                            playerLeft.shotInCell(x, y);
                            System.out.println("You hit the target");
                            if (playerLeft.checkDefeat()) {//нет выживших кораблей противника
                                try {
                                    playerRight.player.out.println("You hit " + str[1] + " " + str[2]);
                                    playerLeft.player.out.println("Your hit " + str[1] + " " + str[2]);
                                    System.out.println("YOU WiN!");
                                    endGame(playerLeft.player);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                playerRight.player.out.println("You hit " + str[1] + " " + str[2]);
                                playerRight.player.out.println("Turn");
                                turnSended = true;
                                playerLeft.player.out.println("Your hit " + str[1] + " " + str[2]);
                            }
                        } else if (playerLeft.CheckForEmptinessCell(x, y)) {//не поразили цель
                            playerLeft.shotInCell(x, y);
                            playerRight.setMove(false);
                            System.out.println("You missed. Enemy turn");
                            playerRight.player.out.println("You miss " + str[1] + " " + str[2]);
                            playerLeft.player.out.println("Turn");
                            playerLeft.player.out.println("Your miss " + str[1] + " " + str[2]);
                            playerLeft.setMove(true);
                        }
                    }
                    flagMessageSea = false;
                }
            }
            System.out.println("----------Game over---------");
        }
    }
}
