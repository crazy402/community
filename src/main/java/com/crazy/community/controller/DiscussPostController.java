package com.crazy.community.controller;

import com.crazy.community.entity.Comment;
import com.crazy.community.entity.DiscussPost;
import com.crazy.community.entity.Page;
import com.crazy.community.entity.User;
import com.crazy.community.service.CommentService;
import com.crazy.community.service.DiscussPostService;
import com.crazy.community.service.UserService;
import com.crazy.community.util.CommunityConstant;
import com.crazy.community.util.CommunityUtil;
import com.crazy.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @ClassName DiscussPostController
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/24 23:11
 * @Version 1.0
 **/
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

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
    public String getDiscussPost(@PathVariable("discussPostId") Integer discussPostId,
                                 Model model,
                                 Page page) {
        // 查询出帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post", post);
        // 查询出作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user", user);
        // 评论分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail/" + discussPostId);
        page.setRows(post.getCommentCount());


        // 评论： 给帖子的评论
        // 回复：
        // 评论列表 commentList找到帖子的评论，commentVoList是对帖子评论的具体信息进行查找
        List<Comment> commentList = commentService.findCommentByEntity(
                ENTITY_TYPE_POST, post.getUserId(), page.getOffset(), page.getLimit());

        // 评论VO列表 VO是值对象     commentVoList里面放的是一条帖子的多个评论，map对象代表一条评论以及对应回复
        List<Map<String, Object>> commentVoList = new ArrayList<>();

        if (commentList != null) {
            for (Comment comment : commentList) {
                // 评论VO
                Map<String, Object> commentVo = new HashMap<>();
                // 评论的信息
                commentVo.put("comment", comment);
                // 评论的作者
                commentVo.put("user", userService.findUserById(comment.getUserId()));

                // 回复列表
                List<Comment> replyList = commentService.findCommentByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
                // 回复VO列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if (replyVoList != null) {
                    for (Comment reply : replyList) {
                        HashMap<String, Object> replayVo = new HashMap<>();
                        // 回复
                        replayVo.put("reply", reply);
                        // 作者
                        replayVo.put("user", userService.findUserById(reply.getUserId()));

                        // 回复的目标
                        User target = reply.getTargetId() == 0 ? null : userService.findUserById(reply.getTargetId());
                        replayVo.put("target", target);

                        replyVoList.add(replayVo);

                    }
                }
                // 将回复列表装到评论VO中
                commentVo.put("replys", replyVoList);

                // 回复数量
                Integer replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("replyCount", replyCount);

                commentVoList.add(commentVo);


            }
        }
        model.addAttribute("comments", commentVoList);


        return "/site/discuss-detail";

    }
}
