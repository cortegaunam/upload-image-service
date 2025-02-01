package com.dgtic.unam.register_image_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}") String mongoUrl;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoUrl));
    }
}
