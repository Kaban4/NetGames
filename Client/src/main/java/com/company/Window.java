package com.company;

import PongOnline.*;
import PongOffline.PongOffline;
import SeaBattleOffline.SeaFightOffline;
import SeaBattleOnline.SeaBattle;
import TicTacToeOffline.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static com.company.Paths.*;

public class Window extends JFrame implements KeyListener {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private final Color myBlue;
    private Clip clipMainSound;
    private Clip clipStatsSound;
    private Clip clipPongSound;
    private Clip clipSeaBattleSound;
    private Clip clipTicTacToeSound;
    public PongOffline pongOffline;
    public Pong pongOnline;
    public SeaBattle seaBattle;
    private SeaFightOffline seaFightOffline;
    private MainFormOffline gameForm;
    private String registerString = "";
    private String loginString = "";
    private String nickReqString = "";
    private String keyRoom = "";
    public boolean logout = false;
    public boolean regigisterRequest = false;
    public boolean loginRequest = false;
    public boolean statsRequest = false;
    public boolean nickRequest = false;
    public boolean createPrivatePong = false;
    public boolean createPrivateSea = false;
    public boolean createPrivateTic = false;
    public boolean createPublicPong = false;
    public boolean createPublicSea = false;
    public boolean createPublicTic = false;
    public boolean connectPong = false;
    public boolean connectSea = false;
    public boolean connectTic = false;
    public boolean joinPong = false;
    public boolean joinSea = false;
    public boolean joinTic = false;
    public boolean outGamePong = false;
    public boolean outGameSea = false;
    public boolean outGameTic = false;
    public boolean quit = false;

    public Window() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super();
        myBlue = new Color(0, 228, 230);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        setUndecorated(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        playMainMusic("Start");
        LoginRegistration();
    }

    public void ClearPanel(){
        getContentPane().removeAll();
        repaint();
    }

    public void LoginRegistration(){
        ClearPanel();

        JButton btnLogin = new JButton();
        CreateBtn(btnLogin, pathBtnLogin, pathBtnLoginOver, pathBtnLoginClick,560,400,404,114);

        JButton btnRegister = new JButton();
        CreateBtn(btnRegister, pathBtnRegister, pathBtnRegisterOver, pathBtnRegisterClick,560,550,404,114);

        JButton btnQuit = new JButton();
        CreateBtn(btnQuit, pathBtnQuit, pathBtnQuitOver, pathBtnQuitClick,560,700,404,114);

        SetBackground();

        btnLogin.addActionListener(e -> Login());

        btnQuit.addActionListener(e -> Quit());

        btnRegister.addActionListener(e -> Registration());

        //Показать фрейм
        setVisible(true);
        repaint();
    }

    private void Login() {
        ClearPanel();

        JLabel nameStr = new JLabel("Login:");
        nameStr.setFont(new Font("Serif", Font.PLAIN, 50));
        nameStr.setBounds(510, 400, 250, 100);
        add(nameStr);

        JLabel passwordStr = new JLabel("Password:");
        passwordStr.setFont(new Font("Serif", Font.PLAIN, 50));
        passwordStr.setBounds(510, 500, 250, 50);
        add(passwordStr);

        JTextField login = new JTextField();
        login.setBounds(510+250, 440, 250, 30);
        add(login);

        JPasswordField password = new JPasswordField();
        password.setBounds(510+250, 520, 250, 30);
        password.setEchoChar('*');
        add(password);

        JButton btnBack = new JButton();
        CreateBtn(btnBack,pathBtnBackSmall,pathBtnBackOverSmall,pathBtnBackClickSmall,504,600,244,72);

        JButton btnGo = new JButton();
        CreateBtn(btnGo,pathBtnGo,pathGoOver,pathGoClick,804,600,244,72);

        btnBack.addActionListener(e -> LoginRegistration());

        btnGo.addActionListener(e -> {
            String log = login.getText();
            String pass = new String(password.getPassword());
            if(log.length() > 15){
                Error("Login must not exceed 15 characters!");
            }
            else if(pass.length() > 15){
                Error("Password must not exceed 15 characters!");
            }
            else if(log.length() < 5){
                Error("Login must be more than 4 characters!");
            }
            else if(pass.length() < 5){
                Error("Password must be more than 4 characters!");
            }
            else{
                loginString = log + " " + pass;
                loginRequest = true;
            }
        });

        SetBackground();
        repaint();
    }

