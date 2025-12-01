package com.elitedex.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.elitedex.api.db.repositories"
)
public class JpaConfig { }