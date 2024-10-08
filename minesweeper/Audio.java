package Minesweeper;

import javazoom.jl.player.Player;
import java.io.*;
import java.util.Random;

//Класс для проигрывания аудио (only mp3) сопровождения при выигрыше/проигрыше
public class Audio extends Thread{
    static String s = "src\\Minesweeper\\Sounds_Minesweeper\\"; //path to audio files
    //static String Radiohead = s + "Radiohead_-_Karma_Police_47843438.mp3";
    //static String Lain_Trickster = s + "Lain - The Trickster Radiohead (AI COVER) [AMV] [TubeRipper.cc].wav";
    static String OH_MY_GOD = s + "oh-my-god-jo-jo.mp3";
    static String HOLY_SHIT = s + "holy-shit-jojo.mp3";
    static String SON_OF_A_BITCH = s + "joseph-son-of-a-bitch.mp3";
    static String NICE = s + "joseph-joestar-nice-101soundboards.mp3";
    static String YES_YES_YES = s + "yes-yes-yes-yes-yes.mp3";
    static String NO_NO_NO = s + "jotaro-no.mp3";
    //static String YES_I_AM = s + "";
    //static String METAL_PIPE = s + "metal pipe .mp3";

    //public static void main(String[] args) {}
    public static boolean win_or_lose;

    public Audio(boolean win_or_lose) {
        this.win_or_lose = win_or_lose;
    }

    //иницилизация метода многопоточного проигрывания
    //ДЕРЬМО ЕБАННОЕ НЕ РАБОТАЕТ ААА
    @Override
    public void run(){
        if(this.win_or_lose){
            playRandomSound_WinGame();
        }else{
            playRandomSound_GameOver();
        }
    }

    //метод проигрывания mp3 файла
    public static void playSound(String ss){
        try{
            File f = new File(ss);
            FileInputStream fi = new FileInputStream(f);
            BufferedInputStream bf = new BufferedInputStream(fi);
            try{
                Player p = new Player(bf);
                p.play();
            }catch(Exception e){
                System.out.println("FUCK YOU");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //проигрывание случайного звука при проигрыше
    public static void playRandomSound_GameOver(){
        int rand = new Random().nextInt(4);
        switch(rand){
            case 0:
                playSound(OH_MY_GOD);
                break;
            case 1:
                playSound(HOLY_SHIT);
                break;
            case 2:
                playSound(SON_OF_A_BITCH);
                break;
            case 3:
                playSound(NO_NO_NO);
                break;
        }
    }

    //проигрывание случайного звука при выигрыше
    public static void playRandomSound_WinGame(){
        int rand = new Random().nextInt(2);
        switch(rand){
            case 0:
                playSound(NICE);
                break;
            case 1:
                playSound(YES_YES_YES);
                break;
            /*case 2:
                playSound(YES_I_AM);
                break;*/
        }
    }
}