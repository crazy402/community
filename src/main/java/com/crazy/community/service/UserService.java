package com.crazy.community.service;

import com.crazy.community.dao.UserMapper;
import com.crazy.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/22 17:09
 * @Version 1.0
 **/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(Integer id) {
        return userMapper.selectById(id);
    }
}
