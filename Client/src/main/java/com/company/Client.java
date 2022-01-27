package com.company;

import PongOffline.PongOffline;
import PongOnline.*;
import SeaBattleOnline.SeaBattle;
import TicTacToeOnline.MainForm;
import TicTacToeOnline.MainGameField;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.Socket;

public class Client {
    private String player = "";
    private Window window;
    private boolean end = false;
    MainForm gameForm = null;
    MainGameField gameField = null;

    public Client(String args0, String args1){
        //String hostName = args0;
        String hostName = "localhost";
        //int portNumber = Integer.parseInt(args1);
        int portNumber = 9090;
        System.out.println("client started");
        try {
            Socket fromServer = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(fromServer.getOutputStream(), true);
            Stream str = new Stream(fromServer);
            str.start();
            while (!end){//считывание с консоли и отправка на серверs
                Thread.sleep(2);//оставить
                if(window!= null){
                    if(window.regigisterRequest) {
                        out.println("Registration " + window.getRegisterString());
                        window.regigisterRequest = false;
                        System.out.println("Registration " + window.getLoginString());
                    }
                    if(window.loginRequest) {
                        String string = "Login " + window.getLoginString();
                        out.println(string);
                        window.loginRequest = false;
                        System.out.println(string);
                    }
                    if(window.statsRequest) {
                        out.println("Stats " + str.login);
                        window.statsRequest = false;
                        System.out.println("Stats " + str.login);
                    }
                    if(window.nickRequest) {
                        out.println("Change " + str.login + " " + window.getNickReqString());
                        window.nickRequest = false;
                        System.out.println("Stats " + str.login + " " + window.getNickReqString());
                    }
                    if(window.quit) {
                        end = true;
                        out.println("Quit");
                        window.quit = false;
                        System.out.println("Quit");
                        System.exit(0);
                    }
                    if(window.logout) {
                        out.println("Logout");
                        window.logout = false;
                        System.out.println("Logout");
                    }
                    if(window.createPublicPong) {
                        out.println("Create public Pong");
                        window.createPublicPong = false;
                        System.out.println("Create public Pong");
                    }
                    if(window.createPublicSea) {
                        out.println("Create public Sea");
                        window.createPublicSea = false;
                        System.out.println("Create public Sea");
                    }
                    if(window.createPublicTic) {
                        out.println("Create public Tic");
                        window.createPublicTic = false;
                        System.out.println("Create public Tic");
                    }
                    if(window.createPrivatePong) {
                        out.println("Create private Pong");
                        window.createPrivatePong = false;
                        System.out.println("Create private Pong");
                    }
                    if(window.createPrivateSea) {
                        out.println("Create private Sea");
                        window.createPrivateSea = false;
                        System.out.println("Create private Sea");
                    }
                    if(window.createPrivateTic) {
                        out.println("Create private Tic");
                        window.createPrivateTic = false;
                        System.out.println("Create private Tic");
                    }
                    if(window.connectPong) {
                        out.println("Connect Pong");
                        window.connectPong = false;
                        System.out.println("Connect Pong");
                    }
                    if(window.connectSea) {
                        out.println("Connect Sea");
                        window.connectSea = false;
                        System.out.println("Connect Sea");
                    }
                    if(window.connectTic) {
                        out.println("Connect Tic");
                        window.connectTic = false;
                        System.out.println("Connect Tic");
                    }
                    if(window.joinPong) {
                        out.println("join Pong " + window.getKeyRoom());
                        window.joinPong = false;
                        System.out.println("join Pong");
                    }
                    if(window.joinSea) {
                        out.println("join Sea " + window.getKeyRoom());
                        window.joinSea = false;
                        System.out.println("join Sea");
                    }
                    if(window.joinTic) {
                        out.println("join Tic " + window.getKeyRoom());
                        window.joinTic = false;
                        System.out.println("join Tic");
                    }
                    ////понг
                    if(player.equals("Left") & this.window.pongOnline != null){
                        if(this.window.pongOnline.b.getP1().movement){
                            out.println("Pong Left " + window.pongOnline.b.getP1().getY()
                                    + " " + window.pongOnline.b.getP1().getYDirection()
                                    + " " + window.pongOnline.getKeyRoom());

                            window.pongOnline.b.getP1().movement = false;
                        }
                    }
                    if(player.equals("Right") & this.window.pongOnline != null){
                        if(window.pongOnline.b.getP2().movement){
                            out.println("Pong Right " + window.pongOnline.b.getP2().getY()
                                    + " " + window.pongOnline.b.getP2().getYDirection()
                                    + " " + window.pongOnline.getKeyRoom());


                            window.pongOnline.b.getP2().movement = false;
                        }
                    }
                    if(window.outGamePong){
                        out.println("Out pong " + window.getKeyRoom());
                        window.outGamePong = false;
                        System.out.println("Out pong " + window.getKeyRoom());
                    }
                    ////крестики
                    if(player.equals("Left") & gameField != null){
                        if(gameField.player1.ashot){
                            System.out.println("TicTacToe Left " + gameField.player1.ashotX + " " + gameField.player1.ashotY
                                    + " " + window.getKeyRoom() );
                            out.println("TicTacToe Left " + gameField.player1.ashotX + " " + gameField.player1.ashotY
                                    + " " + window.getKeyRoom() );
                            gameField.player1.ashot = false;
                        }
                    }
                    if(player.equals("Right") & gameField != null){
                        if(gameField.player1.ashot){
                            System.out.println("TicTacToe Right " + gameField.player1.ashotX + " " + gameField.player1.ashotY
                                    + " " + window.getKeyRoom() );
                            out.println("TicTacToe Right " + gameField.player1.ashotX + " " + gameField.player1.ashotY
                                    + " " + window.getKeyRoom() );
                            gameField.player1.ashot = false;
                        }
                    }
                    if(window.outGameTic){
                        out.println("Out Tic " + window.getKeyRoom());
                        window.outGameTic = false;
                    }
                    //морской бой
                    if(window.seaBattle != null){
                        if(window.seaBattle.moved) {
                            out.println("Turn " + window.seaBattle.coordX + " " + window.seaBattle.coordY + " " + window.getKeyRoom());
                            window.seaBattle.moved = false;
                        }
                        if(window.outGameSea){
                            out.println("Out Sea " + window.getKeyRoom());
                            window.outGameSea = false;
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException ignored) { }
    }

    class Stream extends Thread{
        private final BufferedReader in;
        private String login = "";

        Stream(Socket socket)  throws IOException{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run(){
            String message;
            try{
                while (!end) {//считывание с сервера и вывод в консоль клиента
                    if ((message = in.readLine()) != null) {
                        if (message.equals("Connected")){
                            System.out.println(message);
                            window = new Window();
                        }
                        if (message.contains("Registration ")) {
                            String[] str = message.split(" ");
                            System.out.println(message);
                            if(str[1].equals("false")){
                                window.Error("Login already exists");
                            }
                            if(str[1].equals("true")){
                                login = str[2];
                                window.Menu();
                            }
                        }
                        if (message.contains("Login ")) {
                            String[] str = message.split(" ");
                            System.out.println(message);
                            if(str[1].equals("false")){
                                window.Error("Invalid username or password");
                            }
                            if(str[1].equals("true")){
                                login = str[2];
                                //password = str[3];
                                window.Menu();
                            }
                        }
                        if (message.contains("Error! ")) {
                            System.out.println(message);
                            window.Error(message);
                        }
                        if (message.contains("Stats ")) {
                            window.Stats(message);
                        }
                        if (message.contains("Change ")) {
                            if(message.contains("false")){
                                window.Error("Login already exists!");
                            }
                            if(message.contains("true")) {
                                window.Error("Login changed successfully!");
                                login = window.getNickReqString();
                            }
                        }
                        if (message.contains("Waiting")) {
                            if(message.contains("public")){
                                if(message.contains("Pong")){
                                    window.Waiting("Pong");
                                    System.out.println("Waiting pong");
                                }
                                if(message.contains("Sea")){
                                    window.Waiting("Sea");
                                    System.out.println("Waiting Sea");
                                }
                                if(message.contains("Tic")){
                                    window.Waiting("Tic");
                                    System.out.println("Waiting Tic");
                                }
                            }
                            if(message.contains("private")){
                                if(message.contains("Pong")){
                                    String[] str = message.split(" ");
                                    int keyRoom = Integer.parseInt(str[3]);
                                    window.WaitingPrivate("Pong", keyRoom);
                                    System.out.println("Waiting pong " + keyRoom);
                                }
                                if(message.contains("Sea")){
                                    String[] str = message.split(" ");
                                    int keyRoom = Integer.parseInt(str[3]);
                                    window.WaitingPrivate("Sea", keyRoom);
                                    System.out.println("Waiting sea battle " + keyRoom);
                                }
                                if(message.contains("Tic")){
                                    String[] str = message.split(" ");
                                    int keyRoom = Integer.parseInt(str[3]);
                                    window.WaitingPrivate("Tic", keyRoom);
                                    System.out.println("Waiting TicTacToe " + keyRoom);
                                }
                            }
                        }

                        ////////////////////////пинг
                        if (message.contains("Start game Pong online ")) {
                            System.out.println(message);
                            String[] str = message.split(" ");
                            if (message.contains("Right"))
                                player = "Right";
                            if (message.contains("Left"))
                                player = "Left";
                            window.setKeyRoom(str[5]);
                            window.pongOnline = new Pong(player,window, str[5]);
                            System.out.println(player);
                        }
                        if (message.contains("Ball ")) {
                            String[] coordinate = message.split(" ");
                            window.pongOnline.b.setX(Integer.parseInt(coordinate[1]));
                            window.pongOnline.b.setY(Integer.parseInt(coordinate[2]));
                            window.pongOnline.b.setXDirection(Integer.parseInt(coordinate[3]));
                            window.pongOnline.b.setYDirection(Integer.parseInt(coordinate[4]));
                        }
                        if (message.contains("Score ")){
                            String[] str = message.split(" ");
                            window.pongOnline.b.setP1score(Integer.parseInt(str[1]));
                            window.pongOnline.b.setP2score(Integer.parseInt(str[2]));
                        }
                        if (message.contains("Paddle Player1 ")) {
                            String[] str = message.split(" ");
                            window.pongOnline.b.setPadY1(Integer.parseInt(str[2]));
                            window.pongOnline.b.setPadYDirection1(Integer.parseInt(str[3]));
                        }
                        if (message.contains("Paddle Player2 ")) {
                            String[] str = message.split(" ");
                            window.pongOnline.b.setPadY2(Integer.parseInt(str[2]));
                            window.pongOnline.b.setPadYDirection2(Integer.parseInt(str[3]));
                        }
                        if (message.equals("Pong win!")){
                            window.PongEndGame("Win");
                            System.out.println(message);
                        }
                        if (message.equals("Pong lose!")){
                            window.PongEndGame("Lose");
                            System.out.println(message);
                        }

                        ///////////////////////крестики
                        if (message.contains("Start game Tic online")) {
                            System.out.println("Start game Tic online!");
                            String[] str = message.split(" ");
                            window.setKeyRoom(str[5]);
                            gameForm = new MainForm(window);
                            gameField = gameForm.getfild();
                            MainGameField.linesCount = 3;
                            gameField.gameOver = false;
                            gameField.startNewGame();
                            gameField.firstPaint();
                            gameField.gameMode = 1;
                            if (message.contains("Right")) {
                                player = "Right";
                                gameField.player1.isShotReady = 1;
                            }
                            if (message.contains("Left")) {
                                player = "Left";
                                gameField.player1.isShotReady = 0;
                            }
                            System.out.println(player);
                        }
                        if (message.contains("Move ")) {
                            String[] str = message.split(" ");
                            gameField.cell[Integer.parseInt(str[1])][Integer.parseInt(str[2])]= gameField.player2.getSign();
                            gameField.player1.isShotReady = 1;
                            gameField.player2.isShotReady = 0;
                            gameField.repainting();
                        }
                        if (message.contains("TicTacToe Win ")){
                            String[] str = message.split(" ");
                            if(str[2].equals(player)) {
                                System.out.println("You WIN!!!");
                                gameField.gameOverMessage = "Win";
                            }
                            else {
                                System.out.println("You LOSE!!!");
                                gameField.gameOverMessage = "Lose";
                            }
                            gameField.gameOver = true;
                            gameField.repainting();
                            gameForm = null;
                            gameField = null;
                        }
                        if (message.contains("Draw")) {
                            gameField.gameOver = true;
                            gameField.gameOverMessage = "Draw";
                            System.out.println("Draw");
                            gameField.repainting();
                        }

                        ///////////////////////морской бой
                        if (message.contains("Start game Sea online")) {
                            System.out.println(message);
                            String[] str = message.split(" ");
                            window.setKeyRoom(str[5]);
                            window.seaBattle = new SeaBattle(window);
                            System.out.println("Start game Sea online!");
                        }
                        if (message.contains("MyField")){
                            String []str = message.split(" ");
                            for(int i = 1; i < 40;i+=2)
                                window.seaBattle.setMyField(Integer.parseInt(str[i]), Integer.parseInt(str[i + 1]), 2,"Green");
                        }
                        if (message.contains("Turn")){
                            window.seaBattle.setMove(true);
                        }
                        if (message.contains("You hit")){
                            String []str = message.split(" ");
                            window.seaBattle.setEnemyField(Integer.parseInt(str[2]), Integer.parseInt(str[3]),3,"Yellow");
                        }
                        if (message.contains("Your hit")){
                            String []str = message.split(" ");
                            window.seaBattle.setMyField(Integer.parseInt(str[2]), Integer.parseInt(str[3]),3,"Yellow");
                        }
                        if (message.contains("You miss")){
                            String []str = message.split(" ");
                            window.seaBattle.setEnemyField(Integer.parseInt(str[2]), Integer.parseInt(str[3]),1,"Grey");
                        }
                        if (message.contains("Your miss")){
                            String []str = message.split(" ");
                            window.seaBattle.setMyField(Integer.parseInt(str[2]), Integer.parseInt(str[3]),1,"Grey");
                        }
                        if (message.contains("Sea win")){
                            window.SeaEndGame("Win");
                            System.out.println(message);
                        }
                        if (message.contains("Sea lose")){
                            window.SeaEndGame("Lose");
                            System.out.println(message);
                        }
                    }
                }
            }
            catch(IOException ignored){ } catch (UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}