package TicTacToeOnline;


import TicTacToeOffline.MainGameFieldOffline;
import com.company.Window;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

import static com.company.Paths.*;

public class MainForm extends JFrame {
    private MainGameField gameField;

    public MainForm(Window window) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        window.ClearPanel();
        window.playTicTacToeMusic("Start");
        gameField = MainGameField.getInstance();
        gameField.setBounds(550,200,452,452);
        gameField.setOpaque(false);
        window.add(gameField);

        JButton btnEnd = new JButton();
        window.CreateBtn(btnEnd,pathBtnBack,pathBtnBackOver,pathBtnBackClick,575,675,404,114);

        window.SetBackground();
        window.repaint();
        btnEnd.addActionListener(e -> {
            MainGameFieldOffline.getInstance().removeAll();
            try {
                gameField = null;
                window.playTicTacToeMusic("Stop");
                window.playMainMusic("Start");
                window.OnlineOffline("Tic");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
                ioException.printStackTrace();
            }
        });
        window.setVisible(true);
    }

    public MainGameField getfild() {
        return gameField;
    }
}
