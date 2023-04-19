/**
 * Copyright(C) 2021 Company:easy-spring-staging Co.
 */
package com.ess.core.model.convert;


import com.ess.core.model.dto.AbstractDTO;
import com.ess.core.model.po.AbstractPO;

import java.util.List;

/**
 * 抽象转换器接口.
 *
 * @author caobaoyu
 * @date 2021-09-04 16:16
 **/
public interface ServiceConverter<K, M extends AbstractDTO<K>, P extends AbstractPO<K>>{
    M poToDto(P p);
    List<M> poToDto(List<P> ps);
    P dtoToPo(M m);
    List<P> dtoToPo(List<M> ma);
}
