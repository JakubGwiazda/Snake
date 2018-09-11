package snakev6;

import java.io.File;
import java.io.FileNotFoundException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;

public class Music {

    private static Clip clipTlo, clipJablko, clipBomba, clipKoniecGry;
    private File sTlo, sJablko, sBomba, sKoniecGry;

    public Music() throws FileNotFoundException {

        sTlo = new File("dzwiek_w_tle 2.wav");
        sJablko = new File("zjedzenieJablka.wav");
        sBomba = new File("zjedzenieBomby.wav");
        sKoniecGry = new File("koniecGry.wav");

    }

    public void dzwiekTla() {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(sTlo);
            clipTlo = AudioSystem.getClip();
            clipTlo.open(ais);
            clipTlo.start();
            clipTlo.loop(LOOP_CONTINUOUSLY);
            clipTlo.setFramePosition(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void zjedzenieJablka() {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(sJablko);
            clipJablko = AudioSystem.getClip();
            clipJablko.open(ais);
            clipJablko.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wyciszMyzykeTla() {

        clipTlo.stop();

    }

    public void odciszMuzykeTla() {
        clipTlo.start();

    }

    public void zjedzenieBomby() {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(sBomba);
            clipBomba = AudioSystem.getClip();
            clipBomba.open(ais);
            clipBomba.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void koniecGryDzwiek() {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(sKoniecGry);
            clipKoniecGry = AudioSystem.getClip();
            clipKoniecGry.open(ais);
            clipKoniecGry.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
