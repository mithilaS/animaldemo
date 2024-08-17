package com.mithi.animaldemo;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@Deployment(resources = "classpath*:/model/animal-picture-bpmn.bpmn")
@EnableMongoRepositories
public class StartFormProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartFormProcessApplication.class, args);
    }
}
