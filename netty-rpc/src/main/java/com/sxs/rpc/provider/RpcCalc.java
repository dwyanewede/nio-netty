package com.sxs.rpc.provider;

import com.sxs.rpc.api.IRpcCalc;

public class RpcCalc implements IRpcCalc{

	@Override
	public int add(int a, int b) {
		return a + b;
	}

	@Override
	public int sub(int a, int b) {
		return a - b;
	}

	@Override
	public int mult(int a, int b) {
		return a * b;
	}

	@Override
	public int div(int a, int b) {
		return a / b;
	}

}
