/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.pattern.mvc.simple.service;

import com.ess.core.model.Model;
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
public interface BaseService<K, M extends Model<K>> extends SuperBaseService<K, M> {

}
