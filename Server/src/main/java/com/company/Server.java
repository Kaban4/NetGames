package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Server {
    private final ServerSocket serverSocket;
    private final ArrayList<clientThread> users;
    private final ArrayList<roomPong> roomPongs;
    private final ArrayList<roomSeaBattle> roomSeaBattles;
    private final ArrayList<roomTicTacToe> roomTicTacToes;
    private final FileWriter fileLog;
    private final FileWriter fileUsers;
    private final String pathFileUsers;
    private int keyRoomPong = 0;
    private int keyRoomSea = 0;
    private int keyRoomTic = 0;
    public ArrayList<String> onlineUsers;

    public Server(int port) throws IOException {
        String pathFileLog = "D:\\java_projects\\Test\\Server\\src\\com\\company\\logFile.txt";
        pathFileUsers = "D:\\java_projects\\Test\\Server\\src\\com\\company\\Users.txt";
        fileLog = new FileWriter(pathFileLog, false);
        fileUsers = new FileWriter(pathFileUsers,true);
        serverSocket = new ServerSocket(port);
        onlineUsers = new ArrayList<>();
        users = new ArrayList<>();
        roomPongs = new ArrayList<>();
        roomSeaBattles = new ArrayList<>();
        roomTicTacToes = new ArrayList<>();
        fileLog.write("Server started\n");
        fileLog.flush();
        System.out.println("Server started");
        ServerStart(this);
    }

    public void ServerStart(Server server){
        class AcceptThread extends Thread {
            public void run() {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        clientThread cliThread = new clientThread(clientSocket, server);
                        synchronized (users) {
                            users.add(cliThread);
                            cliThread.out.println("Connected");
                            System.out.println(users.size());
                        }
                        fileLog.write("Client connected\n");
                        fileLog.flush();
                        cliThread.start();
                    } catch (IOException ioe) {
                        try {
                            fileLog.write("Error to connect client!\n");
                            fileLog.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        AcceptThread at = new AcceptThread();
        at.start();
    }

    public synchronized void OverwritingStats(String game, boolean win, clientThread player1) throws IOException {
        Scanner sc = new Scanner(new FileInputStream(pathFileUsers));
        StringBuilder sb = new StringBuilder();
        while(sc.hasNextLine()){
            String stud = sc.nextLine();
            String[] mass = stud.split(" ");
            if (player1.login.equals(mass[0])){
                if(game.equals("Pong")){
                    if(win)
                        mass[2] = Integer.toString(Integer.parseInt(mass[2]) + 1);
                    if(!win)
                        mass[3] = Integer.toString(Integer.parseInt(mass[3]) + 1);
                }
                if(game.equals("SeaBattle")){
                    if(win)
                        mass[4] = Integer.toString(Integer.parseInt(mass[4]) + 1);
                    if(!win)
                        mass[5] = Integer.toString(Integer.parseInt(mass[5]) + 1);
                }
                if(game.equals("TicTacToe")){
                    if(win)
                        mass[6] = Integer.toString(Integer.parseInt(mass[6]) + 1);
                    if(!win)
                        mass[7] = Integer.toString(Integer.parseInt(mass[7]) + 1);
                }
            }
            sb.append(mass[0]).append(" ").append(mass[1]).append(" ").append(mass[2])
                    .append(" ").append(mass[3]).append(" ").append(mass[4])
                    .append(" ").append(mass[5]).append(" ").append(mass[6]).append(" ").append(mass[7]).append("\n");
        }
        ClearTheFile();
        FileWriter wr = new FileWriter(pathFileUsers, true);
        wr.write(sb.toString());
        wr.flush();
    }

    public void Registration(String message, clientThread client) throws IOException {
        String[] str = message.split(" ");
        String login = str[1];
        String password = str[2];
        Scanner sc = new Scanner(new FileInputStream(pathFileUsers));
         while(sc.hasNextLine()){
            String tmp = sc.nextLine();
            str = tmp.split(" ");
            if(str[0].equals(login)) {
                fileLog.write("Registration false");
                fileLog.flush();
                client.out.println("Registration false");
                System.out.println("Reg false");
                return;
            }
        }
        FileWriter wr = new FileWriter(pathFileUsers, true);
        wr.write("\n" + login + " " + password + " 0 " + "0 " + "0 " + "0 " + "0 " + "0");
        wr.flush();
        fileLog.write("Registration true "+ login + " " + password + " 0 " + "0 " + "0 " + "0 " + "0 " + "0");
        fileLog.flush();
        onlineUsers.add(login);
        client.out.println("Registration true " + login);
    }

    public void Login(String message, clientThread client) throws IOException {
        String[] str = message.split(" ");
        String login = str[1];
        String password = str[2];
        Scanner sc = new Scanner(new FileInputStream(pathFileUsers));
        while(sc.hasNextLine()) {
            String tmp = sc.nextLine();
            str = tmp.split(" ");
            if(str[0].equals(login) && str[1].equals(password)){
                if(onlineUsers.contains(login)){
                    fileLog.write("Error! Client " + login + " online!");
                    fileLog.flush();
                    client.out.println("Error! Client online!");
                    System.out.println("Error! Client online!");
                    return;
                }
                client.login = login;
                onlineUsers.add(login);
                System.out.println("Add to online : " + onlineUsers.toString());
                fileLog.write("Login true " + login + " " + password);
                fileLog.flush();
                client.out.println("Login true " + login + " " + password);
                System.out.println("Login true " + login + " " + password);
                return;
            }
        }
        fileLog.write("Login false " + login + " " + password);
        fileLog.flush();
        client.out.println("Login false");
        System.out.println("Log false");
    }

    public void Stats(String message, clientThread client) throws IOException {
        String[] str = message.split(" ");
        String login = str[1];
        Scanner sc = new Scanner(new FileInputStream(pathFileUsers));
        while(sc.hasNextLine()){
            String tmp = sc.nextLine();
            System.out.println(Arrays.toString(str));
            str = tmp.split(" ");
            if(str[0].equals(login)){
                fileLog.write("Stats request");
                fileLog.flush();
                client.out.println("Stats " + tmp);
                System.out.println("Stats " + tmp);
                return;
            }
        }
        System.out.println("Stats false");
    }

    private void ChangeNick(String message, clientThread client ) throws IOException {
        String []str = message.split(" ");
        String oldLogin = str[1];
        String newLogin = str[2];
        Scanner sc = new Scanner(new FileInputStream(pathFileUsers));
        StringBuilder sb = new StringBuilder();
        while(sc.hasNextLine()){
            String stud = sc.nextLine();
            String[] mass = stud.split(" ");
            if (newLogin.equals(mass[0])){
                fileLog.write("Change nickname - " + oldLogin+ " false");
                fileLog.flush();
                client.out.println("Change false");
                return;
            }
        }
        sc = new Scanner(new FileInputStream(pathFileUsers));
        while(sc.hasNextLine()){
            String stud = sc.nextLine();
            String[] mass = stud.split(" ");
            if (oldLogin.equals(mass[0]))
                mass[0] = newLogin;
            sb.append(mass[0]).append(" ").append(mass[1]).append(" ").append(mass[2])
                    .append(" ").append(mass[3]).append(" ").append(mass[4])
                    .append(" ").append(mass[5]).append(" ").append(mass[6]).append(" ").append(mass[7]).append("\n");
        }
        ClearTheFile();
        FileWriter wr = new FileWriter(pathFileUsers, true);
        wr.write(sb.toString());
        wr.flush();
        fileLog.write("Change nickname - " + oldLogin+ " true");
        fileLog.flush();
        client.out.println("Change true");
    }

    public void ClearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter(pathFileUsers, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }

    public synchronized void DeleteClient() throws IOException {
        users.removeIf(ct -> ct.socket.isClosed());
        System.out.println(users.size());
    }

    public class clientThread extends Thread {
        Socket socket;
        Server server;
        PrintWriter out;
        BufferedReader in;
        String login;

        clientThread(Socket s, Server server) throws IOException {
            socket = s;
            this.server = server;
            System.out.println("client connected " + s.getPort());
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }

        public void run() {
            String message;
            while(true){
                try {
                    Thread.sleep(3);
                    if ((message = in.readLine()) != null){
                        ////////////////TicTacToe/////////////////////
                        if(message.contains("TicTacToe Left")){
                            String[] str = message.split(" ");
                            int keyRoom = Integer.parseInt(str[4]);
                            System.out.println(message);
                            for (roomTicTacToe room : roomTicTacToes) {
                                if (room.getKey() == keyRoom) {
                                    room.FromLeftTicTacToe = message;
                                    room.mesFromLeftTicTacToe = true;
                                }
                            }
                        }
                        if(message.contains("TicTacToe Right")){
                            String[] str = message.split(" ");
                            int keyRoom = Integer.parseInt(str[4]);
                            System.out.println(message);
                            for (roomTicTacToe room : roomTicTacToes) {
                                if (room.getKey() == keyRoom) {
                                    room.FromRightTicTacToe = message;
                                    room.mesFromRightTicTacToe = true;
                                }
                            }
                        }
                        if (message.contains("Out Tic")) {
                            String[] str = message.split(" ");
                            System.out.println(message);
                            int keyRoom = Integer.parseInt(str[2]);
                            System.out.println(keyRoom);
                            for (roomTicTacToe room : roomTicTacToes) {
                                if (room.getKey() == keyRoom)
                                    room.endGame(this);
                            }
                        }
                        //////////////////Pong////////////////////////
                        if (message.contains("Pong Left")) {
                            String[] str = message.split(" ");
                            int keyRoom = Integer.parseInt(str[4]);
                            for (roomPong room : roomPongs) {
                                if (room.getKey() == keyRoom) {
                                    room.FromLeftPong = message;
                                    room.mesFromLeftPong = true;
                                }
                            }
                        }
                        if (message.contains("Pong Right")) {
                            String[] str = message.split(" ");
                            int keyRoom = Integer.parseInt(str[4]);
                            for (roomPong room : roomPongs) {
                                if (room.getKey() == keyRoom) {
                                    room.FromRightPong = message;
                                    room.mesFromRightPong = true;
                                }
                            }
                        }
                        if (message.contains("Out pong")) {
                            String[] str = message.split(" ");
                            System.out.println(message);
                            int keyRoom = Integer.parseInt(str[2]);
                            System.out.println(keyRoom);
                            for (roomPong room : roomPongs) {
                                if (room.getKey() == keyRoom)
                                    room.endGame(this);
                            }
                        }
                        ///////////////////Sea////////////////////////
                        if(message.contains("Turn")){
                            String[] str = message.split(" ");
                            int keyRoom = Integer.parseInt(str[3]);
                            for (roomSeaBattle room : roomSeaBattles) {
                                if (room.getKey() == keyRoom){
                                    room.flagMessageSea = true;
                                    room.mesSea = message;
                                }
                            }
                        }
                        if(message.contains("Out Sea")) {
                            String[] str = message.split(" ");
                            int keyRoom = Integer.parseInt(str[2]);
                            for (roomSeaBattle room : roomSeaBattles) {
                                if (room.getKey() == keyRoom)
                                    room.endGame(this);
                            }
                        }
                        //////////////////////////////////////////////
                        if(message.contains("Registration")){
                            System.out.println(message);
                            Registration(message, this);
                        }
                        if(message.contains("Login")){
                            System.out.println(message);
                            Login(message, this);
                        }
                        if(message.contains("Stats")){
                            System.out.println(message);
                            Stats(message, this);
                        }
                        if(message.contains("Change")){
                            System.out.println(message);
                            ChangeNick(message, this);
                        }
                        if(message.equals("Quit")){
                            fileLog.write("Client quit");
                            fileLog.flush();
                            System.out.println("Client quit");
                            onlineUsers.remove(login);
                            System.out.println("Deleted from online : " + onlineUsers.toString());
                            socket.close();
                            DeleteClient();
                        }
                        if(message.contains("Create"))  {
                            if(message.contains("public")){
                                if(message.contains("Pong")){
                                    fileLog.write("Crated Pong public room\n");
                                    fileLog.flush();
                                    roomPong rm = new roomPong(this, server, false, keyRoomPong++);
                                    roomPongs.add(rm);
                                    out.println("Waiting public Pong " + rm.getKey());
                                    System.out.println("Create public Pong");
                                }
                                if(message.contains("Sea")){
                                    fileLog.write("Crated Sea public room");
                                    fileLog.flush();
                                    roomSeaBattle rm = new roomSeaBattle(this, server, false, keyRoomSea++);
                                    roomSeaBattles.add(rm);
                                    out.println("Waiting public Sea " + rm.getKey());
                                    System.out.println("Create public Sea");
                                }
                                if(message.contains("Tic")){
                                    fileLog.write("Crated Tic public room");
                                    fileLog.flush();
                                    roomTicTacToe rm = new roomTicTacToe(this, server, false, keyRoomTic++);
                                    roomTicTacToes.add(rm);
                                    out.println("Waiting public Tic " + rm.getKey());
                                    System.out.println("Create public Tic");
                                }
                            }
                            if(message.contains("private")){
                                if (message.contains("Pong")) {
                                    roomPong rm = new roomPong(this, server, true, keyRoomPong++);
                                    roomPongs.add(rm);
                                    out.println("Waiting private Pong " + rm.getKey());
                                    System.out.println("Create private Pong " + rm.getKey());
                                }
                                if (message.contains("Sea")) {
                                    roomSeaBattle rm = new roomSeaBattle(this, server, true, keyRoomSea++);
                                    roomSeaBattles.add(rm);
                                    out.println("Waiting private Sea " + rm.getKey());
                                    System.out.println("Create private Sea " + rm.getKey());
                                }
                                if (message.contains("Tic")) {
                                    roomTicTacToe rm = new roomTicTacToe(this, server, true, keyRoomTic++);
                                    roomTicTacToes.add(rm);
                                    out.println("Waiting private Tic " + rm.getKey());
                                    System.out.println("Create private Tic " + rm.getKey());
                                }
                            }
                        }
                        if(message.contains("Connect")){
                            if (message.contains("Pong")) {
                                System.out.println("Connect Pong");
                                boolean flagFind = false;
                                for (roomPong room : roomPongs) {
                                    if (room.getClientFirst() == null & room.getClientSecond() == null) {
                                        roomPongs.remove(room);
                                    }
                                    if (room.getClientFirst() != null & room.getClientSecond() == null
                                            & !room.isLocked() & !room.getEndGame()) {
                                        room.setClientSecond(this);
                                        flagFind = true;
                                        room.getClientFirst().out.println("Start game Pong online Left " + room.getKey());
                                        room.getClientSecond().out.println("Start game Pong online Right " + room.getKey());
                                        room.startGamePong();
                                        System.out.println("startGame " + room.getKey());
                                        break;
                                    }
                                }
                                if (!flagFind) {
                                    fileLog.write("Error! Room didn't find!\n");
                                    fileLog.flush();
                                    out.println("Error! Room didn't find!");
                                    System.out.println("Error! Room didn't find!");
                                }
                            }
                            if (message.contains("Sea")) {
                                System.out.println("Connect Sea");
                                boolean flagFind = false;
                                for (roomSeaBattle room : roomSeaBattles) {
                                    if (room.getClientFirst() == null & room.getClientSecond() == null)
                                        roomSeaBattles.remove(room);
                                    if (room.getClientFirst() != null & room.getClientSecond() == null
                                            & !room.isLocked() & !room.getEndGame()) {
                                        room.setClientSecond(this);
                                        flagFind = true;
                                        room.getClientFirst().out.println("Start game Sea online Left " + room.getKey());
                                        room.getClientSecond().out.println("Start game Sea online Right " + room.getKey());
                                        room.startGameSea();
                                        System.out.println("startGame " + room.getKey());
                                        break;
                                    }
                                }
                                if (!flagFind) {
                                    fileLog.write("Error! Room didn't find!\n");
                                    fileLog.flush();
                                    out.println("Error! Room didn't find!");
                                    System.out.println("Error! Room didn't find!");
                                }
                            }
                            if (message.contains("Tic")) {
                                System.out.println("Connect TicTacToe");
                                boolean flagFind = false;
                                for (roomTicTacToe room : roomTicTacToes) {
                                    if (room.getClientFirst() == null & room.getClientSecond() == null)
                                        roomTicTacToes.remove(room);
                                    if (room.getClientFirst() != null & room.getClientSecond() == null
                                            & !room.isLocked() & !room.getEndGame()) {
                                        room.setClientSecond(this);
                                        flagFind = true;
                                        room.getClientFirst().out.println("Start game Tic online Left " + room.getKey());
                                        room.getClientSecond().out.println("Start game Tic online Right " + room.getKey());
                                        room.startGameTicTacToe();
                                        System.out.println("startGame " + room.getKey());
                                        break;
                                    }
                                }
                                if (!flagFind) {
                                    fileLog.write("Error! Room didn't find!\n");
                                    fileLog.flush();
                                    out.println("Error! Room didn't find!");
                                    System.out.println("Error! Room didn't find!");
                                }
                            }
                        }
                        if (message.contains("join")) {
                            if (message.contains("Pong")) {
                                String[] str = message.split(" ");
                                System.out.println("Join Pong " + Integer.parseInt(str[2]));
                                boolean flagFind = false;
                                for (roomPong room : roomPongs) {
                                    if (room.getClientFirst() == null & room.getClientSecond() == null)
                                        roomPongs.remove(room);
                                    if (room.getClientFirst() != null & room.getClientSecond() == null
                                            & room.isLocked() & !room.getEndGame() & room.getKey() == Integer.parseInt(str[2])) {
                                        room.setClientSecond(this);
                                        flagFind = true;
                                        room.getClientFirst().out.println("Start game Pong online Left " + room.getKey());//
                                        room.getClientSecond().out.println("Start game Pong online Right " + room.getKey());//
                                        room.startGamePong();
                                        System.out.println("startGame " + room.getKey());
                                        break;
                                    }
                                }
                                if (!flagFind) {
                                    fileLog.write("Error! Room didn't find!\n");
                                    fileLog.flush();
                                    out.println("Error! Room didn't find!");
                                    System.out.println("Error! Room didn't find!");
                                }
                            }
                            if (message.contains("Sea")) {
                                String[] str = message.split(" ");
                                System.out.println("Join Sea " + Integer.parseInt(str[2]));
                                boolean flagFind = false;
                                for (roomSeaBattle room : roomSeaBattles) {
                                    if (room.getClientFirst() == null & room.getClientSecond() == null)
                                        roomSeaBattles.remove(room);
                                    if (room.getClientFirst() != null & room.getClientSecond() == null
                                            & room.isLocked() & !room.getEndGame() & room.getKey() == Integer.parseInt(str[2])) {
                                        room.setClientSecond(this);
                                        flagFind = true;
                                        room.getClientFirst().out.println("Start game Sea online Left " + room.getKey());//
                                        room.getClientSecond().out.println("Start game Sea online Right " + room.getKey());//
                                        room.startGameSea();
                                        System.out.println("startGame " + room.getKey());
                                        break;
                                    }
                                }
                                if (!flagFind) {
                                    fileLog.write("Error! Room didn't find!\n");
                                    fileLog.flush();
                                    out.println("Error! Room didn't find!");
                                    System.out.println("Error! Room didn't find!");
                                }
                            }
                            if (message.contains("Tic")) {
                                String[] str = message.split(" ");
                                System.out.println("Join Tic " + Integer.parseInt(str[2]));
                                boolean flagFind = false;
                                for (roomTicTacToe room : roomTicTacToes) {
                                    if (room.getClientFirst() == null & room.getClientSecond() == null)
                                        roomTicTacToes.remove(room);
                                    if (room.getClientFirst() != null & room.getClientSecond() == null
                                            & room.isLocked() & !room.getEndGame() & room.getKey() == Integer.parseInt(str[2])) {
                                        room.setClientSecond(this);
                                        flagFind = true;
                                        room.getClientFirst().out.println("Start game Tic online Left " + room.getKey());//
                                        room.getClientSecond().out.println("Start game Tic online Right " + room.getKey());//
                                        room.startGameTicTacToe();
                                        System.out.println("startGame " + room.getKey());
                                        break;
                                    }
                                }
                                if (!flagFind) {
                                    fileLog.write("Error! Room didn't find!\n");
                                    fileLog.flush();
                                    out.println("Error! Room didn't find!");
                                    System.out.println("Error! Room didn't find!");
                                }
                            }
                            //out.println("Waiting");
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    try {
                        onlineUsers.remove(login);
                        socket.close();
                        DeleteClient();
                        break;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        }
    }
}