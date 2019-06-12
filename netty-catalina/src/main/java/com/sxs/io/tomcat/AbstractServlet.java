package com.sxs.io.tomcat;

/**
 * @ClassName: AbstractServlet
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/20 10:44
 * @Version: 1.0
 */
public abstract class AbstractServlet {

    public abstract void doGet(MyRequest request,MyResponse response);
    public abstract void doPost(MyRequest request,MyResponse response);

}
