package com.ooad.secondchance.config;

import com.fasterxml.classmate.TypeResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Priyanka on 3/13/21.
 */
@Configuration
@ConfigurationProperties(prefix = "swagger")
@EnableSwagger2
@Getter
@Setter
public class SwaggerConfig {

    protected String title;
    protected String description;
    protected String contact;
    protected Boolean enabled;

    /**
     * Build up our API list and exclude internal endpoint paths that need not be included in that list.
     */
    @Bean
    public Docket api() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enabled)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.ooad"))
                .build()
                .useDefaultResponseMessages(false)//
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .consumes(getAllConsumeContentTypes())
                .genericModelSubstitutes(Optional.class);
    }

    private Set<String> getAllConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        // Add other media types if required in future
        consumes.add(MediaType.APPLICATION_JSON_VALUE);
        consumes.add(MediaType.ALL_VALUE);
        return consumes;
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(getClass().getPackage().getImplementationVersion())
                .contact(new Contact("","", contact))
                .build();
    }
}
