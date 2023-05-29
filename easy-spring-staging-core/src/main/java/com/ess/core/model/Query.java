/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数模型  .  .
 *
 * <p>
 * 请求参数模型  .
 *
 * @author caobaoyu
 * @date 2020/5/15 14:51
 */
public class Query extends HashMap<String, Object> {

    /**
     * 请求参数构建器
     */
    public static class QueryParameterBuilder{
        private final HashMap<String, Object> data;
        public QueryParameterBuilder(){
            data = new HashMap<>();
        }
        public QueryParameterBuilder add(String key, Object value){
            this.data.put(key, value);
            return this;
        }

        public QueryParameterBuilder setPageNum(Integer pageNum){
            this.data.put(Page.PAGE_NUM_PARAM_NAME, pageNum);
            return this;
        }

        public QueryParameterBuilder setPageSize(Integer pageSize){
            this.data.put(Page.PAGE_SIZE_PARAM_NAME, pageSize);
            return this;
        }
        public Query build(){
            Query query = new Query(data);
            query.initPage();
            return query;
        }
    }

    // 排序参数名称
    public static final String SORT_PARAM_KEY_NAME = "sort";
    // 排序参数分割符号
    public static final String SORT_PARAM_VALUE_DELIMITER = ",";
    public static QueryParameterBuilder builder() {
        return new QueryParameterBuilder();
    }
    // 分页模型
    private Page<?> pageModel;

    public Query(HashMap<String, Object> data){
        super(data);
    }
    public Query(){
        super();
    }

    // 获取分页模型
    public Page<?> getPageModel() {
        return pageModel;
    }

    // 设置分页模型
    public void setPageModel(Page<?> pageModel) {
        this.pageModel = pageModel;
    }

    /**
     * 分页信息
     *
     * @author caobaoyu
     * @date 2020/5/15 15:01
     */
    @JsonIgnore
    public void initPage() {
        Object pageNumObject = this.get(Page.PAGE_NUM_PARAM_NAME);
        Object pageSizeObject = this.get(Page.PAGE_SIZE_PARAM_NAME);
        Integer pageNum = 1;
        Integer pageSize = 0;
        if (pageNumObject != null) {
            pageNum = (Integer) pageNumObject;
        }
        if (pageSizeObject != null) {
            pageSize = (Integer) pageSizeObject;
        }
        pageModel = new Page<>(pageNum, pageSize);

    }

    @JsonIgnore
    public Query add(String key, Object value){
        if (key != null) {
            put(key, value);
        }
        return this;
    }

    /**
     * 构建排序字符串
     *
     * @param columnMap 排序字段映射Map
     * @author caobaoyu
     * @date 2020/5/15 15:10
     */
    @JsonIgnore
    public void sort(Map<String, String> columnMap) {
        if(isSort()){
            List<Sort> sorts = new ArrayList<>();
            final Object sortParam = this.get(Query.SORT_PARAM_KEY_NAME);
            if (sortParam != null && columnMap != null && columnMap.size() > 0) {
                String sortExpression = (String) sortParam;
                String[] sortArray = sortExpression.split(Query.SORT_PARAM_VALUE_DELIMITER);
                for (String s : sortArray) {
                    if (s != null && !"".equals(s) && s.length() > 1) {
                        String sortTypeStr = s.substring(0, 1);
                        Integer sortType = null;
                        String fieldName = s.substring(1);
                        String columnName = columnMap.get(fieldName);
                        if (sortTypeStr.equals("+")) {
                            sortType = Sort.SORT_TYPE_ASC;
                        } else if (sortTypeStr.equals("-")) {
                            sortType = Sort.SORT_TYPE_DESC;
                        }
                        if (sortType != null && columnName != null) {
                            Sort sort = Sort.builder()
                                    .fieldName(fieldName)
                                    .columnName(columnName)
                                    .sortType(sortType)
                                    .build();
                            sorts.add(sort);
                        }
                    }
                }
            }
            this.put(Query.SORT_PARAM_KEY_NAME, sorts);
        }
    }

    public boolean isSort(){
        return containsKey(Query.SORT_PARAM_KEY_NAME);
    }

    /**
     * 判断是否需要分页
     *
     * @return ture:需要分页;false:不需要分页
     * @author caobaoyu
     * @date 2020/5/15 15:10
     */
    @JsonIgnore
    public Boolean isPage() {
        Boolean pageFlag = Boolean.FALSE;
        if (this.containsKey(Page.PAGE_SIZE_PARAM_NAME)) {
            Object pageSizeObj = this.get(Page.PAGE_SIZE_PARAM_NAME);
            if (pageSizeObj instanceof Integer) {
                pageFlag = Boolean.TRUE;
            }
        }
        return pageFlag;
    }
}
