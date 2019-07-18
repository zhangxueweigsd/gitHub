package com.slzs.springcloud_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@EnableDiscoveryClient// 启用服务注册和发现
@RestController // 提供一个简单的降级页面
@SpringBootApplication
public class SpringcloudGateWayApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringcloudGateWayApplication.class,args);
	}
	
	
	@RequestMapping(value="/fallback")
	public Mono<String>fallback(){
		// Mono是一个Reactive stream，对外输出一个“fallback”字符串。
		return Mono.just("fallback");
	}
}
