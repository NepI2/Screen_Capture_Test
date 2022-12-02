package AWSRekognition;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;


public class ClientFactory {
    public static String s3Bucket = "test-dimitriy-belshin";
    public static String api = "dima-test-accespoint-3tng337nkj6o46ya866iryzydpceause1b-s3alias";

    public static AmazonRekognition createClient() {
        System.out.println("Creating client began");
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setConnectionTimeout(30000);
        clientConfig.setRequestTimeout(60000);
        clientConfig.setProtocol(Protocol.HTTPS);
        System.out.println("setting HTTPS is finished");
//        AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();

        return AmazonRekognitionClientBuilder
                .standard()
                .withClientConfiguration(clientConfig)
                .withRegion(Regions.US_EAST_1)
                .build();
    }

}
