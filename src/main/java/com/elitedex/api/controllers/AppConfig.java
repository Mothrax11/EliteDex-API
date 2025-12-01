package com.elitedex.api.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import reactor.netty.http.client.HttpClient;
import skaro.pokeapi.PokeApiReactorNonCachingConfiguration;

@Configuration
@Import(PokeApiReactorNonCachingConfiguration.class)
public class AppConfig {
    @Bean
    public HttpClient httpClient() {
        return HttpClient.create();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}