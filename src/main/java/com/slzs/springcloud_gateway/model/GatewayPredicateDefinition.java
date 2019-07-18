package com.slzs.springcloud_gateway.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由断言定义模型
 * @author 南天
 *
 */
public class GatewayPredicateDefinition {

	//断言的名称
	private String name;
	
	//断言的规则
	private Map<String, String>args=new LinkedHashMap<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getArgs() {
		return args;
	}
	public void setArgs(Map<String, String> args) {
		this.args = args;
	}
}
