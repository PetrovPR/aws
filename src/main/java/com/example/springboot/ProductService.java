package com.example.springboot;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

@Service
public class ProductService {

    private HashMap<Integer, Product> personMap = new HashMap<Integer, Product>();

    public void addProduct(Product product) {

        Segment add_product = AWSXRay.beginSegment("Add Product");
        try {
            personMap.put(product.getId(), product);
        } catch (Exception e ) {
            add_product.addException(new RuntimeException());
        } finally {
            AWSXRay.endSegment();
        }

    }

    public Product getProduct(Integer id) {
        return personMap.get(id);
    }

    public void checkout(String message) throws IOException {

        Properties prop=new Properties();
        FileInputStream ip= new FileInputStream("config.properties");

        prop.load(ip);

        AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                .withRegion("eu-central-1")
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(prop.getProperty("user"),
                                prop.getProperty("password"))))
                .build();

        String queueUrl = sqs.getQueueUrl("Checkoutqueue").getQueueUrl();
        System.out.println(queueUrl);

        SendMessageRequest my_message = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message)
                .withDelaySeconds(5);

        sqs.sendMessage(my_message);

    }
}