    private void Registration() {
        ClearPanel();

        JLabel nameStr = new JLabel("Login:");
        nameStr.setFont(new Font("Serif", Font.PLAIN, 50));
        nameStr.setBounds(510, 300, 250, 100);
        add(nameStr);

        JLabel passwordStr = new JLabel("Password:");
        passwordStr.setFont(new Font("Serif", Font.PLAIN, 50));
        passwordStr.setBounds(510, 400, 250, 50);
        add(passwordStr);

        JTextField login = new JTextField();
        login.setBounds(510+250, 340, 250, 30);
        add(login);

        JPasswordField password = new JPasswordField();
        password.setBounds(510+250, 420, 250, 30);
        password.setEchoChar('*');
        add(password);

        JButton btnBack = new JButton();
        CreateBtn(btnBack, pathBtnBackSmall, pathBtnBackOverSmall, pathBtnBackClickSmall,510,500,244,72);
        add(btnBack);

        JButton btnSave = new JButton();
        CreateBtn(btnSave, pathBtnSave, pathSaveOver, pathSaveClick,810,500,244,72);
        add(btnSave);

        btnBack.addActionListener(e -> LoginRegistration());

        btnSave.addActionListener(e -> {
            String log = login.getText();
            if(log.length() > 15){
                System.out.println(log.length());
                Error("Login must not exceed 15 characters!");
            }
            char[] reg = password.getPassword();
            if(reg.length > 15){
                Error("Password must not exceed 15 characters!");
            }
            StringBuilder tmp = new StringBuilder();
            for (char c : reg) tmp.append(c);
            registerString = log + " " + tmp.toString();
            regigisterRequest = true;
        });

        SetBackground();
        repaint();
    }

    public void SetBackground(){
        setLayout(null);
        ImageIcon img = new ImageIcon(pathBackground);
        JLabel background = new JLabel("", img,JLabel.CENTER);
        background.setBounds(0,0,1620,900);
        add(background);
    }

    public void SelectingGame(){
        ClearPanel();

        JButton btnPong = new JButton();
        CreateBtn(btnPong, pathBtnPong, pathBtnPongOver, pathBtnPongClick,560,250,404,114);

        JButton btnSeaBattle = new JButton();
        CreateBtn(btnSeaBattle, pathBtnSea, pathBtnSeaOver, pathBtnSeaClick,560,375,404,114);

        JButton btnTicTacToe = new JButton();
        CreateBtn(btnTicTacToe, pathBtnTic, pathBtnTicOver, pathBtnTicClick,560,500,404,114);

        JButton btnBack = new JButton();
        CreateBtn(btnBack, pathBtnBack, pathBtnBackOver, pathBtnBackClick,560,625,404,114);

        btnPong.addActionListener(e -> OnlineOffline("Pong"));

        btnSeaBattle.addActionListener(e -> OnlineOffline("Sea"));

        btnTicTacToe.addActionListener(e -> OnlineOffline("Tic"));

        btnBack.addActionListener(e -> Menu());

        SetBackground();
        repaint();
    }

