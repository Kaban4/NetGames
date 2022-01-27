package TicTacToeOffline;

import com.company.Window;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static com.company.Paths.*;

public class MainFormOffline {
    public MainFormOffline(Window window) {
        window.ClearPanel();
        Color myBlue = new Color(0, 228, 230);

        MainGameFieldOffline gameField = MainGameFieldOffline.getInstance();
        gameField.setBounds(550,200,452,452);
        gameField.setOpaque(false);
        window.add(gameField);

        JButton btnStart = new JButton();
        window.CreateBtn(btnStart, pathBtnNewGameSmall,pathBtnNewGameOverSmall,pathBtnNewGameClickSmall,500,675,244,72);

        JButton btnEnd = new JButton();
        window.CreateBtn(btnEnd, pathBtnBackSmall, pathBtnBackOverSmall,pathBtnBackClickSmall,800,675,244,72);

        window.add(btnStart);
        window.add(btnEnd);
        window.SetBackground();
        window.repaint();
        btnEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGameFieldOffline.getInstance().removeAll();
                try {
                    window.playTicTacToeMusic("Stop");
                    window.playMainMusic("Start");
                    window.OnlineOffline("Tic");
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(btnStart.getText());
                GameSettingsFormOffline gameSettingsForm = new GameSettingsFormOffline();
            }
        });
        window.setVisible(true);
    }
}
