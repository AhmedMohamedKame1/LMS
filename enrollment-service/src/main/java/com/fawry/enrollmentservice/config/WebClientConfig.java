package com.fawry.enrollmentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${course-service.uri}")
    private String courseServiceUrl;

    @Bean
    public WebClient courseServiceWebClient(){
        return WebClient.builder()
                .baseUrl(courseServiceUrl)
                .build();
    }
}
