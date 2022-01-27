package SeaBattleOffline;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.company.Paths.*;

public class CanvasOffline {
    JPanel player1Field;
    JPanel player2Field;
    JPanel panel;
    static JLabel textLabel;
    private final ImageIcon alive = new ImageIcon(pathAlive);
    private final ImageIcon killed = new ImageIcon(pathKilled);
    private final ImageIcon injured = new ImageIcon(pathInjured);
    private final ImageIcon missed = new ImageIcon(pathMissed);
    private final ImageIcon nothing = new ImageIcon(pathNull);
    private static CanvasOffline instance;

    public CanvasOffline() throws HeadlessException {
        if(SeaFightOffline.getInstance().window != null)
            SeaFightOffline.getInstance().window.ClearPanel();
        Color myBlue = new Color(0, 228, 230);

        JButton btnBack = new JButton();
        SeaFightOffline.getInstance().window.CreateBtn(btnBack, pathBtnBack,pathBtnBackOver,pathBtnBackClick,
                300,700,404,114);

        btnBack.addActionListener(e -> {
            SeaFightOffline.getInstance().window.OnlineOffline("Sea");
            try {
                SeaFightOffline.getInstance().stop();
            } catch (InterruptedException | UnsupportedAudioFileException | IOException | LineUnavailableException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        panel = new JPanel();
        panel.setBounds(150,50,1220,600);
        initializeFields();

        textLabel = new JLabel("Мой ход");

        JButton newGameButton = new JButton();
        SeaFightOffline.getInstance().window.CreateBtn(newGameButton,pathBtnNewGame,
                pathBtnNewGameOver,pathBtnNewGameClick,800,700,404,114);
        newGameButton.addActionListener(SeaFightOffline.getInstance());

        SeaFightOffline.getInstance().window.add(newGameButton);

        SeaFightOffline.getInstance().window.add(panel);
        SeaFightOffline.getInstance().window.SetBackground();
        SeaFightOffline.getInstance().window.repaint();
    }

    public void initializeFields(){
        player1Field = new JPanel();
        player1Field.setBounds(0,0,panel.getWidth()/2,panel.getHeight());
        player2Field = new JPanel();
        player2Field.setBounds(panel.getWidth()/2+10,0,panel.getWidth()/2,panel.getHeight());

        initializeField(player1Field, SeaFightOffline.computerPlayerOffline.getField(), 1);
        initializeField(player2Field, SeaFightOffline.humanPlayerOffline.getField(), 2);

        panel.add(player1Field);
        panel.add(player2Field);
    }

    /**
     * функция создает отображаемую игровую панель игрового поля,
     * заполняя отображаемую игровую панель ячейками из матрицы
     * игрового поля
     * @param jPanel панель игрового поля
     * @param fieldOffline матрица игрового поля
     */
    private void initializeField(JPanel jPanel, FieldOffline fieldOffline, int player){
        if (fieldOffline == null)
            return;
        jPanel.setLayout(null);
        CellOffline[][] cells = fieldOffline.getCells();
        if(player == 1) {
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    cells[j][i].setIcon(nothing);
                    cells[j][i].setBounds(((i * (panel.getWidth()-20) / 20)), j * panel.getHeight() / 10,
                            (panel.getWidth()-20) / 20, panel.getHeight() / 10);
                    jPanel.add(cells[j][i]);
                }
            }
        }
        if(player == 2) {
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    cells[j][i].setIcon(nothing);
                    cells[j][i].setBounds(((i * (panel.getWidth()-20) / 20)), j * panel.getHeight() / 10,
                            (panel.getWidth()-20) / 20, panel.getHeight() / 10);
                    jPanel.add(cells[j][i]);
                }
            }
        }
    }

    /** Функция отрисовывает поля игроков после каждого шага*/
    public void draw(){
        FieldOffline humanFieldOffline = SeaFightOffline.humanPlayerOffline.getField();
        FieldOffline computerFieldOffline = SeaFightOffline.computerPlayerOffline.getField();

        updateField(humanFieldOffline, false);
        updateField(computerFieldOffline, true);
    }

    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        instance = null;
    }
    /**
     * Функция обновляет отображение поля игрока
     * @param fieldOffline поле игрока
     * @param computerField true, если в обработку передается поле компьютерного игрока, иначе false
     */
    private void updateField(FieldOffline fieldOffline, boolean computerField){
        if (fieldOffline == null)
            return;
        CellOffline[][] cells = fieldOffline.getCells();
        for (int i = FieldOffline.minXY; i <= FieldOffline.maxXY; i++) {
            for (int j = FieldOffline.minXY; j <= FieldOffline.maxXY ; j++) {
                CellOffline cell = cells[j][i];
                if (computerField)
                    updateComputerCell(cell);
                else
                    updateHumanCell(cell);
            }
        }
    }

    /**
     * Функция обновляет отображение конкретной ячейки на поле компьютера
     * @param cell ячейка
     */
    private void updateComputerCell(CellOffline cell){
        CellOffline.CellState state = cell.getState();
        if (state != null)
            switch (state) {
                case NULL -> cell.setIcon(nothing);
                case INJURED -> cell.setIcon(injured);
                case KILLED -> cell.setIcon(killed);
                case MISSED -> cell.setIcon(missed);
            }
    }

    /**
     * Функция обновляет отображение конкретной ячейки на поле человека
     * @param cell ячейка
     */
    private void updateHumanCell(CellOffline cell){
        CellOffline.CellState state = cell.getState();
        if (state != null)
            switch (state) {
                case NULL -> cell.setIcon(nothing);
                case MISSED -> cell.setIcon(missed);
                case ALIVE -> cell.setIcon(alive);
                case INJURED -> cell.setIcon(injured);
                case KILLED -> cell.setIcon(killed);
            }
    }


    /**
     * Функция для получения экземпляра класса Canvas
     * @return экземпляр класса Canvas
     */
    public static CanvasOffline getInstance(){
        if (instance == null)
            instance = new CanvasOffline();
        return instance;
    }
}