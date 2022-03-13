package com.ksy.chatclient;

import com.ksy.chatclient.window.LoginClient;
import com.ksy.chatclient.window.WindowClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.Mapping;

import javax.swing.*;

/**
 * @author Admin
 */
@SpringBootApplication
public class ChatClientApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginClient loginClient = new LoginClient();
            loginClient.createAndShowGUI();
        });
        SpringApplication.run(ChatClientApplication.class, args);
    }

}
