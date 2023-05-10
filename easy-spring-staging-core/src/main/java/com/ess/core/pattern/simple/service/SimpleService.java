/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.pattern.simple.service;

import com.ess.core.model.Model;
import com.ess.core.pattern.BaseService;


/**
 * 简单模式-Service基础接口  .
 *
 * <p>
 * Service基础接口
 *
 * @author caobaoyu
 * @date 2020/5/15 15:36
 */
public interface SimpleService<K, M extends Model<K>> extends BaseService<K, M> {

}
