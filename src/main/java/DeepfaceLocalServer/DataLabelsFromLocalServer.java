package DeepfaceLocalServer;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class DataLabelsFromLocalServer {

    public static @NotNull MatOfByte BufferedImage2Mat(BufferedImage image) throws IOException {

        MatOfByte matOfByte = new MatOfByte();
        ByteArrayOutputStream barray = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", barray);
        barray.flush();
        byte[] bytes = barray.toByteArray();
        Mat mat = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_COLOR);
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        //Mat mat = new Mat();
        //Imgproc.cvtColor(new MatOfByte(bytes), mat, Imgproc.COLOR_BayerBGGR2BGR);
        return matOfByte;
    }
//public static @NotNull Mat BufferedImage2Mat(BufferedImage image) throws IOException {
//
//    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//    ImageIO.write(image, "jpg", byteArrayOutputStream);
//    byteArrayOutputStream.flush();
//    System.out.println("the beginning of BufferedImage2Mat");
//    //IMWRITE_PNG_BILEVEL
//    return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_COLOR);
//}
    static String json;
    static MatOfByte image;
    public static String GetData(BufferedImage bufferedImage) throws IOException {
//        StringBuilder sb = new StringBuilder();

        image = BufferedImage2Mat(bufferedImage);
//        Raster raster = bufferedImage.getRaster();
        String image2 = Base64.getEncoder().encodeToString(image.toArray());

//        String pkg = json.;
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://localhost:5000/api/test");
        httppost.addHeader("content-type","image/jpeg");
// Request parameters and other properties.


        List<NameValuePair> params = new ArrayList<>(1);
        params.add(new BasicNameValuePair("data", image2));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

//Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        System.out.println("Request sent");
//        HttpEntity entity = response.getEntity();

        if (response != null) {
            try {
                json = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return json;
    }
}
