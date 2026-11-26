package com.site.customer_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CustomerConfig {

    // RestClient bean pointing at order_service (port 9090).
    // Spring creates it once and injects it into CustomerController.
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:9090")
                .build();
    }
}
