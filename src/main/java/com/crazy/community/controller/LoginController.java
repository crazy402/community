package com.crazy.community.controller;

import com.crazy.community.entity.User;
import com.crazy.community.service.UserService;
import com.crazy.community.util.CommunityConstant;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName LoginController
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/23 15:22
 * @Version 1.0
 **/
@Controller
public class LoginController implements CommunityConstant {
    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String getRegisterPage(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("user",user);
            model.addAttribute("msg", "注册成功，我们已经发送一份激活邮件");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }

    }

  /**
   * 激活
   * @param model
   * @param userId
   * @param code
   * @return
   */
  @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
  public String activation(
      Model model, @PathVariable("userId") Integer userId, @PathVariable("code") String code) {
        Integer result = userService.activation(userId, code);
        if (result == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功！");
            model.addAttribute("target", "/login");
        }  else if (result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "该账号已经激活过！");
            model.addAttribute("target", "/index");
        }  else {
            model.addAttribute("msg", "激活失败！");
            model.addAttribute("target", "/index");

        }
        return "/site/operate-result";

    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response,
                           HttpSession session) {
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 讲验证码存入session
        session.setAttribute("kaptcha", text);

        // 讲图片输出给浏览器
        response.setContentType("image/png");
        try {
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
