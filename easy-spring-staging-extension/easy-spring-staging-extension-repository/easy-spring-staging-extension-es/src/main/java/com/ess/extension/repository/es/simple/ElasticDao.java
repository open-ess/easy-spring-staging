/**
 * Copyright(C) 2023 Company:深瞳大观科技有限公司
 */

package com.ess.extension.repository.es.simple;

import com.ess.core.model.Model;
import com.ess.extension.repository.es.AbstractElasticDao;

/**
 * 简单模式-elasticsearch-Dao基础接口  .
 *
 * @author caobaoyu
 * @date 2023/5/5 9:57
 */
public abstract class ElasticDao<K, M extends Model<K>> extends AbstractElasticDao<K,M> implements com.ess.core.repository.BaseDao<K,M> {
}
