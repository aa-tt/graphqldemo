package com.aa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.GraphQL;
import graphql.kickstart.execution.GraphQLObjectMapper;
import graphql.schema.GraphQLSchema;

@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQL graphQL(GraphQLSchema graphQLSchema) {
        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    @Bean
    public GraphQLObjectMapper graphQLObjectMapper() {
        return GraphQLObjectMapper.newBuilder().build();
    }
}