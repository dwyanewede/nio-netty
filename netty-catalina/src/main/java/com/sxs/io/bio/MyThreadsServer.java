package com.sxs.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: MyClient
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/19 19:23
 * @Version: 1.0
 */
public class MyThreadsServer {

    private ServerSocket socket;
    private static int port = 8080;

    public static void main(String[] args) throws IOException{
        new MyThreadsServer().initMethod(port);
    }

    private void initMethod(int port) throws IOException {
        System.out.println("服务端启动...端口号为： " + port);
        ServerSocket socket = new ServerSocket(port);
       while (true){
           Socket accept = socket.accept();
           new HandlerThread(accept);
       }
    }


    private class HandlerThread implements Runnable {
        private Socket socket;
        public HandlerThread(Socket client) {
            socket = client;
            new Thread(this).start();
        }

        public void run() {
            try {
                // 读取客户端数据
                System.out.println("当前线程名称： " + Thread.currentThread().getName());
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String clientInputStr = input.readLine();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                // 处理客户端数据
                System.out.println("客户端发过来的内容:" + clientInputStr);

                // 向客户端回复信息
                PrintStream out = new PrintStream(socket.getOutputStream());
                System.out.print("请输入:\t");
                // 发送键盘输入的一行
                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.println(s);

                out.close();
                input.close();
            } catch (Exception e) {
                System.out.println("服务器 run 异常: " + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("服务端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
    }
}