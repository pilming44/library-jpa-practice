package com.jpa.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("도서관 관리 시스템 - 학습용 toy프로젝트")
                        .version("1.0")
                        .description("공부했던 내용들을 실습해보려고 만든 프로젝트입니다.")
                        .contact(new Contact().name("최재현").email("pilming44@naver.com").url("https://github.com/pilming44")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local server")));
    }
}