    public void Stats(String stats){
        ClearPanel();
        String[] str = stats.split(" ");

        JLabel pong = new JLabel("Pong");
        pong.setFont(new Font("Serif", Font.PLAIN, 80));
        pong.setBounds(200,200,250,120);
        add(pong);

        JLabel pongWin = new JLabel("Win : " + str[3]);
        pongWin.setFont(new Font("Serif", Font.PLAIN, 40));
        pongWin.setForeground(Color.GREEN);
        pongWin.setBounds(220,400,200,60);
        add(pongWin);

        JLabel pongLose = new JLabel("Lose : " + str[4]);
        pongLose.setFont(new Font("Serif", Font.PLAIN, 40));
        pongLose.setForeground(Color.RED);
        pongLose.setBounds(220,500,200,60);
        add(pongLose);

        JLabel SeaBattle = new JLabel("Sea Battle");
        SeaBattle.setFont(new Font("Serif", Font.PLAIN, 80));
        SeaBattle.setBounds(550,200,500,120);
        add(SeaBattle);

        JLabel seaBattleWin = new JLabel("Win : " + str[5]);
        seaBattleWin.setFont(new Font("Serif", Font.PLAIN, 40));
        seaBattleWin.setForeground(Color.GREEN);
        seaBattleWin.setBounds(650,400,200,60);
        add(seaBattleWin);

        JLabel seaBattleLose = new JLabel("Lose : " + str[6]);
        seaBattleLose.setFont(new Font("Serif", Font.PLAIN, 40));
        seaBattleLose.setForeground(Color.RED);
        seaBattleLose.setBounds(650,500,200,60);
        add(seaBattleLose);

        JLabel TicTacToe = new JLabel("Tic-Tac-Toe");
        TicTacToe.setFont(new Font("Serif", Font.PLAIN, 80));
        TicTacToe.setBounds(1050,200,700,120);
        add(TicTacToe);

        JLabel ticTacToeWin = new JLabel("Win : " + str[7]);
        ticTacToeWin.setFont(new Font("Serif", Font.PLAIN, 40));
        ticTacToeWin.setForeground(Color.GREEN);
        ticTacToeWin.setBounds(1200,400,200,60);
        add(ticTacToeWin);

        JLabel ticTacToeLose = new JLabel("Lose : " + str[8]);
        ticTacToeLose.setFont(new Font("Serif", Font.PLAIN, 40));
        ticTacToeLose.setForeground(Color.RED);
        ticTacToeLose.setBounds(1200,500,200,60);
        add(ticTacToeLose);

        JButton btnBack = new JButton();
        CreateBtn(btnBack,pathBtnBack,pathBtnBackOver,pathBtnBackClick,575,525,404,414);

        btnBack.addActionListener(e -> Menu());

        btnBack.addActionListener(e -> {
            statsRequest = true;
            try {
                playStatsMusic("Stop");
                playMainMusic("Start");
                Menu();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
                ioException.printStackTrace();
            }
        });
        SetBackground();
        repaint();
    }

    public void Menu(){
        ClearPanel();

        JButton btnSelectGame = new JButton();
        CreateBtn(btnSelectGame, pathBtnSelectGame, pathBtnSelectGameOver, pathBtnSelectGameClick,280,300,404,114);

        JButton btnStats = new JButton();
        CreateBtn(btnStats, pathBtnStats, pathBtnStatsOver, pathBtnStatsClick,280,425,404,114);

        JButton btnChangeName = new JButton();
        CreateBtn(btnChangeName, pathBtnChangeName, pathBtnChangeNameOver, pathBtnChangeNameClick,780,300,404,114);

        JButton btnLogout = new JButton();
        CreateBtn(btnLogout, pathBtnLogout, pathBtnLogoutOver, pathBtnLogoutClick,780,425,404,114);

        JButton btnQuit = new JButton();
        CreateBtn(btnQuit, pathBtnQuit, pathBtnQuitOver, pathBtnQuitClick,560,625,404,114);

        btnSelectGame.addActionListener(e -> SelectingGame());

        btnStats.addActionListener(e -> {
            statsRequest = true;
            try {
                playMainMusic("Stop");
                playStatsMusic("Start");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
                ioException.printStackTrace();
            }
        });

        btnChangeName.addActionListener(e -> ChangeLogin());

        btnLogout.addActionListener(e -> {
            logout = true;
            LoginRegistration();
        });

        btnQuit.addActionListener(e -> Quit());

        SetBackground();
        repaint();
    }

    public void OnlineOffline(String nameOfGame){
        ClearPanel();

        JButton btnOnline = new JButton();
        CreateBtn(btnOnline, pathBtnOnline, pathBtnOnlineOver, pathBtnOnlineClick,560,300,404,114);

        JButton btnOffline = new JButton();
        CreateBtn(btnOffline, pathBtnOffline, pathBtnOfflineOver, pathBtnOfflineClick,560,425,404,114);

        JButton btnBack = new JButton();
        CreateBtn(btnBack, pathBtnBack, pathBtnBackOver, pathBtnBackClick,560,600,404,114);

        add(btnOnline);
        add(btnOffline);

        btnOnline.addActionListener(e -> StartLobby(nameOfGame));

        btnOffline.addActionListener(e -> {
            try {
                if (nameOfGame.equals("Pong")) {
                    BotDifficulty();
                }
                if (nameOfGame.equals("Sea")) {
                    playMainMusic("Stop");
                    playSeaBattleMusic("Start");
                    seaFightOffline = SeaFightOffline.getInstance();
                    seaFightOffline.playGame(this);
                }
                if (nameOfGame.equals("Tic")) {
                    playMainMusic("Stop");
                    playTicTacToeMusic("Start");
                    gameForm = null;
                    gameForm = new MainFormOffline(this);
                }
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
                ioException.printStackTrace();
            }
        });

        btnBack.addActionListener(e -> SelectingGame());
        SetBackground();
        repaint();
    }

