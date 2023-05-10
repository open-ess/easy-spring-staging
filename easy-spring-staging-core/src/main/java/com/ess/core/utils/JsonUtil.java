/**
 * Copyright(C) 2021 Company:北京神州泰岳软件股份有限公司
 */
package com.ess.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具类.
 *
 * @author caobaoyu
 * @date 2021-12-31 11:45
 **/
public class JsonUtil {

  public static <T> T parseObject(String text, Class<T> clazz) throws Exception {
    T t = null;
    if (text != null) {
      ObjectMapper mapper = new ObjectMapper();
      t = mapper.readValue(text, clazz);
    }
    return t;
  }

  public static <T> T parseObject(byte[] bytes, Class<T> clazz) throws Exception {
    T t = null;
    if (bytes != null) {
      ObjectMapper mapper = new ObjectMapper();
      t = mapper.readValue(bytes, clazz);
    }
    return t;
  }

  public static String toJSONString(Object t) throws Exception {
    return toJSONString(t, false);

  }

  public static String toJSONString(Object t, boolean nonNull) throws Exception {
    String json = null;
    if (t != null) {
      ObjectMapper mapper = new ObjectMapper();
      if (!nonNull) {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      }
      json = mapper.writeValueAsString(t);
    }
    return json;

  }
}
