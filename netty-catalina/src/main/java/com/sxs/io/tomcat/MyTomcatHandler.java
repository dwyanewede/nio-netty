package com.sxs.io.tomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @ClassName: MyTomcatHandler
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/20 10:04
 * @Version: 1.0
 */
public class MyTomcatHandler extends ChannelInboundHandlerAdapter {

    // 读
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("tomcat 收到请求，开始处理");
        System.out.println(msg.getClass().getTypeName());
        if (msg instanceof HttpRequest){
            HttpRequest r = (HttpRequest) msg;
            MyRequest request = new MyRequest(ctx,r);
            MyResponse response = new MyResponse(ctx,r);
            MyServlet myServlet  = new MyServlet();
            myServlet.doGet(request,response);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