    public void BotDifficulty(){
        ClearPanel();

        JLabel jLabelAILevel = new JLabel("Выберете уровень AI:");
        jLabelAILevel.setFont(new Font("Verdana", Font.PLAIN, 50));
        jLabelAILevel.setBounds(500,280,800,100);
        add(jLabelAILevel);

        JRadioButton radioBtnEasy = new JRadioButton("Easy");
        radioBtnEasy.setFont(new Font("Verdana", Font.PLAIN, 50));
        radioBtnEasy.setBounds(700,380,400,100);
        radioBtnEasy.setOpaque(false);
        add(radioBtnEasy);
        radioBtnEasy.setSelected(true);

        JRadioButton radioBtnMedium = new JRadioButton("Medium");
        radioBtnMedium.setFont(new Font("Verdana", Font.PLAIN, 50));
        radioBtnMedium.setBounds(700,480,400,100);
        radioBtnMedium.setOpaque(false);
        add(radioBtnMedium);
        radioBtnMedium.setSelected(false);

        JRadioButton radioBtnHard = new JRadioButton("Hard");
        radioBtnHard.setFont(new Font("Verdana", Font.PLAIN, 50));
        radioBtnHard.setBounds(700,580,400,100);
        radioBtnHard.setOpaque(false);
        add(radioBtnHard);
        radioBtnHard.setSelected(false);

        final int[] level = {0};

        radioBtnEasy.addActionListener(e -> {
            if (radioBtnEasy.isSelected()) {
                radioBtnMedium.setSelected(false);
                radioBtnHard.setSelected(false);
                level[0] = 0;
            }
        });

        radioBtnMedium.addActionListener(e -> {
            if (radioBtnMedium.isSelected()) {
                radioBtnEasy.setSelected(false);
                radioBtnHard.setSelected(false);
                level[0] = 1;

            }
        });

        radioBtnHard.addActionListener(e -> {
            if (radioBtnHard.isSelected()) {
                radioBtnEasy.setSelected(false);
                radioBtnMedium.setSelected(false);
                level[0] = 2;
            }
        });

        JButton btnPlay = new JButton();
        CreateBtn(btnPlay,pathBtnPlay,pathBtnPlayOver, pathBtnPlayClick,835,720,250,100);

        btnPlay.addActionListener(e -> {
            try {
                playMainMusic("Stop");
                playPongMusic("Start");
                pongOffline = null;
                pongOffline = new PongOffline(this, level[0]);
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
                ioException.printStackTrace();
            }
        });

        JButton btnBack = new JButton();
        CreateBtn(btnBack,pathBtnBackSmall,pathBtnBackOverSmall,pathBtnBackClickSmall,485,720,244,72);

        btnBack.addActionListener(e -> OnlineOffline("Pong"));

        SetBackground();
        repaint();
    }

    public void StartLobby(String nameOfGame){
        ClearPanel();

        JButton btnConnect = new JButton();
        CreateBtn(btnConnect, pathBtnConnect, pathBtnConnectOver, pathBtnConnectClick,560,300,404,114);

        JButton btnCreate = new JButton();
        CreateBtn(btnCreate, pathBtnCreate, pathBtnCreateOver, pathBtnCreateClick,560,425,404,114);

        JButton btnJoin = new JButton();
        CreateBtn(btnJoin, pathBtnJoin, pathBtnJoinOver, pathBtnJoinClick,560,550,404,114);

        JButton btnBack = new JButton();
        CreateBtn(btnBack, pathBtnBack, pathBtnBackOver, pathBtnBackClick,560,675,404,114);

        btnConnect.addActionListener(e -> ConnectLobby(nameOfGame));

        btnCreate.addActionListener(e -> PublicPrivate(nameOfGame));

        btnJoin.addActionListener(e -> JoinLobby(nameOfGame));

        btnBack.addActionListener(e -> OnlineOffline(nameOfGame));

        SetBackground();
        repaint();
    }

    public void ConnectLobby(String nameOfGame){
        if(nameOfGame.equals("Pong"))
            connectPong = true;
        if(nameOfGame.equals("Sea"))
            connectSea = true;
        if(nameOfGame.equals("Tic"))
            connectTic = true;

    }

