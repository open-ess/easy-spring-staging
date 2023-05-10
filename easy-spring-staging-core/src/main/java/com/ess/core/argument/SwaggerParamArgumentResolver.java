package com.ess.core.argument;

import com.ess.core.model.Query;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * 解析swagger参数参数解析器
 *
 * @author caobaoyu
 * @date 2022/10/1 22:44
 */
public class SwaggerParamArgumentResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(SwaggerParam.class) && parameter.hasMethodAnnotation(ApiImplicitParams.class);
  }

  @Override
  public Query resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Query query = new Query();
    ApiImplicitParams apiImplicitParams = parameter.getMethodAnnotation(ApiImplicitParams.class);
    if (apiImplicitParams != null) {
      ApiImplicitParam[] apiImplicitParamArray = apiImplicitParams.value();
      if (apiImplicitParamArray != null) {
        for (ApiImplicitParam apiImplicitParam : apiImplicitParamArray) {
          String paramType = apiImplicitParam.paramType();
//          boolean required = apiImplicitParam.required();
          String name = apiImplicitParam.name();
          String dataType = apiImplicitParam.dataType();
//          String defaultValue = apiImplicitParam.defaultValue();
          boolean allowMultiple = apiImplicitParam.allowMultiple();
          String format = apiImplicitParam.format();
          if (allowMultiple) {
            String[] parameterValues = ParameterUtil.getParamValues(name, paramType, webRequest);
            List<Object> parameterObjectList = ParameterUtil.convertList(parameterValues, dataType, format);
            if (parameterObjectList != null) {
              query.put(name, parameterObjectList);
            }
          } else {
            String parameterValue = ParameterUtil.getParamValue(name, paramType, webRequest);
            Object parameterObject = ParameterUtil.convert(parameterValue, dataType, format);
            if (parameterObject != null) {
              query.put(name, parameterObject);
            }
          }
        }
      }
    }
    return query;
  }
}
