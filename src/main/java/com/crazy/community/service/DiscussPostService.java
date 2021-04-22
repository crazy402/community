package com.crazy.community.service;

import com.crazy.community.dao.DiscussPostMapper;
import com.crazy.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DiscussPostService
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/22 17:05
 * @Version 1.0
 **/
@Service
public class DiscussPostService {
    @Autowired
    DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit) {

        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public Integer findDiscussPostRows(Integer userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
