package com.slzs.springcloud_gateway.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class fallBackController {
	
	@RequestMapping(value="/fallbackcontroller")
	public Map<String, String> fallBackHandler(){
		log.info("进入熔断处理中。。。。");
		Map<String, String> res =new HashMap<>();
	    res.put("code", "-100");
	    res.put("data", "service not available");
	    return res;
	}

}
