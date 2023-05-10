/**
 * Copyright(C) 2023 Company:深瞳大观科技有限公司
 */

package com.ess.extension.repository.es;

import com.ess.core.model.Model;
import com.ess.core.model.Page;
import com.ess.core.model.Query;
import com.ess.core.repository.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.core.utils.JsonUtil;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * ES Dao抽象类  .
 *
 * @author caobaoyu
 * @date 2020/5/15 14:37
 */
public abstract class AbstractElasticDao<K, M extends Model<K>> implements BaseDao<K, M> {

  public abstract RestHighLevelClient getClient();

  public abstract String getIndexName();

  public abstract boolean queryCondition(SearchSourceBuilder sourceBuilder, Query q);

  public abstract boolean countCondition(SearchSourceBuilder sourceBuilder, Query q);

  private Class<M> getModelClass() {
    return (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
  }

  private Class<K> getKeyClass() {
    return (Class<K>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }


  private void setKey(M m, String id) {
    if (m != null && id != null) {
      Class<?> keyClass = getKeyClass();
      if (keyClass == String.class) {
        m.setKey((K) id);
      } else if (keyClass == Long.class) {
        m.setKey((K) Long.valueOf(id));
      } else if (keyClass == Integer.class) {
        m.setKey((K) Integer.valueOf(id));
      } else if (keyClass == Double.class) {
        m.setKey((K) Double.valueOf(id));
      } else if (keyClass == Float.class) {
        m.setKey((K) Float.valueOf(id));
      }
    }
  }

  private SearchRequest createSearchRequest(Query q, ElasticCondition elasticCondition) {
    SearchRequest searchRequest = new SearchRequest(getIndexName());
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    if (q.isPage()) {
      sourceBuilder.from((q.getPageModel().getPageNo() - 1) * q.getPageModel().getPageSize());
      sourceBuilder.size(q.getPageModel().getPageSize());
    }
    if (!elasticCondition.builder(sourceBuilder, q)) {
      sourceBuilder.query(QueryBuilders.matchAllQuery());
    }
    searchRequest.source(sourceBuilder);
    return searchRequest;
  }

  private SearchResult<M> searchExecute(RestHighLevelClient restHighLevelClient, SearchRequest searchRequest) throws Exception {
    SearchResult<M> searchResult = new SearchResult();
    SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    if (searchResponse.status() == RestStatus.OK && !searchResponse.isTimedOut()) {
      SearchHits searchHits = searchResponse.getHits();
      if (searchHits != null) {
        List<M> items = new ArrayList<>();
        long total = searchHits.getTotalHits().value;
        SearchHit[] searchHitArray = searchHits.getHits();
        for (SearchHit e : searchHitArray) {
          M m = JsonUtil.parseObject(e.getSourceAsString(), getModelClass());
          items.add(m);
        }
        searchResult.setData(items);
        searchResult.setTotal(total);
      }
    }
    return searchResult;
  }

  @Override
  public Page<M> QueryNativePage(AuthorizationUser<?, ?, ?, ?> u, Query q) throws Exception {
    Page<M> page;
    SearchRequest searchRequest = createSearchRequest(q, new ElasticCondition() {
      @Override
      public boolean builder(SearchSourceBuilder sourceBuilder, Query q) {
        return queryCondition(sourceBuilder, q);
      }
    });
    SearchResult<M> searchResult = searchExecute(getClient(), searchRequest);
    page = new Page<>(q.getPageModel().getPageNo(), q.getPageModel().getPageSize(), searchResult.getTotal(), searchResult.data);
    return page;
  }

  @Override
  public M load(AuthorizationUser<?, ?, ?, ?> u, K k) throws Exception {
    M m = null;
    if (k != null) {
      GetRequest request = new GetRequest(getIndexName(), k.toString());
      GetResponse getResponse = getClient().get(request, RequestOptions.DEFAULT);
      if (getResponse != null) {
        byte[] bytes = getResponse.getSourceAsBytes();
        if (bytes != null) {
          m = JsonUtil.parseObject(bytes, getModelClass());
        }
      }
    }
    return m;
  }

  @Override
  public List<M> query(AuthorizationUser<?, ?, ?, ?> u, Query q) throws Exception {
    List<M> list;
    SearchRequest searchRequest = createSearchRequest(q, new ElasticCondition() {
      @Override
      public boolean builder(SearchSourceBuilder sourceBuilder, Query q) {
        return queryCondition(sourceBuilder, q);
      }
    });
    SearchResult<M> searchResult = searchExecute(getClient(), searchRequest);
    list = searchResult.getData();
    return list;
  }

  @Override
  public List<M> list(AuthorizationUser<?, ?, ?, ?> u, String fn, List<?> ks) throws Exception {
    List<M> list;
    SearchRequest searchRequest = new SearchRequest(getIndexName());
    SearchSourceBuilder builder = new SearchSourceBuilder();
    builder.query(QueryBuilders.termsQuery(fn, ks));
    searchRequest.source(builder);
    SearchResult<M> searchResult = searchExecute(getClient(), searchRequest);
    list = searchResult.getData();
    return list;
  }

  @Override
  public Long count(AuthorizationUser<?, ?, ?, ?> u, Query q) throws Exception {
    long total = 0L;
    SearchRequest searchRequest = createSearchRequest(q, new ElasticCondition() {
      @Override
      public boolean builder(SearchSourceBuilder sourceBuilder, Query q) {
        return countCondition(sourceBuilder, q);
      }
    });
    SearchResponse searchResponse = getClient().search(searchRequest, RequestOptions.DEFAULT);
    if (searchResponse.status() == RestStatus.OK && !searchResponse.isTimedOut()) {
      total = searchResponse.getHits().getTotalHits().value;
    }

    return total;
  }

  @Override
  public Integer insert(M m) throws Exception {
    int count = 0;
    if (m != null) {
      IndexRequest indexRequest = new IndexRequest(getIndexName())
              .source(JsonUtil.toJSONString(m), XContentType.JSON);
      if (m.getKey() != null) {
        indexRequest.id(m.getKey().toString());
      }
      IndexResponse indexResponse = getClient().index(indexRequest, RequestOptions.DEFAULT);
      if (indexResponse.getResult() == DocWriteResponse.Result.CREATED || indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
        String id = indexResponse.getId();
        boolean updateKey = m.getKey() == null;
        setKey(m, id);
        if (updateKey) {
          UpdateRequest updateRequest = new UpdateRequest(getIndexName(), id)
                  .doc(JsonUtil.toJSONString(m), XContentType.JSON);
          UpdateResponse updateResponse = getClient().update(updateRequest, RequestOptions.DEFAULT);
          if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            count = 1;
          }
        } else {
          count = 1;
        }
      }
    }
    return count;
  }

  @Override
  public Integer delete(AuthorizationUser<?, ?, ?, ?> u, K k) throws Exception {
    int count = 0;
    DeleteRequest request = new DeleteRequest(getIndexName(), k.toString());
    DeleteResponse response = getClient().delete(request, RequestOptions.DEFAULT);
    boolean isDeleted = response.getResult() == DocWriteResponse.Result.DELETED;
    if (isDeleted) {
      count = 1;
    }
    return count;
  }

  @Override
  public Integer deleteMulti(AuthorizationUser<?, ?, ?, ?> u, List<K> ks) throws Exception {
    Integer count = 0;
    BulkRequest request = new BulkRequest();
    for (K k : ks) {
      request.add(new DeleteRequest(getIndexName(), k.toString()));
    }
    BulkResponse bulkResponse = getClient().bulk(request, RequestOptions.DEFAULT);
    for (BulkItemResponse bulkItemResponse : bulkResponse) {
      if (!bulkItemResponse.isFailed()) {
        count++;
      }
    }
    return count;
  }

  @Override
  public Integer update(AuthorizationUser<?, ?, ?, ?> u, K k, M m) throws Exception {
    int count = 0;
    m.setKey(k);
    UpdateRequest request = new UpdateRequest(getIndexName(), k.toString())
            .doc(JsonUtil.toJSONString(m), XContentType.JSON);
    UpdateResponse updateResponse = getClient().update(request, RequestOptions.DEFAULT);
    if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
      count = 1;
    }
    return count;
  }

  @Override
  public Integer updateAll(AuthorizationUser<?, ?, ?, ?> u, K k, M m) throws Exception {
    int count = 0;
    m.setKey(k);
    UpdateRequest request = new UpdateRequest(getIndexName(), k.toString())
            .doc(JsonUtil.toJSONString(m, true), XContentType.JSON);
    UpdateResponse updateResponse = getClient().update(request, RequestOptions.DEFAULT);
    if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
      count = 1;
    }
    return count;
  }

  private static class SearchResult<M> {
    private Long total;
    private List<M> data;

    public Long getTotal() {
      return total;
    }

    public void setTotal(Long total) {
      this.total = total;
    }

    public List<M> getData() {
      return data;
    }

    public void setData(List<M> data) {
      this.data = data;
    }
  }
}
