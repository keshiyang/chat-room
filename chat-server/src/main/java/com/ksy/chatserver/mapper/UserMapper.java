package com.ksy.chatserver.mapper;

import com.ksy.chatserver.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Admin
 * @PackageName com.ksy.chatclient
 * @ClassName chat-room
 * @Description
 * @create 2022-03-13 12:12
 */
@Mapper
public interface UserMapper {
     /**
      * select by userName
      * @param userName
      * @return
      */
     User selectUserByName(String userName);
}
