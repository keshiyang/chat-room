package com.ksy.chatclient.client;

import com.ksy.chatclient.window.WindowClient;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Admin
 * @PackageName com.ksy.chatroom.client
 * @ClassName chatroom
 * @Description
 * @create 2022-03-12 20:10
 */

public class ReceiveClient implements Runnable{
    private Socket s;
    public ReceiveClient(Socket s) {
        this.s = s;
    }
    @Override
    public void run() {
        try {
            //信息接收流
            BufferedReader brIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while(true){
                char info = (char)brIn.read();//读取信息流首字符，判断信息类型
                String line = brIn.readLine();//读取信息流内容

                if(info == '1'){//代表发送的是消息
                    WindowClient.textMessage.append(line + "\r\n");	//将消息添加到文本域中
                    //设置消息显示最新一行，也就是滚动条出现在末尾，显示最新一条输入的信息
                    WindowClient.textMessage.setCaretPosition(WindowClient.textMessage.getText().length());
                }

                if(info == '2' || info == '3'){//有新用户加入或退出，2为加入，3为退出
                    String sub = line.substring(1, line.length()-1);
                    String[] data = sub.split(",");
                    WindowClient.user.clearSelection();
                    WindowClient.user.setListData(data);
                }

                if(info == '4'){//4代表服务端退出
                    WindowClient.link.setText("连接");
                    WindowClient.exit.setText("已退出");
                    WindowClient.socket.close();
                    WindowClient.socket = null;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(WindowClient.window, "客户端已退出连接");
        }
    }

}
