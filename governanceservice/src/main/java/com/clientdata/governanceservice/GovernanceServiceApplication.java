package com.clientdata.governanceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableMongoRepositories(basePackages = "com.clientdata.schemas.repo")
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.clientdata.schemas",
        "com.clientdata.governanceservice"})
public class GovernanceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GovernanceServiceApplication.class, args);
    }
}
