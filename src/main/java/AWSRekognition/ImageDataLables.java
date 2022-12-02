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
