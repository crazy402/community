package com.crazy.community;

import com.crazy.community.dao.DiscussPostMapper;
import com.crazy.community.dao.UserMapper;
import com.crazy.community.entity.DiscussPost;
import com.crazy.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private DiscussPostMapper discussPostMapper;

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

}
