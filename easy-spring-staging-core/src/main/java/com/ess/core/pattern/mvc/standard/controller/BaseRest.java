/**
 * Copyright(C) 2020 Company:easy-spring-staging Co.
 */
package com.ess.core.pattern.mvc.standard.controller;

import com.ess.core.model.convert.ControllerConverter;
import com.ess.core.model.dto.AbstractDTO;
import com.ess.core.model.vo.AbstractAddVO;
import com.ess.core.model.vo.AbstractDetailVO;
import com.ess.core.model.vo.AbstractEditVO;
import com.ess.core.model.vo.AbstractListVO;

import java.util.Map;

/**
 * 功能简介  .
 *
 * <p>
 * 功能详细描述
 *
 * @author caobaoyu
 * @date 2020/5/16 23:51
 */
public interface BaseRest<K, DV extends AbstractDetailVO<K>, LV extends AbstractListVO<K>, AV extends AbstractAddVO<Void>, EV extends AbstractEditVO<K>,DTO extends AbstractDTO<K>> {
    /**
     * 获取排序字段映射
     *
     * @return 字段映射
     * @author caobaoyu
     * @date 2020/5/16 23:53
     */
    Map<String, String> getColumnMap();


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
