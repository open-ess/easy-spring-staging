/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.pattern.simple.service;

import com.ess.core.repository.BaseDao;
import com.ess.core.executor.add.AddExecutor;
import com.ess.core.executor.add.AddPerExecutor;
import com.ess.core.executor.add.AddPostExecutor;
import com.ess.core.executor.edit.*;
import com.ess.core.executor.query.*;
import com.ess.core.executor.remove.*;
import com.ess.core.model.Model;
import com.ess.core.model.Page;
import com.ess.core.model.Query;
import com.ess.core.pattern.AbstractBaseService;
import com.ess.core.sercurity.AuthorizationUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 简单模式-Service基础实现 .
 *
 * <p>
 * Service基础实现
 *
 * @author caobaoyu
 * @date 2020/5/15 15:46
 */
public abstract class AbstractSimpleService<DAO extends BaseDao<K,M>,K, M extends Model<K>> extends AbstractBaseService<K, M> {

  public abstract DAO getDao();

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public M queryDetails(AuthorizationUser<?, ?, ?, ?> u, K k, QueryDetailsExecutor<K, M>... executors) throws Exception {
    M m;
    queryDetailsExecute(u, QueryDetailsPerExecutor.class, k, null, executors);
    m = getDao().load(u, k);
    queryDetailsExecute(u, QueryDetailsPostExecutor.class, k, m, executors);
    return m;
  }



  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public Page<M> queryPage(AuthorizationUser<?, ?, ?, ?> u, Query q, QueryPageExecutor<K, M>... executors) throws Exception {
    Page<M> page;
    q.initPage();
    queryPageExecute(u, QueryPagePerExecutor.class, q, null, executors);
    if (q.isPage()) {
      page = getDao().QueryNativePage(u,q);
    } else {
      List<M> list = getDao().query(u, q);
      page = new Page<>(list);
    }
    queryPageExecute( u, QueryPagePostExecutor.class, q, page, executors);
    return page;
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public List<M> queryList(AuthorizationUser<?, ?, ?, ?> u,String fn, List<?> ks) throws Exception{
    List<M> dataList;
    dataList = getDao().list(u,fn, ks);
    return dataList;
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public Long count(AuthorizationUser<?, ?, ?, ?> u, Query q) throws Exception {
    return getDao().count(u,q);
  }


  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public K add(AuthorizationUser<?, ?, ?, ?> u, M m, AddExecutor<K, M>... executors) throws Exception {
    K k = null;
    addExecute(u, AddPerExecutor.class, null, m, executors);
    if (getDao().insert(m) > 0) {
      k = m.getKey();
    }
    addExecute(u, AddPostExecutor.class, k, m, executors);
    return k;
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public Boolean remove(AuthorizationUser<?, ?, ?, ?> u, K k, RemoveExecutor<K>... executors) throws Exception {
    boolean result = false;
    removeExecute(u, RemovePerExecutor.class, k, executors);
    int count = getDao().delete(u, k);
    if (count > 0) {
      result = true;
    }
    removeExecute(u, RemovePostExecutor.class, k, executors);
    return result;
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public Integer removeMulti(AuthorizationUser<?, ?, ?, ?> u, List<K> ks, RemoveMultiExecutor<K>... executors) throws Exception {
    Integer count;
    removeMultiExecute(u, RemoveMultiPerExecutor.class, ks,executors);
    count = getDao().deleteMulti(u, ks);
    removeMultiExecute(u, RemoveMultiPostExecutor.class, ks,executors);
    return count;
  }


  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public Boolean edit(AuthorizationUser<?, ?, ?, ?> u, K k, M m, EditExecutor<K, M>... executors) throws Exception {
    boolean result = false;
    editExecute(u, EditPerExecutor.class, k, m, executors);
    int count = getDao().update(u, k, m);
    if (count > 0) {
      result = true;
    }
    editExecute(u, EditPostExecutor.class, k, m, executors);
    return result;
  }

  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public Boolean editAll(AuthorizationUser<?, ?, ?, ?> u, K k, M m, EditAllExecutor<K, M>... executors) throws Exception {
    boolean result = false;
    editAllExecute(u, EditAllPerExecutor.class, k, m, executors);
    int count = getDao().updateAll(u, k, m);
    if (count > 0) {
      result = true;
    }
    editAllExecute(u, EditAllPostExecutor.class, k, m, executors);
    return result;
  }
}
