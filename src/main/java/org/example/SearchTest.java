package org.example;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

public class SearchTest {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
//        searchAll(client);
//        termQuery(client);
//        pageQuery(client);
//        sort(client);
//        fetchSource(client);
//        boolQuery(client);
//        rangeQuery(client);
//        fuzzyQuery(client);
//        highLightQuery(client);
//        maxQuery(client);
        groupByQuery(client);
        client.close();
    }


    private static void groupByQuery(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(AggregationBuilders.terms("age_groupby").field("age"));
        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getAggregations().get("age_groupby").getType());
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
    }


    private static void maxQuery(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(AggregationBuilders.max("maxAge").field("age"));
        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
    }

    private static void highLightQuery(RestHighLevelClient client) throws IOException  {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("name", "zhangsan");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(termsQueryBuilder);
        //设置高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");//设置标签前缀
        highlightBuilder.postTags("</font>");//设置标签后缀
        highlightBuilder.field("name");//设置高亮字段
        //设置高亮构建对象
        sourceBuilder.highlighter(highlightBuilder);
        //设置请求体
        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
            //打印高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields);
        }

    }

    private static void fuzzyQuery(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        FuzzyQueryBuilder builder = QueryBuilders.fuzzyQuery("name", "zhangsan").fuzziness(Fuzziness.ONE);
        sourceBuilder.query(builder);

        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }

    }

    private static void rangeQuery(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        //大于等于
        rangeQuery.gte(30);
        //小于等于
        rangeQuery.lte(40);
        sourceBuilder.query(rangeQuery);

        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }

    }

    private static void boolQuery(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有数据
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("name", "zhangsan"));
        boolQueryBuilder.should(QueryBuilders.matchQuery("set", "男"));
        sourceBuilder.query(boolQueryBuilder);

        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
    }



    private static void fetchSource(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //查询字段过滤
        String[] excludes = {};
        String[] includes = {"name", "age"};
        sourceBuilder.fetchSource(includes, excludes);

        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }

    }


    private static void searchAll(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }

    }

    private static void termQuery(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有数据
        sourceBuilder.query(QueryBuilders.termQuery("age", 30));
        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }

    }

    private static void pageQuery(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 当前页其实索引(第一条数据的顺序号)
        sourceBuilder.from(2);
        //每页显示多少条
        sourceBuilder.size(2);
        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }

    }

    private static void sort(RestHighLevelClient client) throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.sort("age", SortOrder.ASC);
        request.source(sourceBuilder);
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = searchResponse.getHits();
        System.out.println("took:" + searchResponse.getTook());
        System.out.println("timeout:" + searchResponse.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
    }

}
