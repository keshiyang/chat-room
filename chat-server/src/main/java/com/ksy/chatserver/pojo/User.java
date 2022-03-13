package com.ksy.chatserver.pojo;

import java.sql.Timestamp;

/**
 * @author Admin
 * @PackageName com.ksy.chatclient.pojo
 * @ClassName chat-room
 * @Description
 * @create 2022-03-13 12:13
 */

public class User {
    Integer id;
    String userName;
    String password;
    String loginIp;
    Timestamp createTime;
    Timestamp loginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
}
