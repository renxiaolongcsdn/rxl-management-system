package com.rxl.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ren.xiaolong
 * @date 2022/4/17
 * @Description  引入 mybatis-plus 分页插件
 *
 */
@EnableTransactionManagement    //必须开启事务管理才可以生效
@Configuration                   //代表是一个配置类
@MapperScan("com.rxl.dao")       //扫描包
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
