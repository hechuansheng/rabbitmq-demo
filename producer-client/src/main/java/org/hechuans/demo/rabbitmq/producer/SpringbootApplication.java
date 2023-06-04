package org.hechuans.demo.rabbitmq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author : hechuansheng
 * @date : 2023/5/30 16:45
 * @description :
 * @since : version-1.0
 */
@SpringBootApplication
@EnableConfigurationProperties
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
