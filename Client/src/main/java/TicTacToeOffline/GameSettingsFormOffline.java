package TicTacToeOffline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GameSettingsFormOffline extends JFrame {

    GameSettingsFormOffline gameSettingsForm = this;

    public GameSettingsFormOffline() {
        setTitle("Настройки игры");
        setBounds(150, 300, 220, 190);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(null);
        setUndecorated(true);
        setBackground(new Color(10, 228, 230));

        JLabel jLabelAILevel = new JLabel("Выберете уровень AI:");
        jLabelAILevel.setBounds(5,5,190,15);
        add(jLabelAILevel);

        JRadioButton radioBtnEasy = new JRadioButton("Easy");
        radioBtnEasy.setBounds(5,30,190,15);
        add(radioBtnEasy);
        radioBtnEasy.setSelected(true);

        JRadioButton radioBtnMedium = new JRadioButton("Medium");
        radioBtnMedium.setBounds(5,55,190,15);
        add(radioBtnMedium);
        radioBtnMedium.setSelected(false);

        JRadioButton radioBtnHard = new JRadioButton("Hard");
        radioBtnHard.setBounds(5,80,190,15);
        add(radioBtnHard);
        radioBtnHard.setSelected(false);

        final int[] level = {0};

        radioBtnEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioBtnEasy.isSelected()) {
                    radioBtnMedium.setSelected(false);
                    radioBtnHard.setSelected(false);
                    level[0] = 0;

                }
            }
        });

        radioBtnMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioBtnMedium.isSelected()) {
                    radioBtnEasy.setSelected(false);
                    radioBtnHard.setSelected(false);
                    level[0] = 1;

                }
            }
        });

        radioBtnHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioBtnHard.isSelected()) {
                    radioBtnEasy.setSelected(false);
                    radioBtnMedium.setSelected(false);
                    level[0] = 1;

                }
            }
        });


        JLabel jLabelLinesCount = new JLabel("Размер поля (по умолчанию 3 на 3): ");
        jLabelLinesCount.setBounds(5,105,215,15);
        add(jLabelLinesCount);
        JTextField jTextFieldLinesCount = new JTextField();
        jTextFieldLinesCount.setBounds(75,125,70,20);
        jTextFieldLinesCount.setMaximumSize(new Dimension(100, 20));
        add(jTextFieldLinesCount);
        JButton jButtonSetSettings = new JButton("Начать игру!");
        jButtonSetSettings.setBounds(45,150,130,30);
        add(jButtonSetSettings);
        setVisible(true);

        jButtonSetSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGameFieldOffline gameField = MainGameFieldOffline.getInstance();
                if (jTextFieldLinesCount.getText().isEmpty()) {
                    gameField.linesCount = 3;
                }
                else {
                    try {
                        gameField.linesCount = Integer.parseInt(jTextFieldLinesCount.getText());
                    }
                    catch (NumberFormatException ex) {
                        System.out.println("Необходимо ввести целое число!");
                    }
                }
                gameField.startNewGame();
                gameField.gameMode = 2;
                gameField.aiLevel = level[0];
                gameSettingsForm.setVisible(false);
            }
        });
    }
}