package com.example.elasticsearch;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Administrator
 */

@SpringBootApplication
//@MapperScan("com.example.elasticsearch.mapper")
//@ComponentScan(basePackages = "com.example.elasticsearch.controller")
//@ComponentScan(basePackages = "com.example.elasticsearch.service")
public class ElasticsearchApplication {
    //private Logger logger = LoggerFactory.getLogger(ElasticsearchApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

}
