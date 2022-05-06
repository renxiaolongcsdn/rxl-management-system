package com.rxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ren.xiaolong
 * @date 2022/4/17
 * @Description
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.rxl.dao","com.rxl.*"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
