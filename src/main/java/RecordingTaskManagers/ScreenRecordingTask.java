package RecordingTaskManagers;

import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;

public class ScreenRecordingTask extends TimerTask {
    AWTSequenceEncoder encoder;
    Robot robot;
    Rectangle dimensions;
    BufferedImage image;

    public ScreenRecordingTask(AWTSequenceEncoder enc, Rectangle rectangle) throws AWTException {
        encoder = enc;
        dimensions = rectangle;
        robot = new Robot();
    }
    @Override
    public void run() {


        image = robot.createScreenCapture(dimensions);
        try {
            encoder.encodeImage(image);
            System.out.println("encoding...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
