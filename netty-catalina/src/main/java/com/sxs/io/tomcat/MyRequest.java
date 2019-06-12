package com.sxs.io.tomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: MyRequest
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/20 10:46
 * @Version: 1.0
 */
public class MyRequest {

    private ChannelHandlerContext ctx;

    private HttpRequest r;

    public MyRequest(ChannelHandlerContext ctx, HttpRequest r) {

        this.ctx = ctx;
        this.r = r;
    }

    public String getUri(){
        return r.getUri();
    }
    public String getMethod(){
        return r.getMethod().name();
    }

    public Map<String, List<String>> getParamters(){
        QueryStringDecoder decoder = new QueryStringDecoder(r.getUri());
        return decoder.parameters();
    }

    public String getParamter(String name){
        Map<String, List<String>> paramters = getParamters();
        List<String> param = paramters.get(name);
        if (null == param){
            return null;
        }else {
            return param.get(0);
        }
    }

}

