/**
 * Copyright(C) 2023 Company:深瞳大观科技有限公司
 */

package com.ess.extension.repository.es;

import com.ess.core.model.Query;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * <p>TODO</p> .
 *
 * @author caobaoyu
 * @date 2023/5/9 10:29
 */
@FunctionalInterface
public interface ElasticCondition {
  boolean builder(SearchSourceBuilder sourceBuilder, Query q);
}
