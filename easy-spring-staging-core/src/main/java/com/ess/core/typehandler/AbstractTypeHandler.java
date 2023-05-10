/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.typehandler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * json类型抽象TypeHandler  .
 *
 * <p>
 * json类型抽象TypeHandler
 *
 * @author caobaoyu
 * @date 2020/10/28 10:36
 */
public abstract class AbstractTypeHandler<T> extends BaseTypeHandler<T> {
  @Override
  public void setNonNullParameter(PreparedStatement preparedStatement, int i, T t, JdbcType jdbcType) throws SQLException {
    if (t == null) {
      preparedStatement.setString(i, null);
    } else {

      try {
        String jsonStr = toString(t);
        preparedStatement.setString(i, jsonStr);
      } catch (Exception e) {
        throw new SQLException(e);
      }

    }
  }

  @Override
  public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
    T t;
    String tsStr = resultSet.getString(s);
    t = createTypeObject(tsStr);
    return t;
  }

  @Override
  public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
    T t;
    String tsStr = resultSet.getString(i);
    t = createTypeObject(tsStr);
    return t;
  }

  @Override
  public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    T t;
    String tsStr = callableStatement.getString(i);
    t = createTypeObject(tsStr);
    return t;
  }

  private T createTypeObject(String tsStr) throws SQLException {
    T t = null;
    if (tsStr != null) {
      try {
        t = toObject(tsStr);
      } catch (Exception e) {
        throw new SQLException(e);
      }
    }
    return t;
  }

  /**
   * 把json字符串转换成对象数组
   *
   * <p>
   * 把json字符串转换成对象数组
   *
   * @param tsStr json字符串
   * @return java.util.List<T> 对象List
   * @author caobaoyu
   * @date 2020/10/28 10:36
   */
  public abstract T toObject(String tsStr) throws Exception;

  public abstract String toString(T t) throws Exception;
}
