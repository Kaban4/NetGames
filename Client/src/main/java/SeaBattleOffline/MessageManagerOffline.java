package SeaBattleOffline;

import java.util.ArrayList;
import java.util.Random;


public class MessageManagerOffline {

    private static MessageManagerOffline instance;
    private ArrayList<String> onHumanMiss;
    private ArrayList<String> onHumanInjure;
    private ArrayList<String> onHumanKill;
    private ArrayList<String> onPCMiss;
    private ArrayList<String> onPCInjure;
    private ArrayList<String> onPCKill;
    private ArrayList<String> onPCWin;
    private ArrayList<String> onHumanWin;

    public static MessageManagerOffline getInstance(){
        if (instance == null)
            instance = new MessageManagerOffline();
        return instance;
    }

    public void getMessage(boolean human, PlayerOffline.ShootResult shootResult){
        if (shootResult == null) return;
        int index;
        Random random = new Random();
        if (human){
            switch (shootResult){
                case MISSED:
                    index = random.nextInt(onHumanMiss.size());
                    CanvasOffline.textLabel.setText(onHumanMiss.get(index));
                    break;
                case INJURED:
                    index = random.nextInt(onHumanInjure.size());
                    CanvasOffline.textLabel.setText(onHumanInjure.get(index));
                    break;
                case KILLED:
                    index = random.nextInt(onHumanKill.size());
                    CanvasOffline.textLabel.setText(onHumanKill.get(index));
                    break;
            }
        } else {
            switch (shootResult){
                case MISSED:
                    index = random.nextInt(onPCMiss.size());
                    CanvasOffline.textLabel.setText(onPCMiss.get(index));
                    break;
                case INJURED:
                    index = random.nextInt(onPCInjure.size());
                    CanvasOffline.textLabel.setText(onPCInjure.get(index));
                    break;
                case KILLED:
                    index = random.nextInt(onPCKill.size());
                    CanvasOffline.textLabel.setText(onPCKill.get(index));
                    break;
            }
        }
    }

    public void getWinMessage(boolean humanWin){
        Random random = new Random();
        int index;
        if (humanWin){
            index = random.nextInt(onHumanWin.size());
            CanvasOffline.textLabel.setText(onHumanWin.get(index));
        } else {
            index = random.nextInt(onPCWin.size());
            CanvasOffline.textLabel.setText(onPCWin.get(index));
        }
    }


    public void getStartMessage(){
        CanvasOffline.textLabel.setText("Стреляй");
    }

    private MessageManagerOffline(){
        initOnHumanInjure();
        initOnHumanKill();
        initOnHumanMiss();
        initOnHumanWin();
        initOnPCInjure();
        initOnPCKill();
        initOnPCMiss();
        initOnPCWin();
    }

    private void initOnHumanMiss(){
        onHumanMiss = new ArrayList<>();
        onHumanMiss.add("Вы промазали!");
        onHumanMiss.add("Вам не везет!");
        onHumanMiss.add("Косой!");
    }

    private void initOnHumanInjure(){
        onHumanInjure = new ArrayList<>();
        onHumanInjure.add("Вы ранили меня!");
        onHumanInjure.add("Больно!");
        onHumanInjure.add("Ты подстрелил мой черный зад!");
        onHumanInjure.add("Не надо, пожалуйста!");
    }

    private void initOnHumanKill(){
        onHumanKill = new ArrayList<>();
        onHumanKill.add("Вы потопили мой кораблик!");
        onHumanKill.add("Like a boss!");
    }


    private void initOnPCMiss(){
        onPCMiss = new ArrayList<>();
        onPCMiss.add("Я промазал!");
        onPCMiss.add("Сегодня тебе везет!");
    }

    private void initOnPCInjure(){
        onPCInjure = new ArrayList<>();
        onPCInjure.add("Я ранил вас!");
        onPCInjure.add("ХА! Больно?");
    }

    private void initOnPCKill(){
        onPCKill = new ArrayList<>();
        onPCKill.add("Я отправил ваш корабль кормить рыб!");
        onPCKill.add("Я позабочусь о твоих детях");
    }


    private void initOnPCWin(){
        onPCWin = new ArrayList<>();
        onPCWin.add("Тебя сделал твой комп, пожалуйся своей мамочке!");
        onPCWin.add("Я позабочусь о твоих детях");
        onPCWin.add("Может морской бой - это не для тебя?");
        onPCWin.add("Мой рандомный генератор работает играет в морской бой лучше тебя. Я выйграл.");
    }


    private void initOnHumanWin(){
        onHumanWin = new ArrayList<>();
        onHumanWin.add("Ладно, ладно, ты победил. Давай заново...");
        onHumanWin.add("Позаботься о моей жене...");
        onHumanWin.add("я всего лишь набор нулей и единиц. Ты одолел несуществующего соперника");
    }
}
