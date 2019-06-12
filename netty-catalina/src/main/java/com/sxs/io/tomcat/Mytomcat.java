package com.sxs.io.tomcat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName: Mytomcat
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/20 9:47
 * @Version: 1.0
 */
public class Mytomcat {

    public void start(int port){

        // NIO
//        ServerSocketChannel channel = ServerSocketChannel.open();
//        InetSocketAddress address = new InetSocketAddress(port);
//        channel.bind(address);
        // BIO
//        ServerSocket serverSocket = new ServerSocket(port);
        // Reactor 主从
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        // netty服务启动
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    // 主线程处理类
                    .channel(NioServerSocketChannel.class)
                    // 子线程处理 Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            // 业务逻辑链路
                            // 编码器
//                            socketChannel.pipeline().addLast(new HttpResponseEncoder());
                            // 解码器
//                            socketChannel.pipeline().addLast(new HttpResponseDecoder());
                            //业务逻辑
                            //第一种 直接实现接口方法
//                            socketChannel.pipeline().addLast(new MyTomcatHandler());
                            // 第二种 实现 simple 中 read0
                            socketChannel.pipeline().addLast(new HttpServerCodec());
                            socketChannel.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
                            socketChannel.pipeline().addLast(new MySimpleHandler());

                        }
                    }).option(ChannelOption.SO_BACKLOG, 128) // 对主线程的配置
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // 对子线程的配置

            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("tomcat 服务已经启动，端口号： " + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后确认流关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new Mytomcat().start(8080);
    }
}
