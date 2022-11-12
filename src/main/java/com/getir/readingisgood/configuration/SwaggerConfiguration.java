package com.getir.readingisgood.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static springfox.documentation.service.ParameterType.HEADER;


@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .securitySchemes(singletonList(apiKey()))
                .securityContexts(singletonList(getSecurityContext()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.getir"))
                .paths(PathSelectors.any())
                .build();
    }

    private SecurityContext getSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(singletonList(SecurityReference.builder()
                        .reference(AUTHORIZATION)
                        .scopes(new AuthorizationScope[0])
                        .build()))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(AUTHORIZATION, AUTHORIZATION, HEADER.getIn());
    }
}
