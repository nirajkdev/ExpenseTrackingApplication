package com.cd;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpenseTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackingApplication.class, args);
    }

    @Bean
    public OpenAPI springTodoOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Expense Tracking Application")
                        .description("Expense Tracking Application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Expense Tracking Application")
                        .url("https://Todo.wiki.github.org/docs"));
    }
}
