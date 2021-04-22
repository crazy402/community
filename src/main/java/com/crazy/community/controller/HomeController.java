package com.crazy.community.controller;

import com.crazy.community.entity.DiscussPost;
import com.crazy.community.entity.Page;
import com.crazy.community.entity.User;
import com.crazy.community.service.DiscussPostService;
import com.crazy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HomeController
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/22 17:13
 * @Version 1.0
 **/

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        ArrayList<Map<String, Object>> disussPosts = new ArrayList<>();

        if (list != null) {
            for (DiscussPost post : list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);

                disussPosts.add(map);
            }
        }

        model.addAttribute("disussPosts", disussPosts);
        model.addAttribute("page", page);
        return "/index";
    }

}
