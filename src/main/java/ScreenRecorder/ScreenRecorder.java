package ScreenRecorder;

import RecordingTaskManagers.CounterTask;
import RecordingTaskManagers.ScreenRecordingTask;
import RecordingTaskManagers.RecordingTimer;
import org.jcodec.api.awt.AWTSequenceEncoder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class ScreenRecorder {
    Rectangle dimensions;
    AWTSequenceEncoder encoder;
    CounterTask counterTask;
    ScreenRecordingTask recordingTask;
    public Timer timerForCounting;
    public Timer timerForRecording;
    File file;
    boolean isRecording = false;

    private void initiateFileAndScreenImage(String fileName) throws IOException {
        dimensions = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        file = new File(fileName + ".mp4");

        encoder = AWTSequenceEncoder.createSequenceEncoder(file,24/8);

    }

    public ScreenRecorder() throws IOException {
        initiateFileAndScreenImage("MyVideo");
    }


    public void startTimers(JLabel label,JTextPane text) throws AWTException {
        isRecording = true;
//        int framesSequence = 1000/24;
        int framesSequence = 2000;
        RecordingTimer.reset();

        timerForCounting = new Timer("TimeCounter");
        timerForRecording = new Timer("ScreenCapture");

        counterTask = new CounterTask(label);
        recordingTask = new ScreenRecordingTask(encoder,dimensions);
        text.setText(recordingTask.ImageData);

        timerForCounting.scheduleAtFixedRate(counterTask,0,1000);
        timerForRecording.scheduleAtFixedRate(recordingTask,0,framesSequence);
    }

    public void stopTimers() throws IOException {
        if (isRecording){
            RecordingTimer.stop();
            timerForCounting.cancel();
            timerForCounting.purge();

            timerForRecording.cancel();
            timerForRecording.purge();

            counterTask.cancel();
            recordingTask.cancel();

            encoder.finish();
            System.out.println("encoding finished");
            isRecording = false;
        }


    }



}
