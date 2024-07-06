package org.example;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

public class IndexTest {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
//        System.out.println(client);
//        // 创建索引 - 请求对象
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest("user");
//        // 发送请求，获取响应
//        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
//        // 获取响应状态
//        boolean acknowledged = createIndexResponse.isAcknowledged();
//        System.out.println("操作状态 = " + acknowledged);

        // 查询索引 - 请求对象
//        GetIndexRequest request = new GetIndexRequest("user");
//        // 发送请求，获取响应
//        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
//        System.out.println("aliases:" + response.getAliases());
//        System.out.println("mappings:" + response.getMappings());
//        System.out.println("settings:" + response.getSettings());
//        client.close();


//        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("user");
//        // 发送请求，获取响应
//        AcknowledgedResponse deleteResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
//        System.out.println("操作状态 = " + deleteResponse.isAcknowledged());
//        client.close();

        // 查询索引 - 请求对象
        GetIndexRequest request = new GetIndexRequest("user");
        // 发送请求，获取响应
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        System.out.println("aliases:" + response.getAliases());
        System.out.println("mappings:" + response.getMappings());
        System.out.println("settings:" + response.getSettings());
        client.close();
    }
}
