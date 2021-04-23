package com.crazy.community.service;

import com.crazy.community.dao.UserMapper;
import com.crazy.community.entity.User;
import com.crazy.community.util.CommunityConstant;
import com.crazy.community.util.CommunityUtil;
import com.crazy.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName UserService
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/22 17:09
 * @Version 1.0
 **/
@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public User findUserById(Integer id) {
        return userMapper.selectById(id);
    }

    public Map<String, Object> register(User user) {
        HashMap<String, Object> map = new HashMap<>();
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("username", "账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("username", "密码不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("username", "Email不能为空");
            return map;
        }
        //验证账号
        User name = userMapper.selectByName(user.getUsername());
        if (name != null) {
            map.put("usernameMsg", "账号已经存在");
            return map;
        }
        //验证邮箱
        User email = userMapper.selectByEmail(user.getEmail());
        if (email != null) {
            map.put("emailMsg", "邮箱已经被注册");
            return map;
        }
        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());

        userMapper.insertUser(user);

        //激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        String url = domain + contextPath + "/activation" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String process = templateEngine.process("/mail/activation", context);
        mailClient.setMailSender(user.getEmail(), "激活账号", process);

        return map;
    }

    public Integer activation(Integer userId, String code) {
        User user = userMapper.selectById(userId);

        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        }  else {
            return ACTIVATION_FAILURE;
        }
    }


}
