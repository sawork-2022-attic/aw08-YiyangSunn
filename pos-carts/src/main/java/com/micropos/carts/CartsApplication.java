package com.micropos.carts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class CartsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartsApplication.class, args);
    }

    @Bean
    @LoadBalanced
    // 注入一个使用负载均衡的 RestTemplate 实例，用于从 pos-products 服务获取商品数据
    // 默认的负载均衡策略是 RoundRobin（默认的负载均衡器为 Spring Cloud 的 LoadBalancer）
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
