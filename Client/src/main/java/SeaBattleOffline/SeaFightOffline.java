package SeaBattleOffline;
import com.company.Window;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class SeaFightOffline implements ActionListener, Runnable{
    private Thread lastGame;
    private boolean reset = false;
    public static ComputerPlayerOffline computerPlayerOffline;
    public static HumanPlayerOffline humanPlayerOffline;
    private static SeaFightOffline game;
    private boolean gamePlay = true;
    public Window window;


    public SeaFightOffline(){
        computerPlayerOffline = new ComputerPlayerOffline();
        humanPlayerOffline = new HumanPlayerOffline();
        humanPlayerOffline.setOpponent(computerPlayerOffline);
        computerPlayerOffline.setOpponent(humanPlayerOffline);
    }


    public void playGame(Window window){
        this.window = window;
        lastGame = new Thread(this);
        lastGame.start();
    }

    public void stop() throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        window.playSeaBattleMusic("Stop");
        window.playMainMusic("Start");
        game = null;
        CanvasOffline.getInstance().stop();
        gamePlay = false;
        lastGame.join();
    }


    public static SeaFightOffline getInstance(){
        if (game == null)
            game = new SeaFightOffline();
        return game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        reset = true;
        if (!lastGame.isAlive()){
            lastGame = new Thread(this);
            lastGame.start();
        }
    }

    @Override
    public void run() {
        CanvasOffline.getInstance().draw();
        MessageManagerOffline.getInstance().getStartMessage();
        boolean shootComanded = false;
        while(gamePlay){
            if (reset){
                reset = false;
                computerPlayerOffline.reset();
                humanPlayerOffline.reset();
                CanvasOffline.getInstance().draw();
                MessageManagerOffline.getInstance().getStartMessage();
            }
            if (computerPlayerOffline.moves() && !shootComanded) {
                computerPlayerOffline.shoot();
                shootComanded = true;
            } else {
                shootComanded = false;
            }
            if (computerPlayerOffline.getShipsToKill() == 0 || humanPlayerOffline.getShipsToKill() == 0) {
                MessageManagerOffline.getInstance().getWinMessage(humanPlayerOffline.getShipsToKill() == 0);
                break;
            }
            try {
                if(gamePlay)
                    Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
