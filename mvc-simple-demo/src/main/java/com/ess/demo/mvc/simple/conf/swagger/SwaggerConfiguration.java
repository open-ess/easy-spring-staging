package com.ess.demo.mvc.simple.conf.swagger;

import com.ess.core.conf.swagger.AbstractSwaggerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@Configuration
// @Profile(value = "dev")
@EnableSwagger2WebMvc
public class SwaggerConfiguration extends AbstractSwaggerConfiguration {
    @Bean
    public Docket creatDocket() {
        return createDocket(
                "mvc简单模式演示",
                "com.ess.demo.mvc.simple.modules",
                "1.0-SNAPSHOT",
                null
        );
    }
}


