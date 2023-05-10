/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.pattern.standard.controller;

import com.ess.core.model.convert.ControllerConverter;
import com.ess.core.model.dto.AbstractDTO;
import com.ess.core.model.vo.AbstractAddVO;
import com.ess.core.model.vo.AbstractDetailVO;
import com.ess.core.model.vo.AbstractEditVO;
import com.ess.core.model.vo.AbstractListVO;
import com.ess.core.pattern.BaseRest;

/**
 * 标准模式-Rest接口约束  .
 *
 * <p>
 * 标准模式-Rest接口约束
 *
 * @author caobaoyu
 * @date 2020/5/16 23:51
 */
public interface StandardRest<K, DV extends AbstractDetailVO<K>, LV extends AbstractListVO<K>, AV extends AbstractAddVO<Void>, EV extends AbstractEditVO<K>,DTO extends AbstractDTO<K>> extends BaseRest {

    /**
     * 获取参数转换器
     *
     * @return 参数转换器
     *
     * @author caobaoyu
     * @date 2020/5/16 23:53
     */
    ControllerConverter<K, DV, LV, AV,EV, DTO> getConverter();

}
