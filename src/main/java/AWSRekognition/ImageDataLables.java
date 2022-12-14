package AWSRekognition;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


//I suggest that you convert Mat into FloatBuffer as follows:
//
//        Mat floatMat = new Mat();
//        mat.convertTo(floatMat, CV_32F);
//        FloatBuffer floatBuffer = floatMat.createBuffer();
//        Note that the createBuffer method is found within the Mat class of the import org.bytedeco.opencv.opencv_core.Mat not the import org.opencv.core.
//
//        Then you can create a tensor from the floatBuffer variable:
//
//        Tensor.create(new long[]{1, image_height, image_width, 3}, floatBuffer)


public class ImageDataLables {
    static byte[] bytes;
    static ByteArrayOutputStream bytesStream;
    public static String GetData(BufferedImage image) throws IOException {
        System.out.println("begin to load image and reading bytes");
        bytesStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bytesStream);
        bytes = bytesStream.toByteArray();
        System.out.println("Conversion to bytes array is complete");
        StringBuilder sb = new StringBuilder();
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        AmazonRekognition rekognition = ClientFactory.createClient();
        System.out.println("rekognition is created");
        DetectFacesRequest request = new DetectFacesRequest()
                .withImage(new Image().withBytes(byteBuffer))
                        .withAttributes(Attribute.ALL);
        System.out.println("request with image is done");

        DetectFacesResult result = rekognition.detectFaces(request);
        System.out.println("got result from rekognition.detectFaces");
        List<FaceDetail> faceDetails = result.getFaceDetails();
        for (FaceDetail faceDetail:faceDetails) {
            List<Emotion> lst = faceDetail.getEmotions();
            for (Emotion emotion:lst) {
                sb.append(emotion.getType() + " " + emotion.getConfidence());
            }
            System.out.println(sb);
        }
        return sb.toString();

    }

}
