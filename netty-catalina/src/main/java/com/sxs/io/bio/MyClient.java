package com.sxs.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @ClassName: MyClient
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/19 19:23
 * @Version: 1.0
 */
public class MyClient {

    private static String host = "localhost";
    private static int port = 8080;
    private Socket socket;


    public MyClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        System.out.println("客户端启动...端口号为： " + port);
    }

    public void listen() throws IOException, InterruptedException {
        while (true){
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //向服务器端发送数据
                PrintStream out = new PrintStream(socket.getOutputStream());
                System.out.print("请输入: \t");
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.println(str);

                String ret = input.readLine();
                System.out.println("服务器端返回过来的是: " + ret);
                // 如接收到 "OK" 则断开连接
                if ("OK".equals(ret)) {
                    System.out.println("客户端将关闭连接");
                    Thread.sleep(500);
                    out.close();
                    input.close();
                }
//            out.close();
//            input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new MyClient(host, port).listen();
    }

}
