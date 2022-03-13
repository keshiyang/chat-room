package com.ksy.chatserver.service;

/**
 * @author Admin
 * @PackageName com.ksy.chatclient.service
 * @ClassName chat-room
 * @Description
 * @create 2022-03-13 13:52
 */
public interface UserService {
    /**
     * select by username
     * @param name
     * @param password
     * @param ip
     * @return
     */
    Boolean selectUserByName(String name,String password,String ip);
}
