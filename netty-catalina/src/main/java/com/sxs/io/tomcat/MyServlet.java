package com.sxs.io.tomcat;

/**
 * @ClassName: MyServlet
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/20 10:47
 * @Version: 1.0
 */
public class MyServlet extends AbstractServlet {

    @Override
    public void doGet(MyRequest request, MyResponse response) {
//        doPost(request, response);
        System.out.println("tomcat 请求处理结束，得到响应结果");
        response.write(request.getParamter("name"));
    }

    @Override
    public void doPost(MyRequest request, MyResponse response) {

    }
}
