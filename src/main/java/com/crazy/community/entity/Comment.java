package com.crazy.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName Comment
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/25 18:12
 * @Version 1.0
 **/
@Data
public class Comment {
    private Integer id;
    private Integer userId;
    private Integer entityType;
    private Integer entityId;
    /**
     * 回复的目标id
     */
    private Integer targetId;
    private String content;
    private Integer status;
    private Date createTime;
}
