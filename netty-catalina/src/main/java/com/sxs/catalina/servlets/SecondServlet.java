package com.sxs.catalina.servlets;

import com.alibaba.fastjson.JSON;
import com.sxs.catalina.http.MyRequest;
import com.sxs.catalina.http.MyResponse;
import com.sxs.catalina.http.MyServlet;

public class SecondServlet extends MyServlet {

	@Override
	public void doGet(MyRequest request, MyResponse response) {
		doPost(request, response);
	}
	
	@Override
	public void doPost(MyRequest request, MyResponse response) {
	    String str = JSON.toJSONString(request.getParameters(),true);  
	    response.write(str,200);
	}
	
}
