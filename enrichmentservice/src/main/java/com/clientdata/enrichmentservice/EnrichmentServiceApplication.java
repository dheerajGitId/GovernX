package com.clientdata.enrichmentservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.clientdata.schemas.repo")
@SpringBootApplication

@ComponentScan(basePackages = {
        "com.clientdata.schemas",
        "com.clientdata.enrichmentservice"})
public class EnrichmentServiceApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(EnrichmentServiceApplication.class, args);
    }
}
