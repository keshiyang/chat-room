package com.ksy.chatclient.window;

import com.ksy.chatclient.client.ReceiveClient;
import com.ksy.chatclient.client.SendClient;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Admin
 * @PackageName com.ksy.chatclient.window
 * @ClassName chat-room
 * @Description
 * @create 2022-03-12 20:38
 */
@Service
public class WindowClient {
    JTextField port,name,ip,message;
    JButton send;
    public static JFrame window;
    public static JButton link,exit;
    public static JTextArea textMessage;
    public static Socket socket = null;
    public static JList<String> user;

    //窗体初始化内容
    public void init(String userName,String loginIp){//采用绝对布局
        window = new JFrame("客户端");
        window.setLayout(null);
        window.setBounds(200, 200, 500, 400);
        window.setResizable(false);

        JLabel label = new JLabel("主机IP:");
        label.setBounds(10, 8, 50, 30);
        window.add(label);

        ip = new JTextField();
        ip.setBounds(55, 8, 60, 30);
        ip.setText(loginIp);
        window.add(ip);


        JLabel label1 = new JLabel("端口号:");
        label1.setBounds(125, 8, 50, 30);
        window.add(label1);

        port = new JTextField();
        port.setBounds(170, 8, 40, 30);
        port.setText("30000");
        window.add(port);

        JLabel names = new JLabel("用户名:");
        names.setBounds(220, 8, 55, 30);
        window.add(names);

        name = new JTextField();
        name.setBounds(265, 8, 60, 30);
        name.setText("客户端1");
        window.add(name);

        link = new JButton("连接");
        link.setBounds(335, 8, 75, 30);
        window.add(link);

        exit = new JButton("退出");
        exit.setBounds(415, 8, 75, 30);
        window.add(exit);

        JLabel label2 = new JLabel("用户列表");
        label2.setBounds(40, 40, 80, 30);
        window.add(label2);

        user = new JList<String>();
        JScrollPane scrollPane = new JScrollPane(user);//设置滚动条
        scrollPane.setBounds(10, 70, 120, 220);
        window.add(scrollPane);

        textMessage = new JTextArea();
        textMessage.setBounds(135, 70, 340, 220);
        textMessage.setEditable(false);//文本不可编辑
        textMessage.setBorder(new TitledBorder("聊天记录"));//设置标题
        //文本内容换行的两个需要配合着用
        textMessage.setLineWrap(true);//设置文本内容自动换行，在超出文本区域时，可能会切断单词
        textMessage.setWrapStyleWord(true);//设置以自动换行，以单词为整体，保证单词不会被切断
        JScrollPane scrollPane1 = new JScrollPane(textMessage);//设置滚动条
        scrollPane1.setBounds(135, 70, 340, 220);
        window.add(scrollPane1);

        message = new JTextField();
        message.setBounds(10, 300, 360, 50);
        message.setText(null);
        window.add(message);

        send = new JButton("发送");
        send.setBounds(380, 305, 70, 40);
        window.add(send);

        myEvent();//添加监听事件
        window.setVisible(true);//设置窗体可见
    }
    public void myEvent(){//事件监听
        window.addWindowListener(new WindowAdapter() {//退出窗体
            @Override
            public void windowClosing(WindowEvent e){
                //如果仍在连接，发信息给服务端，并退出
                if(socket != null && socket.isConnected()){
                    try {
                        new SendClient(socket, getName(), 3 + "");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                System.exit(0);
            }
        });

        //关闭连接
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //如果仍在连接，将信息发给服务端
                if(socket == null){
                    JOptionPane.showMessageDialog(window, "已关闭连接");
                }else if(socket != null && socket.isConnected()){
                    try {
                        new SendClient(socket, getName(), "3");//发送信息给服务端
                        link.setText("连接");
                        exit.setText("已退出");
                        socket.close();//关闭socket
                        socket = null;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //建立连接
        link.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //判断是否已经连接成功
                if(socket != null && socket.isConnected()){
                    JOptionPane.showMessageDialog(window, "已经连接成功！");
                }else {
                    String ipString = ip.getText();//获取ip地址
                    String portClinet = port.getText();//获取端口号

                    if("".equals(ipString) || "".equals(portClinet)){//判断获取内容是否为空
                        JOptionPane.showMessageDialog(window, "ip或端口号为空！");
                    }else {
                        try {
                            int ports = Integer.parseInt(portClinet);//将端口号转为整形
                            socket = new Socket(ipString,ports);//建立连接
                            link.setText("已连接");//更改button显示信息
                            exit.setText("退出");
                            new SendClient(socket, getName(), 2 + "");//发送该客户端名称至服务器
                            new Thread(new ReceiveClient(socket)).start();//启动接收线程
                        } catch (Exception e2) {
                            JOptionPane.showMessageDialog(window, "连接未成功！可能是ip或端口号格式不对，或服务器未开启。");
                        }
                    }
                }
            }
        });

        //点击按钮发送信息
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });

        //按回车发送信息
        message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    sendMsg();
                }
            }
        });
    }

    //发送信息的方法
    public void sendMsg(){
        String messages = message.getText();//获取文本框内容
        if("".equals(messages)){//判断信息是否为空
            JOptionPane.showMessageDialog(window, "内容不能为空！");
        }
        else if(socket == null || !socket.isConnected()){//判断是否已经连接成功
            JOptionPane.showMessageDialog(window, "未连接成功，不能发送消息！");
        }else {
            try {
                //发送信息
                new SendClient(socket,getName() + "：" + messages,"1");
                message.setText(null);//文本框内容设置为空
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(window, "信息发送失败！");
            }
        }
    }

    //获取客户端名称
    public String getName(){
        return name.getText();
    }

}
