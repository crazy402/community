package com.crazy.community;

import com.crazy.community.dao.DiscussPostMapper;
import com.crazy.community.dao.LoginTicketMapper;
import com.crazy.community.dao.UserMapper;
import com.crazy.community.entity.DiscussPost;
import com.crazy.community.entity.LoginTicket;
import com.crazy.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private DiscussPostMapper discussPostMapper;

  @Autowired
  private LoginTicketMapper loginTicketMapper;

  @Test
  void contextLoads() {}

  @Test
  public void testSelectUser() {

    User user = userMapper.selectById(1);
    System.out.println(user);
  }

  @Test
  public void testSelectPost() {
    List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 0, 10);
    for (DiscussPost post : list) {
      System.out.println(post);
    }

    Integer rows = discussPostMapper.selectDiscussPostRows(0);
    System.out.println(rows);
  }

  @Test
  public void testInsertLogin() {
    LoginTicket loginTicket = new LoginTicket();
    loginTicket.setUserId(101);
    loginTicket.setTicket("abc");
    loginTicket.setStatus(0);
    loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
    loginTicketMapper.insertLoginTicket(loginTicket);

  }

  @Test
  public void testSelectTicket() {
    LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
    System.out.println(loginTicket);

    loginTicketMapper.updateStatus("abc", 1);
    System.out.println(loginTicketMapper.selectByTicket("abc"));
  }



}
