package GUI;

import ScreenRecorder.ScreenRecorder;
import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame{
    private JPanel mainFrame;
    private JProgressBar progressBar1;
    private JButton btnStart;
    private JButton btnStop;
    private JTextPane textOutput;
    private JLabel lblEngagement;
    private JLabel lblTimer;

    ScreenRecorder recorder;

    public MainFrame() throws IOException {
        InitializeComponents();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        recorder = new ScreenRecorder();
        //System.setProperty("java.awt.headless", "false");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    recorder.startTimers(lblTimer);
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    recorder.stopTimers();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {

        MainFrame frame = new MainFrame();
        frame.setSize(400,400);
        frame.setContentPane(frame.mainFrame);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void InitializeComponents() {
        JPanel mainFrame = new JPanel();
        mainFrame.setVisible(true);
        mainFrame.setSize(200,200);
        JProgressBar progressBar1 = new JProgressBar();
        JButton btnStart = new JButton();
        JButton btnStop = new JButton();
        JLabel lblEngagement = new JLabel();
        JTextPane textOutput = new JTextPane();

    }
}
