package com.slzs.springcloud_gateway.model;

import org.omg.CORBA.ServerRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class HystrixHandler {
	
	public Mono<ServerResponse> hi(ServerRequest request){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject("hi,this is SpringWebFlux"));
	}

}
