package org.example;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;


public class BatchTest {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        //创建批量新增请求对象
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "张三"));
        request.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", "李四"));
        request.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "王五"));
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println("took:" + bulkResponse.getTook());
        System.out.println("items:" + bulkResponse.getItems());

        searchDocument(client);
        BulkRequest deleteRequest = new BulkRequest();
        deleteRequest.add(new DeleteRequest().index("user").id("1001"));
        deleteRequest.add(new DeleteRequest().index("user").id("1002"));
        BulkResponse deleteResponse = client.bulk(deleteRequest, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println("took:" + deleteResponse.getTook());
        System.out.println("items:" + deleteResponse.getItems());

        searchDocument(client);
        client.close();



    }


    private static void searchDocument(RestHighLevelClient client) throws IOException {
        //1.创建请求对象
        GetRequest getRequest = new GetRequest().index("user").id("1001");
        //2.客户端发送请求，获取响应对象
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        //3.打印结果信息
        System.out.println("_index:" + getResponse.getIndex());
        System.out.println("_type:" + getResponse.getType());
        System.out.println("_id:" + getResponse.getId());
        System.out.println("source:" + getResponse.getSourceAsString());
        GetRequest getRequest2 = new GetRequest().index("user").id("1002");
        //2.客户端发送请求，获取响应对象
        GetResponse getResponse2 = client.get(getRequest2, RequestOptions.DEFAULT);
        //3.打印结果信息
        System.out.println("_index:" + getResponse2.getIndex());
        System.out.println("_type:" + getResponse2.getType());
        System.out.println("_id:" + getResponse2.getId());
        System.out.println("source:" + getResponse2.getSourceAsString());


    }
}
