package com.ess.demo.mvc.simple.modules.test.repository;


import com.ess.core.model.Query;
import com.ess.demo.mvc.simple.modules.test.model.EmployeeInfo;
import com.ess.extension.repository.es.simple.ElasticDao;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EmployeeInfoDao extends ElasticDao<String, EmployeeInfo> {

  private static final String INDEX_NAME = "employee_index_es";
  @Resource
  private RestHighLevelClient restHighLevelClient;

  @Override
  public RestHighLevelClient getClient() {
    return this.restHighLevelClient;
  }

  @Override
  public String getIndexName() {
    return INDEX_NAME;
  }

  @Override
  public boolean queryCondition(SearchSourceBuilder sourceBuilder, Query q) {
    return false;
  }

  @Override
  public boolean countCondition(SearchSourceBuilder sourceBuilder, Query q) {
    return false;
  }
}


