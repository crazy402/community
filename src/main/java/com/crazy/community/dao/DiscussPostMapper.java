package com.crazy.community.dao;

import com.crazy.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author crazy402
 */
@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(@Param("userId") Integer userId,
                                         @Param("offset") Integer offset,
                                         @Param("limit") Integer limit);

    Integer selectDiscussPostRows(@Param("userId") Integer userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(@Param("id") Integer id);


}
