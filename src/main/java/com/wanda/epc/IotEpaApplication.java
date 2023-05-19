package com.wanda.epc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = "com.wanda.epc.mapper")
public class IotEpaApplication {

    public static void main(String[] args)  {
        try {
            SpringApplication.run(IotEpaApplication.class, args);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}
