package com.company;

import TicTacToe.MainForm;
import TicTacToe.MainGameField;

import java.io.IOException;

public class roomTicTacToe {
    private final boolean locked;
    private final Server server;
    private final int key;
    public boolean mesFromLeftTicTacToe = false;
    public boolean mesFromRightTicTacToe = false;
    public String FromLeftTicTacToe = "";
    public String FromRightTicTacToe = "";
    private final Server.clientThread clientFirst;
    private Server.clientThread clientSecond;
    private boolean endGame = false;
    private MainGameField gameField;

    public roomTicTacToe(Server.clientThread clientFirstTCP,Server server, boolean locked, int key){
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

    public void endGame(Server.clientThread client) throws IOException {
        endGame = true;
        if(client == clientFirst){
            clientFirst.out.println("TicTacToe Win Right");
            server.OverwritingStats("TicTacToe", false, clientFirst);
            clientSecond.out.println("TicTacToe Win Right");
            server.OverwritingStats("TicTacToe", true, clientSecond);
            gameField.gameOverMessage = "Player Right WIN!!!";
        }
        if(client == clientSecond){
            clientFirst.out.println("TicTacToe Win Left");
            server.OverwritingStats("TicTacToe", true, clientFirst);
            clientSecond.out.println("TicTacToe Win Left");
            server.OverwritingStats("TicTacToe", false, clientSecond);
            gameField.gameOverMessage = "Player Left WIN!!!";
        }
    }

    public void startGameTicTacToe(){
        startGameTicTacToe st = new startGameTicTacToe(clientFirst, clientSecond);
        st.start();
    }

    class startGameTicTacToe extends Thread{
        Server.clientThread player1;
        Server.clientThread player2;

        public startGameTicTacToe(Server.clientThread player1, Server.clientThread player2){
            this.player1 = player1;
            this.player2 = player2;
        }

        public void run() {
            MainForm gameForm = new MainForm();
            gameField = gameForm.getfild();
            MainGameField.linesCount = 3;
            gameField.startNewGame();
            gameField.gameMode = 1;
            player1.out.println("Start game TicTacToe online Left");//левый
            player2.out.println("Start game TicTacToe online Right");//правый
            while(!endGame){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //проверяем прислал ли пользователь свой ход и отправляем второму поле(походу нахуй не надо)
                if (gameField.player1.win() & !gameForm.flagGameResultSended) {
                    try {
                        gameForm.flagGameResultSended = true;
                        System.out.println("Player Left WIN!!!");
                        gameField.gameOver = true;
                        endGame = true;
                        endGame(clientSecond);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (gameField.isFieldFull() && !gameField.player1.win() && !gameField.player2.win()
                        & !gameForm.flagGameResultSended) {
                    gameForm.flagGameResultSended = true;
                    gameField.gameOver = true;
                    endGame = true;
                    gameField.gameOverMessage = "DRAW!!!";
                    player1.out.println("Draw");
                    player2.out.println("Draw");
                }
                if (gameField.player2.win() & !gameForm.flagGameResultSended) {
                    try {
                        gameForm.flagGameResultSended = true;
                        System.out.println("Player Right WIN!!!");
                        gameField.gameOver = true;
                        endGame(clientFirst);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (gameField.isFieldFull() && !gameField.player2.win() && !gameField.player1.win()
                        & !gameForm.flagGameResultSended) {
                    gameForm.flagGameResultSended = true;
                    gameField.gameOver = true;
                    endGame = true;
                    gameField.gameOverMessage = "DRAW!!!";
                    player1.out.println("Draw");
                    player2.out.println("Draw");
                }
                if(mesFromLeftTicTacToe){ //если левый прислал сообщение заполняем поле и отправляем правому его ход
                    String[] str = FromLeftTicTacToe.split(" ");
                    player2.out.println("Move " + str[2] + " " + str[3]);
                    gameField.cell[Integer.parseInt(str[2])][Integer.parseInt(str[3])]= gameField.player1.getSign();
                    gameField.repaint();
                    mesFromLeftTicTacToe = false;
                }
                if(mesFromRightTicTacToe){ //на клиенте необходимо сделать отправку хода в формате left + " " + x + " " + y
                    String[] str = FromRightTicTacToe.split(" ");
                    player1.out.println("Move " + str[2] + " " + str[3]);
                    gameField.cell[Integer.parseInt(str[2])][Integer.parseInt(str[3])]= gameField.player2.getSign();
                    gameField.repaint();
                    mesFromRightTicTacToe = false;
                }
            }
        }
    }
}

