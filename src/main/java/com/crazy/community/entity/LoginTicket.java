package com.crazy.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName LoginTicket
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/23 20:27
 * @Version 1.0
 **/
@Data
public class LoginTicket {
    private Integer id;
    private Integer userId;
    private String ticket;
    private Integer status;
    private Date expired;
}
