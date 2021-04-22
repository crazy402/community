package com.crazy.community;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @ClassName LoggeTest
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/23 0:43
 * @Version 1.0
 **/
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void testLogger() {
        System.out.println(logger.getName());
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
    }
}
