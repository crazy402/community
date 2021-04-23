package com.crazy.community.util;

import com.crazy.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @ClassName HostHolder
 * @Description //持有用户信息， 用户代替session对象
 * @Author crazy402
 * @Date 2021/4/23 22:16
 * @Version 1.0
 **/
@Component
public class HostHolder {
    private ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public void setUser(User user){
        userThreadLocal.set(user);
    }
    public User getUser() {
        return userThreadLocal.get();
    }

    public void clear() {
        userThreadLocal.remove();
    }
}
