package com.slzs.springcloud_gateway.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
	
	@Bean(name= {"redisTemplate","stringRedisTemplate"})
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate stringRedisTemplate=new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(factory);
		return stringRedisTemplate;
	}

}
