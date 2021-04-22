package com.crazy.community.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisConfig
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/22 15:23
 * @Version 1.0
 **/
@Configuration
@MapperScan("com.crazy.community.dao")
public class MybatisConfig {

}
