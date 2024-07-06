package org.example;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class ClientTest {


    public static void main(String[] args)  {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        System.out.println(client);
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
