package com.elitedex.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;

@SpringBootApplication(
    exclude = {JdbcRepositoriesAutoConfiguration.class}
)
public class EliteDex {
    public static void main(String[] args) {
        SpringApplication.run(EliteDex.class, args);
    }
}