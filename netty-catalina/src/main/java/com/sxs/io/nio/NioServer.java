package com.sxs.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: NioServer
 * @Description: Nio 服务端实现
 * @Author: 尚先生
 * @CreateDate: 2018/11/19 17:43
 * @Version: 1.0
 */
public class NioServer {

    private int port = 8083;
    private InetSocketAddress address = null;
    private Selector selector;
    public NioServer(int port){
        try {
            this.port = port;
            address = new InetSocketAddress(this.port);
            // 打开通道
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(address);
            // 默认为阻塞，手动设置为非阻塞
            server.configureBlocking(false);
            // selector 开始工作
            selector =  Selector.open();
            // option的简称
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器准备就绪，监听的端口是： " + this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioServer(8081).listen();
    }

    private void listen() {
        // 轮询
        try {
            while (true){

                // 有多少人在服务大厅排队
                int wait = this.selector.select();
                if (0 == wait){continue;}
                Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    // 用完一个删除一个
                    process(key);
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void process(SelectionKey key) throws Exception {
        // 分配内存
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if (key.isAcceptable()){
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            serverSocketChannel.configureBlocking(false);
            SocketChannel client = serverSocketChannel.accept();
            client.register(selector,SelectionKey.OP_ACCEPT);
        }else if (key.isReadable()){
            SocketChannel client = (SocketChannel) key.channel();
            int len = client.read(buffer);
            if (len > 0){
                buffer.flip();
                String content = new String(buffer.array(), 0, len);
                System.out.println("read 读到的内容为： " + content);
                client.register(selector,SelectionKey.OP_WRITE);
                buffer.clear();
                client.finishConnect();
            }
        }else if (key.isWritable()){

            SocketChannel client = (SocketChannel) key.channel();
            client.write(buffer.wrap("Hello World".getBytes()));

        }
    }

}
