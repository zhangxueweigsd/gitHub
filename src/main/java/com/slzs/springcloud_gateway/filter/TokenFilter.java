package com.slzs.springcloud_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TokenFilter implements GlobalFilter,Ordered {
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("进入到token检验中。。");
		String token=exchange.getRequest().getQueryParams().getFirst("token");
		if (token==null || token.isEmpty()) {
			log.info("必填参数token没有。。。。。");
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -100;
	}

	
}
