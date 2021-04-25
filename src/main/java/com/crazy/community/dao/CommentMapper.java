package com.crazy.community.dao;

import com.crazy.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectCommentByEntity(@Param("entityType") Integer entityType,
                                        @Param("entityId") Integer entityId,
                                        @Param("offset") Integer offset,
                                        @Param("limit") Integer limit);

    Integer selectCountByEntity(@Param("entityType") Integer entityType,
                                @Param("entityId") Integer entityId);

    Integer insertComment(@Param("comment") Comment comment);
}
