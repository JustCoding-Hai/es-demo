package org.example;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        logger.info("Hello, World!");
//        System.out.println("Hello world!");

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost();
        StringEntity stringEntity = new StringEntity("json", "UTF-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        HttpResponse execute = httpClient.execute(httpPost);
        System.out.println(execute);

    }
}