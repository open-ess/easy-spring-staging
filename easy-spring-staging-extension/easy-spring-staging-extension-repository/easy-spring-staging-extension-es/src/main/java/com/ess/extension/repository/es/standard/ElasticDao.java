/**
 * Copyright(C) 2023 Company:深瞳大观科技有限公司
 */

package com.ess.extension.repository.es.standard;

import com.ess.core.model.po.AbstractPO;
import com.ess.extension.repository.es.AbstractElasticDao;

/**
 * 标准模式-elasticsearch-Dao基础接口  .
 *
 * @author caobaoyu
 * @date 2023/5/5 9:57
 */
public abstract class ElasticDao<K, P extends AbstractPO<K>>  extends AbstractElasticDao<K,P> implements com.ess.core.repository.BaseDao<K,P> {
}
