package com.clientdata.dashboardservice.service;

import com.clientdata.schemas.model.PolicyDocument;
import com.clientdata.schemas.model.PolicyNameCount;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class DashboardStatsService {
    private final MongoTemplate mongoTemplate;

    public int totalNumberOfPolicyDocuments(){

        return (int) mongoTemplate.count(new Query(), PolicyDocument.class);
    }

    public List<PolicyNameCount> getPolicyNameCounts() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("policyName").count().as("count"),
                Aggregation.project("count")
                        .and("_id").as("policyName")
        );

        return mongoTemplate
                .aggregate(aggregation, PolicyDocument.class, PolicyNameCount.class)
                .getMappedResults();
    }
}
