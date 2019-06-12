package com.sxs.io.tomcat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * @ClassName: MyResponse
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/20 10:46
 * @Version: 1.0
 */
public class MyResponse {

    private ChannelHandlerContext ctx;

    private HttpRequest r;

    public MyResponse(ChannelHandlerContext ctx, HttpRequest r) {

        this.ctx = ctx;
        this.r = r;
    }

    // 写
    public void write(String out) {
        try {
            FullHttpResponse response = new DefaultFullHttpResponse
                    (HttpVersion.HTTP_1_0, HttpResponseStatus.OK, Unpooled.wrappedBuffer(out.getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE,"text/json");
            response.headers().set(CONTENT_LENGTH,response.content().readableBytes());
            response.headers().set(EXPIRES,0);
            if (HttpHeaders.isKeepAlive(r)){
                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.write(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            ctx.flush();
        }
    }
}
