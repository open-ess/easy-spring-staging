/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.pattern.mvc.standard.service;

import com.ess.core.model.dto.AbstractDTO;
import com.ess.core.model.po.AbstractPO;
import com.ess.core.pattern.mvc.SuperBaseService;

/**
 * Service基础接口  .
 *
 * <p>
 * Service基础接口
 *
 * @author caobaoyu
 * @date 2020/5/15 15:36
 */
public interface BaseService<K, M extends AbstractDTO<K>, P extends AbstractPO<K>>  extends SuperBaseService<K, M> {

}

