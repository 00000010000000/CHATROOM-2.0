package ServerClient.Message;

import java.io.ByteArrayOutputStream;
import javax.sound.sampled.AudioFormat;

public class VoiceUtil {
    protected static boolean isRecording = false;
    static ByteArrayOutputStream out;

    static AudioFormat getAudioFormat(){
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate,sampleSizeInBits,channels,signed,bigEndian);
        return format;
    }

    public static void setRecording(boolean flag){
        isRecording = flag;
    }

    public static boolean isRecording(){
        return isRecording;
    }
}
