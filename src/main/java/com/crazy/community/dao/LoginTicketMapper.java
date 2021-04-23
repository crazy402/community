package com.crazy.community.dao;

import com.crazy.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {
    @Insert({
            "insert into login_ticket(user_id, ticket, status, expired)",
            "values(#{userId}, #{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id, user_id, ticket, status, expired",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(@Param("ticket") String ticket);

    @Update({
            "update login_ticket set status=#{status} where ticket=#{ticket}"
    })
    Integer updateStatus(@Param("ticket") String ticket, @Param("status") Integer status);
}
