package com.example.as_api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.as_api.mapper")
@SpringBootApplication
public class AsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsApiApplication.class, args);
    }

}
