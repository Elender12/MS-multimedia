package com.ecirstea.multimedia.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;



@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-25T11:13:13.435Z[GMT]")
@Configuration
public class SwaggerDocumentationConfig
{

    @Value("${destination.environment}")
    private String destinationEnvironment;



    ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
            .title("Multimedia API (" + destinationEnvironment + ")")
            .description("Multimedia microservice. Detailed description of the Api.")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .termsOfServiceUrl("")
            .version("1.0.0")
            .contact(new Contact("Elena C.", "", "elender1230@gmail.com"))
            .build();
    }



    @Bean
    public Docket customImplementation()
    {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .securityContexts(Arrays.asList(securityContext()))
            .securitySchemes(Arrays.asList(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ecirstea.multimedia.controller"))
            .build()
            .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
            .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class);
    }



    private ApiKey apiKey()
    {
        return new ApiKey("JWT", "Authorization", "header");
    }



    private SecurityContext securityContext()
    {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();
    }



    private AuthorizationScope[] authorizationScopes()
    {
        return new AuthorizationScope[]{
            new AuthorizationScope("global", "write accessEverything")
        };
    }



    List<SecurityReference> defaultAuth()
    {
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes()));
    }

}
