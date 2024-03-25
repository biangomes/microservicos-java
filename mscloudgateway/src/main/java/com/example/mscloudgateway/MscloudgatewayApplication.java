package com.example.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class MscloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscloudgatewayApplication.class, args);
	}

	// Objeto que faz o roteamento das rotas diretos para o discovery-server
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.
				routes()
				.route(r -> r.path("/v1/clientes/**").uri("lb://msclientes"))
				.route(r -> r.path("/v1/cartoes/**").uri("lb://mscartoes"))
				.route(r -> r.path("/v1/avaliacoes-credito/**").uri("lb://msavaliadorcredito"))
				.build();
	}
}
