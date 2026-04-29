package com.clientdata.downloadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.clientdata.schemas.repo")
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.clientdata.schemas",
        "com.clientdata.downloadservice"})
public class DownloadServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DownloadServiceApplication.class, args);
    }
}