    public void PublicPrivate(String nameOfGame){
        ClearPanel();

        JButton btnPrivate = new JButton();
        CreateBtn(btnPrivate, pathBtnPrivate, pathBtnPrivateOver, pathBtnPrivateClick,560,350,404,114);

        JButton btnPublic = new JButton();
        CreateBtn(btnPublic, pathBtnPublic, pathBtnPublicOver, pathBtnPublicClick,560,500,404,114);

        JButton btnBack = new JButton();
        CreateBtn(btnBack, pathBtnBack, pathBtnBackOver, pathBtnBackClick,560,650,404,114);


        btnBack.addActionListener(e -> StartLobby(nameOfGame));

        btnPrivate.addActionListener(e -> PrivateRoom(nameOfGame));

        btnPublic.addActionListener(e -> {
            System.out.println("Public");
            PublicRoom(nameOfGame);
        });

        SetBackground();
        repaint();
    }

    public void PrivateRoom(String nameOfGame){
        if(nameOfGame.equals("Pong")){
            createPrivatePong = true;
        }
        if(nameOfGame.equals("Sea")){
            createPrivateSea = true;
        }
        if(nameOfGame.equals("Tic")){
            createPrivateTic = true;
        }

        JLabel balanceWaiting = new JLabel("Waiting players");
        balanceWaiting.setFont(new Font("Serif", Font.PLAIN, 100));
        balanceWaiting.setBounds(460,300,600,100);
        add(balanceWaiting);
    }

    public void PublicRoom(String nameOfGame){
        if(nameOfGame.equals("Pong")){
            createPublicPong = true;
        }
        if(nameOfGame.equals("Sea")){
            System.out.println("True");
            createPublicSea = true;
        }
        if(nameOfGame.equals("Tic")){
            createPublicTic = true;
        }

        JLabel balanceWaiting = new JLabel("Waiting players");
        balanceWaiting.setFont(new Font("Serif", Font.PLAIN, 100));
        balanceWaiting.setBounds(460,300,600,100);
        add(balanceWaiting);
    }

    public void JoinLobby(String nameOfGame){
        ClearPanel();

        JLabel nameStr = new JLabel("Room key:");
        nameStr.setFont(new Font("Serif", Font.PLAIN, 50));
        nameStr.setBounds(250, 350, 350, 100);
        add(nameStr);

        JTextField inputKeyRoom = new JTextField();
        inputKeyRoom.setBounds(650, 390, 250, 30);
        add(inputKeyRoom);

        JButton btnJoin = new JButton();
        CreateBtn(btnJoin, pathBtnJoin, pathBtnJoinOver, pathBtnJoinClick,1000,380,404,114);

        JButton btnBack = new JButton();
        CreateBtn(btnBack, pathBtnBack, pathBtnBackOver, pathBtnBackClick,560,625,404,114);

        btnJoin.addActionListener(e -> {
            keyRoom = inputKeyRoom.getText();
            if(nameOfGame.equals("Pong"))
                joinPong = true;
            if(nameOfGame.equals("Sea"))
                joinSea = true;
            if(nameOfGame.equals("Tic"))
                joinTic = true;
            System.out.println("Join = " + keyRoom);
        });
        btnBack.addActionListener(e -> StartLobby(nameOfGame));
        SetBackground();
    }

