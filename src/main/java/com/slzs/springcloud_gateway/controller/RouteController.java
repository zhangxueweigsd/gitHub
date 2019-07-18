package com.slzs.springcloud_gateway.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.slzs.springcloud_gateway.model.GatewayFilterDefinition;
import com.slzs.springcloud_gateway.model.GatewayPredicateDefinition;
import com.slzs.springcloud_gateway.model.GatewayRouteDefinition;
import com.slzs.springcloud_gateway.service.DynamicRouteServiceImpl;

@RestController
@RequestMapping(value="/route")
public class RouteController {
	@Autowired
    private DynamicRouteServiceImpl dynamicRouteService;
    /**
     * 增加路由
     * @param gwdefinition
     * @return
     */
    @PostMapping("/add")
    public String add(
    		@RequestBody GatewayRouteDefinition gwdefinition) {
        try {
            RouteDefinition definition = assembleRouteDefinition(gwdefinition);
            return this.dynamicRouteService.add(definition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "succss";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        return this.dynamicRouteService.delete(id);
    }
    @PostMapping("/update")
    public String update(@RequestBody GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = assembleRouteDefinition(gwdefinition);
        return this.dynamicRouteService.update(definition);
    }

    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = new RouteDefinition();
        List<PredicateDefinition> pdList=new ArrayList<>();
        definition.setId(gwdefinition.getId());
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList=gwdefinition.getPredicates();
        for (GatewayPredicateDefinition gpDefinition: gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);
      //设置过滤器
        List<FilterDefinition> filters = new ArrayList();
        List<GatewayFilterDefinition> gatewayFilters = gwdefinition.getFilters();
        for(GatewayFilterDefinition filterDefinition : gatewayFilters){
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        }
        definition.setFilters(filters);
        URI uri =null;
        if (gwdefinition.getUri().startsWith("http")) {
			uri=UriComponentsBuilder.fromHttpUrl(gwdefinition.getUri()).build().toUri();
		}else {
			uri=URI.create(gwdefinition.getUri());
		}
        definition.setUri(uri);
        return definition;
    }
  }
