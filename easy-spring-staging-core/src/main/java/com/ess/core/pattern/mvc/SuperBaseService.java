/**
 * Copyright(C) 2023 Company:深瞳大观科技有限公司
 */

package com.ess.core.pattern.mvc;

import com.ess.core.executor.add.AddExecutor;
import com.ess.core.executor.edit.EditAllExecutor;
import com.ess.core.executor.edit.EditExecutor;
import com.ess.core.executor.query.QueryDetailsExecutor;
import com.ess.core.executor.query.QueryPageExecutor;
import com.ess.core.executor.remove.RemoveExecutor;
import com.ess.core.executor.remove.RemoveMultiExecutor;
import com.ess.core.model.Model;
import com.ess.core.model.Page;
import com.ess.core.model.QueryParameter;
import com.ess.core.sercurity.AuthorizationUser;

import java.util.List;

/**
 * <p>顶级服务接口</p> .
 *
 * @author caobaoyu
 * @date 2023/4/19 14:03
 */
public interface SuperBaseService <K, M extends Model<K>> {

  /**
   * 详情查询
   *
   * @param u         用户
   * @param k         模型主键
   * @param executors 附加执行器列表
   * @return 模型
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/14 17:16
   */
  //@SuppressWarnings("unchecked")
  M queryDetails(AuthorizationUser<?, ?, ?, ?> u, K k, QueryDetailsExecutor<K, M>... executors) throws Exception;


  /**
   * 列表查询
   *
   * @param u         用户
   * @param q         查询参数模型
   * @param executors 附加执行器列表
   * @return 分页模型
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/14 17:40
   */
  //@SuppressWarnings("unchecked")
  Page<M> queryPage(AuthorizationUser<?, ?, ?, ?> u, QueryParameter q, QueryPageExecutor<K, M>... executors) throws Exception;

  /**
   * 指定字段在集合中的数据集
   *
   * @param u  用户
   * @param fn 字段名
   * @param ks 限制条件集合
   * @return 模型列表
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/14 17:47
   */
  List<M> queryList(AuthorizationUser<?, ?, ?, ?> u, String fn, List<?> ks) throws Exception;


  /**
   * 查询数据集记录数
   *
   * @param u 用户
   * @param q 查询参数模型
   * @return 记录数
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/14 17:57
   */
  Integer count(AuthorizationUser<?, ?, ?, ?> u, QueryParameter q) throws Exception;

  /**
   * 添加
   *
   * @param u         用户
   * @param m         模型
   * @param executors 附加执行器列表
   * @return 主键
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/14 18:04
   */
  //@SuppressWarnings("unchecked")
  K add(AuthorizationUser<?, ?, ?, ?> u, M m, AddExecutor<K, M>... executors) throws Exception;


  /**
   * 通过主键删除
   *
   * @param u         用户
   * @param k         模型主键
   * @param executors 附加执行器列表
   * @return 删除是否成功
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/17 11:00
   */
  //@SuppressWarnings("unchecked")
  Boolean remove(AuthorizationUser<?, ?, ?, ?> u, K k, RemoveExecutor<K>... executors) throws Exception;

  /**
   * 通过多个主键批量删除
   *
   * @param u         用户
   * @param ks        模型主键list
   * @param executors 附加执行器列表
   * @return 删除数量
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/17 11:03
   */
  //@SuppressWarnings("unchecked")
  Integer removeMulti(AuthorizationUser<?, ?, ?, ?> u, List<K> ks, RemoveMultiExecutor<K>... executors) throws Exception;

  /**
   * 修改（注：修改不为空的属性）
   *
   * @param u         用户
   * @param k         模型主键
   * @param m         模型
   * @param executors 执行器
   * @return 修改是否成功
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/17 11:05
   */
  //@SuppressWarnings("unchecked")
  Boolean edit(AuthorizationUser<?, ?, ?, ?> u, K k, M m, EditExecutor<K, M>... executors) throws Exception;

  /**
   * 修改（注：全量字段修改,空的属性修改为空）
   *
   * @param u         用户
   * @param k         模型主键
   * @param m         模型
   * @param executors 执行器
   * @return 修改是否成功
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/17 11:05
   */
  //@SuppressWarnings("unchecked")
  Boolean editAll(AuthorizationUser<?, ?, ?, ?> u, K k, M m, EditAllExecutor<K, M>... executors) throws Exception;

}