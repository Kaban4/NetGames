package TicTacToe;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {
    private MainGameField gameField;
    public boolean flagGameResultSended = false;

    public MainForm() {
        setTitle("XO game GUI");
        setBounds(300, 300, 455, 525);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameField = MainGameField.getInstance();
        // Создаём панель для кнопок
        JPanel buttonPanel = new JPanel(new GridLayout());
        add(gameField, BorderLayout.CENTER);
    }

    public MainGameField getfild() {
        return gameField;
    }

    public void setGameField(MainGameField gf){
        gameField = gf;
    }
}
