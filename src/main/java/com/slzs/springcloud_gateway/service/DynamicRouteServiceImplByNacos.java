package com.slzs.springcloud_gateway.service;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
/**
 * 监听nacos config server下发配置
 * @author 南天
 *
 */
@Slf4j
@Component
public class DynamicRouteServiceImplByNacos{
	
	@Autowired
	private DynamicRouteServiceImpl dynamicRouteServiceImpl;
	
	public DynamicRouteServiceImplByNacos() {
		dynamicRouteByNacosListener("gateway_config_json","refresh_config_json");
	}
	/**
	 * 监听nacos server 下发的动态路由配置
	 */
	public void dynamicRouteByNacosListener(String dataId,String group) {
		try {
			ConfigService configService=NacosFactory.createConfigService("127.0.0.1:8848");
			String content=configService.getConfig(dataId,group,5000);
			log.info("$$$$$从nacos监听到的内容："+content);
			configService.addListener(dataId, group, new Listener() {
				@Override
				public void receiveConfigInfo(String configInfo) {
					JSONObject json = JSONObject.parseObject(configInfo);
				    JSONArray array = json.getJSONArray("items");
				    for (int i = 0; i < array.size(); i++) {
				    	RouteDefinition definition=JSON.parseObject(array.getString(i),RouteDefinition.class);
						dynamicRouteServiceImpl.update(definition);
					}
				}
				@Override
				public Executor getExecutor() {
					return null;
				}
			});
		
		
		} catch (Exception e) {
			log.error("监听nacos下发路由配置解析错误！！！！", e);
		}
	}
}
