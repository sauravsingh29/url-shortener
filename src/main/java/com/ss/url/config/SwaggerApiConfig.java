package com.ss.url.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerApiConfig {

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("SS")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("URL Shortening rest api documentation")
                .description("URL Shortening rest api with with Swagger Api documentation")
                .termsOfServiceUrl("https://www.ssingh.com/terms-of-use")
                .contact(new Contact("Saurav Singh", "www.ssingh.com", "sauravsingh7@outlook.om"))
                .title("URL Shortening APIs")
                .version("2.0")
                .build();
    }
}
