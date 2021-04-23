package com.crazy.community;

import com.crazy.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @ClassName MailTest
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/23 14:08
 * @Version 1.0
 **/
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testMail() {
        mailClient.setMailSender("1224668355@qq.com","test", "Welcome");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "test");

        String process = templateEngine.process("/mail/demo", context);
        System.out.println(process);

        mailClient.setMailSender("1224668355@qq.com", "HTML", process);

    }
}
