package RecordingTaskManagers;

import AWSRekognition.ImageDataLables;
import FaceEyesRecognition.DetectAndDisplay;
import org.jcodec.api.awt.AWTSequenceEncoder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;

public class ScreenRecordingTask extends TimerTask {
    AWTSequenceEncoder encoder;
    Robot robot;
    Rectangle dimensions;
    BufferedImage image;
    public String ImageData;
    JTextPane paneToWrite;

    public ScreenRecordingTask(AWTSequenceEncoder enc, Rectangle rectangle, JTextPane text) throws AWTException {
        encoder = enc;
        dimensions = rectangle;
        paneToWrite = text;
        robot = new Robot();
    }
    @Override
    public void run() {

        image = robot.createScreenCapture(dimensions);
        try {

            ImageData = ImageDataLables.GetData(image);
            paneToWrite.setText(ImageData);
            encoder.encodeImage(DetectAndDisplay.ObjectDetection(image));
            System.out.println("encoding...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
