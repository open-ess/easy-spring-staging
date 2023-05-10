/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.repository.rdb.standard;


import com.ess.core.model.Page;
import com.ess.core.model.Query;
import com.ess.core.model.po.AbstractPO;
import com.ess.core.repository.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 标准模式-关系型数据库-Dao基础接口  .
 * Dao基础接口
 *
 * @author caobaoyu
 * @date 2020/5/15 14:37
 */
public interface RdbDao<K, P extends AbstractPO<K>> extends BaseDao<K, P> {
  default Page<P> QueryNativePage(AuthorizationUser<?, ?, ?, ?> u, Query q) throws Exception{
    Page<P> page;
    PageHelper.startPage(q.getPageModel().getPageNo(), q.getPageModel().getPageSize());
    PageInfo<P> pageInfo = new PageInfo<>(query(u, q));
    page = new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),pageInfo.getList());
    return page;
  }
}
