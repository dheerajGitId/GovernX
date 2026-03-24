package com.clientdata.dashboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.clientdata.schemas.repo")
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.clientdata.schemas",
        "com.clientdata.dashboardservice"
})
public class DashboardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DashboardServiceApplication.class, args);
    }
}
