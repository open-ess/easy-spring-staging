/**
 * Copyright: Copyright(C) 2020 Easy-Java-Rest-Framework.
 */
package com.ess.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * WEB 响应模型类  .
 *
 * <p>
 * WEB 响应模型类
 *
 * @author caobaoyu
 * @date 2020/3/20 16:30
 */
@ApiModel(value = "RestResult", description = "响应结果")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RestResult<D> implements Serializable {

    /**
     * 创建响应成功项目模型
     *
     * @param d   响应数据
     * @param <D> 响应数据类型
     * @return 响应模型
     * @author caobaoyu
     * @date 2020/3/20 16:32
     */
    public static <D> RestResult<D> success(D d) {
        return new RestResult<>(RestStatus.OK.getCode(), RestStatus.OK.getMessage(), d);
    }

    /**
     * 创建响应失败项目模型
     *
     * @param restStatus 响应状态码,通常不为OK
     * @param d            响应数据
     * @param <D>          响应数据类型
     * @return 响应模型
     * @author caobaoyu
     * @date 2020/3/20 16:34
     */
    public static <D> RestResult<D> fail(RestStatus restStatus, D d) {
        return new RestResult<>(restStatus.getCode(), restStatus.getMessage(), d);
    }

    /**
     * 创建响应通用项目模型
     *
     * @param code    状态码
     * @param message 描述信息
     * @param d       响应数据
     * @param <D>     响应数据类型
     * @return 响应模型
     */
    public static <D> RestResult<D> create(Integer code, String message, D d) {
        return new RestResult<>(code, message, d);
    }

    /**
     * 创建响应模型构造
     *
     * @param code    状态码
     * @param message 描述信息
     * @param data    响应数据
     */
    public RestResult(Integer code, String message, D data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 状态码
    @ApiModelProperty(value = "状态码", required = true)
    private Integer code;

    // 描述信息
    @ApiModelProperty(value = "描述信息", required = true)
    private String message;

    // 响应数据
    @ApiModelProperty(value = "响应数据")
    private D data;


}
