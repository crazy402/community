package com.crazy.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName User
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/22 15:27
 * @Version 1.0
 **/
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private Integer type;
    private Integer status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;

}
