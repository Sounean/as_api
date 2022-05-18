package com.example.as_api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.example.as_api.mapper")
@EnableSwagger2
@SpringBootApplication
public class AsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsApiApplication.class, args);
    }

    @Bean
    public HttpFirewall allowUrlSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }


}
