package com.ksy.chatserver.service.impl;

import com.ksy.chatserver.mapper.UserMapper;
import com.ksy.chatserver.pojo.User;
import com.ksy.chatserver.service.UserService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


/**
 * @author Admin
 * @PackageName com.ksy.chatclient.service.impl
 * @ClassName chat-room
 * @Description
 * @create 2022-03-13 13:52
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public Boolean selectUserByName(String name, String password, String ip) {
        User user =  userMapper.selectUserByName(name);
        if (user == null) {
            return false;
        }
        byte[] bytes = password.getBytes();
        String encodePassword = MD5Encoder.encode(bytes);
        String userPassword = user.getPassword();
        if (encodePassword.equals(userPassword)) {
            Timestamp loginTime = new Timestamp(System.currentTimeMillis());
            user.setLoginTime(loginTime);
            user.setLoginIp(ip);
            return true;
        }
        return false;
    }
}
