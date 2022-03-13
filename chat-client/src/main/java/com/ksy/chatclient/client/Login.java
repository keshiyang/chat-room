package com.ksy.chatclient.client;


import com.alibaba.fastjson.JSONObject;
import com.ksy.chatclient.window.WindowClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Admin
 * @PackageName com.ksy.chatclient.service
 * @ClassName chat-room
 * @Description
 * @create 2022-03-13 12:01
 */

public class Login {


    public void login(String userName, String password) throws IOException {
        String urlPost = "https://wangrui.world:30001/login";
        HashMap<String, Object> hashMap = new HashMap<String, Object>(3);
        RestTemplate restTemplate = new RestTemplate();
        hashMap.put("username", userName);
        hashMap.put("password", password);
        JSONObject jsonObject = restTemplate.postForObject(urlPost, hashMap, JSONObject.class);
        String message = "登录失败";
        if (jsonObject != null) {
            message = jsonObject.getString("responseMessage");
            String loginIp = jsonObject.getString("loginIp");
            WindowClient client = new WindowClient();
            client.init(userName,loginIp);
        }
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
