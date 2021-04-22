package com.crazy.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName DisussPost
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/22 16:39
 * @Version 1.0
 **/
@Data
public class DiscussPost {
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private Date createTime;
    private Integer commentCount;
    private Double score;
}
