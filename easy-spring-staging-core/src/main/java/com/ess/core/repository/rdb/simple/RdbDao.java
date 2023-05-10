/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.repository.rdb.simple;

import com.ess.core.model.Model;
import com.ess.core.model.Page;
import com.ess.core.model.Query;
import com.ess.core.repository.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
 * 简单模式-关系型数据库-Dao基础接口  .
 * Dao基础接口
 *
 * @author caobaoyu
 * @date 2020/5/15 14:37
 */
public interface RdbDao<K, M extends Model<K>> extends BaseDao<K,M>
{
  @SuppressWarnings("resource")
  default Page<M> QueryNativePage(AuthorizationUser<?, ?, ?, ?> u, Query q) throws Exception{
    Page<M> page;
    PageHelper.startPage(q.getPageModel().getPageNo(), q.getPageModel().getPageSize());
    PageInfo<M> pageInfo = new PageInfo<>(query(u, q));
    page = new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),pageInfo.getList());
    return page;
  }

}
