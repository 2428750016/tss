package com.yuhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yuhang.dao")
@SpringBootApplication
public class TangpoetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TangpoetryApplication.class, args);
    }

}

