/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.pattern.standard.service;

import com.ess.core.model.dto.AbstractDTO;
import com.ess.core.model.po.AbstractPO;
import com.ess.core.pattern.BaseService;

/**
 * 标准模式-Service基础接口  .
 *
 * <p>
 * Service基础接口
 *
 * @author caobaoyu
 * @date 2020/5/15 15:36
 */
public interface StandardService<K, DTO extends AbstractDTO<K>, PO extends AbstractPO<K>>  extends BaseService<K, DTO> {

}

