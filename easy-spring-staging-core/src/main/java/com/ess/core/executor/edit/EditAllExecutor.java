package com.ess.core.executor.edit;

import com.ess.core.model.Model;
import com.ess.core.sercurity.AuthorizationUser;

/**
 * 抽象完全修改执行器
 * @param <K> 模型主键类型
 * @param <M> 模型类型
 *
 * @author caobaoyu
 * @date 2022/10/1 23:00
 */
public interface EditAllExecutor<K, M extends Model<K>> {
    void execute(K k, AuthorizationUser<?, ?, ?, ?> u, M m) throws Exception;
}