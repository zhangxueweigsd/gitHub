package com.slzs.springcloud_gateway.filter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class HostAddrKeyResolver implements KeyResolver {

	@Override
	public Mono<String> resolve(ServerWebExchange exchange) {
		log.info("进入到ip地址限流中。。。。。。");
		return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
	}
}
