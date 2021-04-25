package com.crazy.community.dao;

import com.crazy.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author crazy402
 */
@Mapper
public interface UserMapper {
  /**
   * 根据id查询
   * @param id
   * @return
   */
  User selectById(@Param("id") Integer id);

  /**
   * 根据名字查询
   * @param username
   * @return
   */
  User selectByName(@Param("username") String username);

  /**
   * 根据邮箱查询
   * @param email
   * @return
   */
  User selectByEmail(@Param("email") String email);

  Integer insertUser(@Param("user") User user);

  Integer updateStatus(@Param("id") Integer id, @Param("status") Integer status);

  Integer updateHeader(@Param("id") Integer id, @Param("headerUrl") String headerUrl);

  Integer updatePassword(@Param("id") Integer id, @Param("password") String password);
}
