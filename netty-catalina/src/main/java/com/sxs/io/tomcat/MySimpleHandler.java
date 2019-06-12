package com.sxs.io.tomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @ClassName: MyTomcatHandler
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/20 10:04
 * @Version: 1.0
 */
public class MySimpleHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        System.out.println("tomcat 收到请求，开始处理");
        System.out.println(fullHttpRequest.getClass().getTypeName());
        if (fullHttpRequest instanceof HttpRequest) {
            HttpRequest r = (HttpRequest) fullHttpRequest;
            MyRequest request = new MyRequest(ctx, r);
            MyResponse response = new MyResponse(ctx, r);
            MyServlet myServlet = new MyServlet();
            myServlet.doGet(request, response);
        }
    }
}