    public void ChangeLogin(){
        ClearPanel();

        JLabel balanceLabel = new JLabel("Сетевые игры");
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 100));
        balanceLabel.setBounds(460,100,600,100);
        add(balanceLabel);

        JLabel nameStr = new JLabel("New nickname:");
        nameStr.setFont(new Font("Serif", Font.PLAIN, 50));
        nameStr.setBounds(250, 350, 350, 100);
        add(nameStr);

        JTextField login = new JTextField();
        login.setBounds(650, 390, 250, 30);
        add(login);

        JButton btnSave = new JButton();
        CreateBtn(btnSave,pathBtnSave,pathSaveOver,pathSaveClick,1000,380,244,72);

        JButton btnBack = new JButton();
        CreateBtn(btnBack, pathBtnBack, pathBtnBackOver, pathBtnBackClick,560,625,404,114);

        btnSave.addActionListener(e -> {
            String log = login.getText();
            if(log.length() > 15){
                Error("Login must not exceed 15 characters!");
            }
            else if(log.length() < 5){
                Error("Login must be more than 4 characters!");
            }
            else{
                nickReqString = log;
                nickRequest = true;
                System.out.println("log = " + log);
            }
        });

        btnBack.addActionListener(e -> Menu());
        SetBackground();
        repaint();
    }

    public void Waiting(String game){
        ClearPanel();
        JLabel waiting = new JLabel("Waiting opponent...");
        waiting.setFont(new Font("Serif", Font.PLAIN, 80));
        waiting.setBounds(450,400,800,120);
        add(waiting);

//        JButton back = new JButton();
//        CreateBtn(back, pathBtnBack, pathBtnBackOver, pathBtnBackClick,560,650,404,114);

        //back.addActionListener(e -> PublicPrivate(game));
        SetBackground();
        repaint();
    }

    public void WaitingPrivate(String game, int key){
        ClearPanel();
        JLabel waiting = new JLabel("Waiting opponent...");
        waiting.setFont(new Font("Serif", Font.PLAIN, 80));
        waiting.setBounds(450,250,800,100);
        add(waiting);

        JLabel keyRoom = new JLabel("Your room key: " + key);
        keyRoom.setFont(new Font("Serif", Font.PLAIN, 80));
        keyRoom.setBounds(500,450,800,100);
        add(keyRoom);

        JButton back = new JButton();
        CreateBtn(back, pathBtnBack, pathBtnBackOver, pathBtnBackClick,560,650,404,114);

        back.addActionListener(e -> PublicPrivate(game));
        SetBackground();
        repaint();
    }

    public void Quit(){
        quit = true;
        dispose();
    }

    public void Error(String error){
        JFrame err = new JFrame();
        err.setBounds(575,350,350,150);
        JLabel textError = new JLabel(error);

        textError.setFont(new Font("Serif", Font.PLAIN, 15));
        textError.setBounds(50,10,300,40);
        err.add(textError);

        JButton btnOk = new JButton("Ok");
        btnOk.setFont(new Font("Serif", Font.PLAIN, 15));
        btnOk.setBounds(120,60,100,40);
        btnOk.setBackground(myBlue);
        err.add(btnOk);

        btnOk.addActionListener(e -> err.dispose());

        err.setLayout(null);
        err.setVisible(true);
    }

    public void CreateBtn(JButton btn, String path, String pathOver, String pathClick, int x, int y, int w, int h){
        btn.setIcon(new ImageIcon(path));
        btn.setRolloverIcon(new ImageIcon(pathOver));
        btn.setPressedIcon(new ImageIcon(pathClick));
        btn.setMargin(new Insets(0,0,0,0));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setBounds(x,y,w,h);
        add(btn);
    }

    public String getRegisterString() {
        return registerString;
    }

    public String getLoginString() {
        return loginString;
    }

    public String getKeyRoom(){
        return keyRoom;
    }

    public void setKeyRoom(String key){
        keyRoom = key;
    }

    public String getNickReqString(){ return nickReqString;}

    public void playMainMusic(String state) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(state.equals("Start")){
            File f = new File(Paths.pathMainMusic);
            DataLine.Info info = new DataLine.Info(Clip.class, AudioSystem.getAudioFileFormat(f).getFormat());
            clipMainSound = (Clip) AudioSystem.getLine(info);
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            clipMainSound.open(ais);

            FloatControl gainControl = (FloatControl) clipMainSound.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(0.0001f));

            clipMainSound.start();
            clipMainSound.loop(Clip.LOOP_CONTINUOUSLY);
        }
        if(state.equals("Stop")){
            clipMainSound.stop();
            clipMainSound.setFramePosition(0);
        }
    }

    public void playStatsMusic(String state) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(state.equals("Start")){
            File f = new File(pathStatsMusic);
            DataLine.Info info = new DataLine.Info(Clip.class, AudioSystem.getAudioFileFormat(f).getFormat());
            clipStatsSound = (Clip) AudioSystem.getLine(info);
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            clipStatsSound.open(ais);

            FloatControl gainControl = (FloatControl) clipStatsSound.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(0.2f));

            clipStatsSound.start();
            clipStatsSound.loop(Clip.LOOP_CONTINUOUSLY);
        }
        if(state.equals("Stop")){
            clipStatsSound.stop();
            clipStatsSound.setFramePosition(0);
        }
    }

    public void playPongMusic(String state) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(state.equals("Start")){
            File f = new File(pathPongMusic);
            DataLine.Info info = new DataLine.Info(Clip.class, AudioSystem.getAudioFileFormat(f).getFormat());
            clipPongSound = (Clip) AudioSystem.getLine(info);
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            clipPongSound.open(ais);

            FloatControl gainControl = (FloatControl) clipPongSound.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(0.2f));

            clipPongSound.start();
            clipPongSound.loop(Clip.LOOP_CONTINUOUSLY);
        }
        if(state.equals("Stop")){
            clipPongSound.stop();
            clipPongSound.setFramePosition(0);
        }
    }

    public void playSeaBattleMusic(String state) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(state.equals("Start")){
            File f = new File(pathSeaMusic);
            DataLine.Info info = new DataLine.Info(Clip.class, AudioSystem.getAudioFileFormat(f).getFormat());
            clipSeaBattleSound = (Clip) AudioSystem.getLine(info);
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            clipSeaBattleSound.open(ais);

            FloatControl gainControl = (FloatControl) clipSeaBattleSound.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(0.1f));

            clipSeaBattleSound.start();
            clipSeaBattleSound.loop(Clip.LOOP_CONTINUOUSLY);
        }
        if(state.equals("Stop")){
            clipSeaBattleSound.stop();
            clipSeaBattleSound.setFramePosition(0);
        }
    }

    public void playTicTacToeMusic(String state) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(state.equals("Start")){
            File f = new File(pathTicMusic);
            DataLine.Info info = new DataLine.Info(Clip.class, AudioSystem.getAudioFileFormat(f).getFormat());
            clipTicTacToeSound = (Clip) AudioSystem.getLine(info);
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            clipTicTacToeSound.open(ais);

            FloatControl gainControl = (FloatControl) clipTicTacToeSound.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(0.05f));

            clipTicTacToeSound.start();
            clipTicTacToeSound.loop(Clip.LOOP_CONTINUOUSLY);
        }
        if(state.equals("Stop")){
            clipTicTacToeSound.stop();
            clipTicTacToeSound.setFramePosition(0);
        }
    }

    public void PongEndGame(String result) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        ClearPanel();
        pongOnline.stop();
        JLabel label;
        if(result.equals("Win"))
            label = new JLabel("You win!");
        else
            label = new JLabel("You lose!");
        label.setFont(new Font("Verdana", Font.PLAIN, 90));
        label.setBounds(550,200,500,500);
        label.setLayout(null);
        add(label);
        JButton ok = new JButton();
        CreateBtn(ok, pathBtnOk, pathBtnOkOver, pathBtnOkClick,560,650,404,114);
        add(ok);

        ok.addActionListener(e -> {
            try {
                playPongMusic("Stop");
                playMainMusic("Start");
                OnlineOffline("Pong");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
                ioException.printStackTrace();
            }
        });
        SetBackground();
        repaint();
    }

    public void SeaEndGame(String result) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Thread.sleep(200);
        ClearPanel();
        seaBattle = null;
        JLabel label;
        if(result.equals("Win"))
            label = new JLabel("You win!");
        else
            label = new JLabel("You lose!");
        label.setFont(new Font("Verdana", Font.PLAIN, 90));
        label.setBounds(550,200,500,500);
        label.setLayout(null);
        add(label);
        JButton ok = new JButton();
        CreateBtn(ok, pathBtnOk, pathBtnOkOver, pathBtnOkClick,560,650,404,114);
        add(ok);

        ok.addActionListener(e -> {
            try {
                playSeaBattleMusic("Stop");
                playMainMusic("Start");
                OnlineOffline("Sea");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
                ioException.printStackTrace();
            }
        });
        SetBackground();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(pongOffline != null) {
            PongOffline.b.getP1Paddle().keyPressed(e);
            PongOffline.b.getP2Paddle().keyPressed(e);
        }
        if(pongOnline != null) {
            if(pongOnline.getPlayer().equals("Right"))
                pongOnline.b.getP2Paddle().keyPressed(e);
            if(pongOnline.getPlayer().equals("Left"))
                pongOnline.b.getP1Paddle().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(pongOffline != null) {
            PongOffline.b.getP1Paddle().keyReleased(e);
            PongOffline.b.getP2Paddle().keyReleased(e);
        }
        if(pongOnline != null) {
            if(pongOnline.getPlayer().equals("Right"))
                pongOnline.b.getP2Paddle().keyReleased(e);
            if(pongOnline.getPlayer().equals("Left"))
                pongOnline.b.getP1Paddle().keyReleased(e);
        }
    }
}