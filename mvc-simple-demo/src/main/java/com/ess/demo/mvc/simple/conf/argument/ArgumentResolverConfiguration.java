package com.ess.demo.mvc.simple.conf.argument;

import com.ess.core.argument.AbstractUserParamArgumentResolver;
import com.ess.core.conf.argument.AbstractArgumentResolverConfiguration;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.core.sercurity.DefaultAuthorizationUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class ArgumentResolverConfiguration extends AbstractArgumentResolverConfiguration {
    /**
     * 构建出来swagger参数外的其他参数解析器
     * 
     * @return
     */
    @Override
    public List<HandlerMethodArgumentResolver> builderArgumentResolver() {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        // 解析用户参数,通常通过token进行用户信息的构建1
        AbstractUserParamArgumentResolver user = new AbstractUserParamArgumentResolver(){
            @Override
            public AuthorizationUser<?, ?, ?, ?> parserUser(MethodParameter parameter, NativeWebRequest webRequest) {
                return new DefaultAuthorizationUser();
            }
        };
        argumentResolvers.add(user);
        return argumentResolvers;
    }
}

