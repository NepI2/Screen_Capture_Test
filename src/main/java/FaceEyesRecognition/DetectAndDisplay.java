package FaceEyesRecognition;

import java.awt.image.BufferedImage;
import java.awt.image.ImageConsumer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;


public class DetectAndDisplay {
    public static CascadeClassifier faceCascade = new CascadeClassifier("haarcascade_eye_tree_eyeglasses.xml");
    public static CascadeClassifier eyesCascade = new CascadeClassifier("haarcascade_eye_tree_eyeglasses.xml");
    public static @NotNull Mat BufferedImage2Mat(BufferedImage image) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        System.out.println("the beginning of BufferedImage2Mat");
        //IMWRITE_PNG_BILEVEL
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMWRITE_EXR_TYPE);
    }


    public static BufferedImage Mat2BufferedImage(Mat matrix)throws IOException {
        MatOfByte mob=new MatOfByte();
        System.out.println("the beginning of imencode");

        Imgcodecs.imencode(".jpg", matrix, mob);
        System.out.println("the beginning of return");

        return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
    }
        public static BufferedImage detectAndDisplay(Mat frame, @NotNull CascadeClassifier faceCascade, CascadeClassifier eyesCascade) throws IOException {

            System.out.println("the beginning of detectAndDisplay");
            Mat frameGray = new Mat();
            Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BayerBGGR2GRAY);
            Imgproc.equalizeHist(frameGray, frameGray);
            // -- Detect faces
            MatOfRect faces = new MatOfRect();

            System.out.println("the beginning of detectMultiScale");
            faceCascade.detectMultiScale(frameGray, faces);
            List<Rect> listOfFaces = faces.toList();
            for (Rect face : listOfFaces) {
                System.out.println("the beginning of loop");
                Point center = new Point(face.x + face.width / 2, face.y + face.height / 2);
                Imgproc.ellipse(frame, center, new Size(face.width / 2, face.height / 2), 0, 0, 360,
                        new Scalar(255, 0, 255));
                Mat faceROI = frameGray.submat(face);
                // -- In each face, detect eyes
                MatOfRect eyes = new MatOfRect();
                System.out.println("the beginning of detectMultiscale");
                eyesCascade.detectMultiScale(faceROI, eyes);
                List<Rect> listOfEyes = eyes.toList();
                for (Rect eye : listOfEyes) {
                    Point eyeCenter = new Point(face.x + eye.x + eye.width / 2, face.y + eye.y + eye.height / 2);
                    int radius = (int) Math.round((eye.width + eye.height) * 0.25);
                    Imgproc.circle(frame, eyeCenter, radius, new Scalar(255, 0, 0), 4);
                }
            }
            //-- Show what you got
//            HighGui.imshow("Capture - Face detection", frame );
            System.out.println("detected");
            return Mat2BufferedImage(frame);
        }

        public static BufferedImage ObjectDetection(BufferedImage image) throws IOException {

            //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);



            Mat frame = BufferedImage2Mat(image);

                //-- 3. Apply the classifier to the frame
            return detectAndDisplay(frame, faceCascade, eyesCascade);

        }
    }

