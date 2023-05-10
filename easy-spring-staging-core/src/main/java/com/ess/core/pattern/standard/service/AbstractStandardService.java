/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.pattern.standard.service;


import com.ess.core.repository.BaseDao;
import com.ess.core.executor.add.AddExecutor;
import com.ess.core.executor.add.AddPerExecutor;
import com.ess.core.executor.add.AddPostExecutor;
import com.ess.core.executor.edit.*;
import com.ess.core.executor.query.*;
import com.ess.core.executor.remove.*;
import com.ess.core.model.Page;
import com.ess.core.model.Query;
import com.ess.core.model.convert.ServiceConverter;
import com.ess.core.model.dto.AbstractDTO;
import com.ess.core.model.po.AbstractPO;
import com.ess.core.pattern.AbstractBaseService;
import com.ess.core.sercurity.AuthorizationUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标准模式-Service基础实现 .
 *
 * <p>
 * Service基础实现
 *
 * @author caobaoyu
 * @date 2020/5/15 15:46
 */
public abstract class AbstractStandardService<DAO extends BaseDao<K,PO>, K, DTO extends AbstractDTO<K>, PO extends AbstractPO<K>> extends AbstractBaseService<K, DTO> {

    public abstract DAO getDao();

    public abstract ServiceConverter<K, DTO,PO> getConverter();

    @Transactional(readOnly = true)
    public DTO queryDetails(AuthorizationUser<?, ?, ?, ?> u, K k, QueryDetailsExecutor<K, DTO>... executors) throws Exception {
        DTO dto ;
        queryDetailsExecute(u, QueryDetailsPerExecutor.class, k, null, executors);
        dto = getConverter().poToDto(getDao().load(u, k));
        queryDetailsExecute(u, QueryDetailsPostExecutor.class, k, dto, executors);
        return dto;
    }


    @Transactional(readOnly = true)
    public Page<DTO> queryPage(AuthorizationUser<?, ?, ?, ?> u, Query q, QueryPageExecutor<K, DTO>... executors) throws Exception {
        Page<DTO> page;
        q.initPage();
        queryPageExecute(u, QueryPagePerExecutor.class, q, null, executors);
        if (q.isPage()) {
            Page<PO> poPage = getDao().QueryNativePage(u, q);
            page = getConverter().poToDto(poPage);
        } else {
            List<DTO> list = getConverter().poToDto(getDao().query(u, q));
            page = new Page<>(list);
        }
        queryPageExecute(u, QueryPagePostExecutor.class, q, page, executors);
        return page;
    }

    @Transactional(readOnly = true)
    public List<DTO> queryList(AuthorizationUser<?, ?, ?, ?> u, String fn, List<?> ks) throws Exception {
        List<DTO> dataList;
        dataList = getConverter().poToDto(getDao().list(u,fn, ks));
        return dataList;
    }

    @Transactional(readOnly = true)
    public Long count(AuthorizationUser<?, ?, ?, ?> u, Query q) throws Exception {
        return getDao().count(u, q);
    }



    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public K add(AuthorizationUser<?, ?, ?, ?> u, DTO dto, AddExecutor<K, DTO>... executors) throws Exception {
        K k = null;
        addExecute(u, AddPerExecutor.class, null, dto, executors);
        PO po = getConverter().dtoToPo(dto);
        if (getDao().insert(po) > 0) {
            k = po.getKey();
        }
        addExecute(u, AddPostExecutor.class, k, dto, executors);
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
    public Boolean edit(AuthorizationUser<?, ?, ?, ?> u, K k, DTO dto, EditExecutor<K, DTO>... executors) throws Exception {
        boolean result = false;
        editExecute(u, EditPerExecutor.class, k, dto, executors);
        int count = getDao().update(u, k, getConverter().dtoToPo(dto));
        if (count > 0) {
            result = true;
        }
        editExecute(u, EditPostExecutor.class, k, dto, executors);
        return result;
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean editAll(AuthorizationUser<?, ?, ?, ?> u, K k, DTO dto, EditAllExecutor<K, DTO>... executors) throws Exception {
        boolean result = false;
        editAllExecute(u, EditAllPerExecutor.class, k, dto, executors);
        int count = getDao().updateAll(u, k, getConverter().dtoToPo(dto));
        if (count > 0) {
            result = true;
        }
        editAllExecute(u, EditAllPostExecutor.class, k, dto, executors);
        return result;
    }

}

