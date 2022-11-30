package RecordingTaskManagers;

import javax.swing.*;
import java.util.TimerTask;

public class CounterTask extends TimerTask {

    JLabel label;
    int timeInSeconds = 0;
    public CounterTask(JLabel lbl){
        label = lbl;
    }
    @Override
    public void run() {
        label.setText(""+timeInSeconds+" s");
        timeInSeconds++;
    }
}
