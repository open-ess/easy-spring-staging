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

  /**
   * json字符串转换为对象
   *
   * @param text json字符串
   * @param clazz 转换对象的类型
   * @return 对象
   * @param <T> 转换类型
   * @throws Exception 异常
   *
   * @author caobaoyu
   * @date 2023/5/17 11:35
   */
  public static <T> T parseObject(String text, Class<T> clazz) throws Exception {
    T t = null;
    if (text != null) {
      ObjectMapper mapper = new ObjectMapper();
      t = mapper.readValue(text, clazz);
    }
    return t;
  }

  /**
   * json字符串数据流转换为对象
   *
   * @param bytes 数据流
   * @param clazz 转换对象的类型
   * @return 对象
   * @param <T> 转换类型
   * @throws Exception 异常
   *
   * @author caobaoyu
   * @date 2023/5/17 11:33
   */
  public static <T> T parseObject(byte[] bytes, Class<T> clazz) throws Exception {
    T t = null;
    if (bytes != null) {
      ObjectMapper mapper = new ObjectMapper();
      t = mapper.readValue(bytes, clazz);
    }
    return t;
  }

  /**
   * 把对象转换成json字符串，包含空属性
   *
   * @param t 对象
   * @return json字符串
   * @throws Exception 异常
   *
   * @author caobaoyu
   * @date 2023/5/17 11:32
   */
  public static String toJSONString(Object t) throws Exception {
    return toJSONString(t, false);

  }

  /**
   *  把对象转换成json字符串
   *
   * @param t 对象
   * @param nonNull json字符是否不包含空属性,true:不包含;false:包含
   * @return json字符串
   * @throws Exception 异常
   *
   * @author caobaoyu
   * @date 2023/5/17 11:23
   */
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
