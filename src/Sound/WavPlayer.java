package Sound;

import javax.sound.sampled.*;
import java.io.File;

public class WavPlayer extends Thread {
    static public final byte PICK = 0;
    static public final byte EAT = 1;
    static public final byte BACK = 2;
    static public final byte GO = 3;

    static private final File pick=new File("voice/pick.wav");
    static private final File go=new File("voice/go.wav");
    static private final File back=new File("voice/back.wav");
    static private final File eat=new File("voice/eat.wav");

    private final File file;

    public WavPlayer(byte flag) {
        switch (flag) {
            case GO -> this.file = go;
            case PICK -> this.file = pick;
            case BACK -> this.file = back;
            default -> this.file = eat;
        }
    }

    @Override
    public void run() {
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        auline.start();
        int nBytesRead;
        byte[] abbytes = new byte[512];
        try {
            while ((nBytesRead = audioInputStream.read(abbytes, 0, abbytes.length)) != -1) {
                if (nBytesRead >= 0) {
                    auline.write(abbytes, 0, nBytesRead);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            auline.drain();
            auline.close();
        }


    }
}