/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.pattern.mvc.standard.service;


import com.ess.core.executor.add.AddExecutor;
import com.ess.core.executor.add.AddPerExecutor;
import com.ess.core.executor.add.AddPostExecutor;
import com.ess.core.executor.edit.*;
import com.ess.core.executor.query.*;
import com.ess.core.executor.remove.*;
import com.ess.core.model.Page;
import com.ess.core.model.QueryParameter;
import com.ess.core.model.convert.ServiceConverter;
import com.ess.core.model.dto.AbstractDTO;
import com.ess.core.model.po.AbstractPO;
import com.ess.core.pattern.mvc.SuperAbstractService;
import com.ess.core.pattern.mvc.standard.dao.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service基础实现 .
 *
 * <p>
 * Service基础实现
 *
 * @author caobaoyu
 * @date 2020/5/15 15:46
 */
public abstract class AbstractService<K, M extends AbstractDTO<K>, P extends AbstractPO<K>> extends SuperAbstractService<K, M> {

    public abstract BaseDao<K, P> getDao();

    public abstract ServiceConverter<K, M,P> getConverter();

    @Transactional(readOnly = true)
    public M queryDetails(AuthorizationUser<?, ?, ?, ?> u, K k, QueryDetailsExecutor<K, M>... executors) throws Exception {
        M m ;
        queryDetailsExecute(u, QueryDetailsPerExecutor.class, k, null, executors);
        m = getConverter().poToDto(getDao().load(u, k));
        queryDetailsExecute(u, QueryDetailsPostExecutor.class, k, m, executors);
        return m;
    }


    @Transactional(readOnly = true)
    public Page<M> queryPage(AuthorizationUser<?, ?, ?, ?> u, QueryParameter q, QueryPageExecutor<K, M>... executors) throws Exception {
        Page<M> page;
        q.initPage();
        queryPageExecute(u, QueryPagePerExecutor.class, q, null, executors);
        if (q.isPage()) {
            com.github.pagehelper.Page<M> pageHelperPage = PageHelper.startPage(q.getPageModel().getPageNo(), q.getPageModel().getPageSize()).doSelectPage(() -> getDao().query(u, q));
            page = new Page<>(pageHelperPage);
        } else {
            List<M> list = getConverter().poToDto(getDao().query(u, q));
            page = new Page<>(list);
        }
        queryPageExecute(u, QueryPagePostExecutor.class, q, page, executors);
        return page;
    }

    @Transactional(readOnly = true)
    public List<M> queryList(AuthorizationUser<?, ?, ?, ?> u, String fn, List<?> ks) throws Exception {
        List<M> dataList;
        dataList = getConverter().poToDto(getDao().list(u,fn, ks));
        return dataList;
    }

    @Transactional(readOnly = true)
    public Integer count(AuthorizationUser<?, ?, ?, ?> u, QueryParameter q) throws Exception {
        return getDao().count(u, q);
    }



    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public K add(AuthorizationUser<?, ?, ?, ?> u, M m, AddExecutor<K, M>... executors) throws Exception {
        K k = null;
        addExecute(u, AddPerExecutor.class, null, m, executors);
        P p = getConverter().dtoToPo(m);
        if (getDao().insert(p) > 0) {
            k = p.getKey();
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
        int count;
        removeMultiExecute(u, RemoveMultiPerExecutor.class, ks, executors);
        count = getDao().deleteMulti(u, ks);
        removeMultiExecute(u, RemoveMultiPostExecutor.class, ks, executors);
        return count;
    }



    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean edit(AuthorizationUser<?, ?, ?, ?> u, K k, M m, EditExecutor<K, M>... executors) throws Exception {
        boolean result = false;
        editExecute(u, EditPerExecutor.class, k, m, executors);
        int count = getDao().update(u, k, getConverter().dtoToPo(m));
        if (count > 0) {
            result = true;
        }
        editExecute(u, EditPostExecutor.class, k, m, executors);
        return result;
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean editAll(AuthorizationUser<?, ?, ?, ?> u, K k, M m, EditAllExecutor<K, M>... executors) throws Exception {
        boolean result;
        editAllExecute(u, EditAllPerExecutor.class, k, m, executors);
        result = getDao().updateAll(u, k, getConverter().dtoToPo(m));
        editAllExecute(u, EditAllPostExecutor.class, k, m, executors);
        return result;
    }

}

