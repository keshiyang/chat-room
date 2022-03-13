package com.ksy.chatserver.Controller;

import com.alibaba.fastjson.JSONObject;
import com.ksy.chatserver.pojo.User;
import com.ksy.chatserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 * @PackageName com.ksy.chatserver.Controller
 * @ClassName chat-room
 * @Description
 * @create 2022-03-13 21:19
 */
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public JSONObject login(@RequestBody User user) {
        Boolean result = userService.selectUserByName(user.getUserName(),user.getPassword(),user.getLoginIp());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "登录失败");
        if (result) {
            jsonObject.put("message", "登录成功");
            jsonObject.put("loginIp",user.getLoginIp());
        }
        return jsonObject;
    }
}
