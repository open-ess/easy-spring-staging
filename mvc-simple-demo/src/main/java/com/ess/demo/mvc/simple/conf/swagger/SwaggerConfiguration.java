package com.ess.demo.mvc.simple.conf.swagger;

import com.ess.core.conf.swagger.AbstractSwaggerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
// @Profile(value = "dev")
@EnableSwagger2
public class SwaggerConfiguration extends AbstractSwaggerConfiguration {
    @Bean
    public Docket creatDocket() {
        Docket docket = createDocket(
                "mvc简单模式演示",
                "com.ess.demo.mvc.simple.modules",
                "1.0-SNAPSHOT",
                null
        );
        return docket;
    }
}


