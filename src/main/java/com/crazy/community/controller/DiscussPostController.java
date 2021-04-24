package com.crazy.community.controller;

import com.crazy.community.entity.DiscussPost;
import com.crazy.community.entity.User;
import com.crazy.community.service.DiscussPostService;
import com.crazy.community.service.UserService;
import com.crazy.community.util.CommunityUtil;
import com.crazy.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @ClassName DiscussPostController
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/24 23:11
 * @Version 1.0
 **/
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJsonString(403, "你还没有登录");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        post.setType(user.getType());
        post.setStatus(user.getStatus());
        discussPostService.addDiscussPost(post);

        return CommunityUtil.getJsonString(0,"发布成功");
    }

    @GetMapping("/detail/{discussPostId}")
    public String getDiscussPost(@PathVariable("discussPostId") Integer discussPostId, Model model) {
        // 查询出帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post", post);
        // 查询出作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user", user);

        return "/site/discuss-detail";

    }
}
