package SeaBattleOnline;

import com.company.Window;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.company.Paths.*;

public class SeaBattle {
    private final int[][] myField;
    private final int[][] enemyField;
    private final JPanel panel;
    private JPanel myPanel;
    private JPanel enemyPanel;
    private final JLabel[][] btnListMy;
    private final JButton[][] btnListEnemy;
    private final Color myBlue = new Color(0,228,230);
    private final Color myGreen = new Color(0,228,100);
    private final Color myYellow = new Color(225,225,0);
    private final Color myGrey = new Color(136,136,160);
    private boolean move = false;
    public boolean moved = false;
    public int coordX;
    public int coordY;

    public SeaBattle(Window window) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        window.ClearPanel();

        window.playSeaBattleMusic("Start");
        System.out.println("start play");

        panel = new JPanel();
        panel.setBounds(150,50,1220,600);
        window.add(panel);

        JButton btnBack = new JButton();
        window.CreateBtn(btnBack,pathBtnBack,pathBtnBackOver,pathBtnBackClick,575, 700,404,114);

        btnBack.addActionListener(e -> {
            try {
                window.playSeaBattleMusic("Stop");
                window.playMainMusic("Start");
                window.outGameSea = true;
                window.OnlineOffline("SeaBattle");
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        myField = new int[10][10];
        enemyField = new int[10][10];
        btnListMy = new JLabel[10][10];
        btnListEnemy = new JButton[10][10];
        for (int i = 0; i < 10;i++){
            for (int j = 0; j < 10;j++){
                myField[i][j] = 0;
                enemyField[i][j] = 0;
                btnListMy[i][j] = new JLabel();
                btnListEnemy[i][j] = new JButton();
            }
        }
        initializeFields();
        window.SetBackground();
        window.repaint();
    }

    public void setMyField(int x, int y, int val, String color) {
        myField[x][y] = val;
        //setBtnIcon(path, btnListMy[x][y]);
        btnListMy[x][y].setOpaque(true);
        if(color.equals("Green")){
            btnListMy[x][y].setBackground(myGreen);
            System.out.println("Green");
        }
        if(color.equals("Yellow")){
            btnListMy[x][y].setBackground(myYellow);
            System.out.println("Yellow");
        }
        if(color.equals("Grey")){
            btnListMy[x][y].setBackground(myGrey);
            System.out.println("Grey");
        }
        myPanel.repaint();
    }

    public void setEnemyField(int x, int y, int val, String color) {
        enemyField[x][y] = val;
        //setBtnIcon(path, btnListMy[x][y]);
        btnListEnemy[x][y].setOpaque(true);
        if(color.equals("Green")) {
            btnListEnemy[x][y].setBackground(myGreen);
            System.out.println("Green");
        }
        if(color.equals("Yellow")) {
            btnListEnemy[x][y].setBackground(myYellow);
            System.out.println("Green");
        }
        if(color.equals("Grey")) {
            btnListEnemy[x][y].setBackground(myGrey);
            System.out.println("Grey");
        }
        enemyPanel.repaint();
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void initializeFields(){
        myPanel = new JPanel();
        myPanel.setBounds(panel.getWidth()/2+10,0,panel.getWidth()/2, panel.getHeight());

        enemyPanel = new JPanel();
        enemyPanel.setBounds(0,0,panel.getWidth()/2, panel.getHeight());

        initializeMyPanel();
        initializeEnemyPanel();

        panel.add(myPanel);
        panel.add(enemyPanel);
    }

    private void initializeMyPanel() {
        myPanel.setLayout(null);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                btnListMy[i][j].setBounds(((i * (panel.getWidth() - 20) / 20)), j * panel.getHeight() / 10,
                        (panel.getWidth() - 20) / 20, panel.getHeight() / 10);
                btnListMy[i][j].setOpaque(true);
                //setBtnIcon(pathNull, btnListMy[i][j]);
                btnListMy[i][j].setBackground(myBlue);
                myPanel.add(btnListMy[i][j]);
            }
        }
    }

    private void initializeEnemyPanel() {
        enemyPanel.setLayout(null);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                btnListEnemy[i][j].setBounds(((i * (panel.getWidth() - 20) / 20)), j * panel.getHeight() / 10,
                        (panel.getWidth() - 20) / 20, panel.getHeight() / 10);
                btnListEnemy[i][j].setOpaque(true);
                //setBtnIcon(pathNull, btnListEnemy[i][j]);
                btnListEnemy[i][j].setBackground(myBlue);
                int finalI = i;
                int finalJ = j;
                btnListEnemy[i][j].addActionListener((java.awt.event.ActionEvent e) -> {
                    if(move & enemyField[finalI][finalJ] == 0) {
                        moved = true;
                        move = false;
                        coordX = finalI;
                        coordY = finalJ;
                    }
                });
                enemyPanel.add(btnListEnemy[i][j]);
            }
        }
    }
}
