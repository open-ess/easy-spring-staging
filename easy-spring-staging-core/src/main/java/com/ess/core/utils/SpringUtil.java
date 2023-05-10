/**
 * Copyright(C) 2021 Company:北京神州泰岳软件股份有限公司
 */
package com.ess.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * spring 工具类.
 *
 * @author caobaoyu
 * @date 2021-09-14 15:39
 **/
public class SpringUtil {

    public static HttpServletRequest getServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
