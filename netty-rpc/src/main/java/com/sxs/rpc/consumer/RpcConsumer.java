package com.sxs.rpc.consumer;

import com.sxs.rpc.api.IRpcCalc;
import com.sxs.rpc.api.IRpcHello;
import com.sxs.rpc.consumer.proxy.RpcProxy;

public class RpcConsumer {
	
	public static void main(String[] args) {
		
		//本机一个人在玩
		//自娱自乐
		//肯定是用动态代理来实现的,传给它一个接口，返回一个实例，伪代理
		IRpcHello rpcHello = RpcProxy.create(IRpcHello.class);
		String r = rpcHello.hello("sxs");
		System.out.println(r);
		
		
		int a = 8,b = 2;
		IRpcCalc calc = RpcProxy.create(IRpcCalc.class);
		System.out.println(a + " + " + b +" = " + calc.add(a, b));
		System.out.println(a + " - " + b +" = " + calc.sub(a, b));
		System.out.println(a + " * " + b +" = " + calc.mult(a, b));
		System.out.println(a + " / " + b +" = " + calc.div(a, b));


	}
	
}
