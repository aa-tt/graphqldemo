package com.aa.configuration;

import graphql.GraphQL;
import graphql.kickstart.execution.GraphQLInvoker;
import graphql.kickstart.execution.GraphQLObjectMapper;
import graphql.kickstart.execution.subscriptions.GraphQLSubscriptionInvocationInputFactory;
import graphql.kickstart.execution.subscriptions.GraphQLSubscriptionMapper;
import graphql.kickstart.execution.subscriptions.apollo.ApolloSubscriptionConnectionListener;
import graphql.kickstart.execution.subscriptions.apollo.ApolloSubscriptionProtocolFactory;
import graphql.kickstart.execution.subscriptions.apollo.ApolloSubscriptionSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@Component
public class GraphQLWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLWebSocketHandler.class);

    private final GraphQL graphQL;
    private final GraphQLObjectMapper graphQLObjectMapper;
    private final ApolloSubscriptionProtocolFactory subscriptionProtocolFactory;
    private final GraphQLSubscriptionInvocationInputFactory invocationInputFactory;
    private final ConcurrentMap<String, Consumer<String>> consumers = new ConcurrentHashMap<>();

    @Autowired
    public GraphQLWebSocketHandler(GraphQL graphQL, GraphQLObjectMapper graphQLObjectMapper, GraphQLSubscriptionInvocationInputFactory invocationInputFactory, GraphQLInvoker graphQLInvoker) {
        this.graphQL = graphQL;
        this.graphQLObjectMapper = graphQLObjectMapper;
        this.invocationInputFactory = invocationInputFactory;
        this.subscriptionProtocolFactory = new ApolloSubscriptionProtocolFactory(
                graphQLObjectMapper,
                invocationInputFactory,
                graphQLInvoker,
                Collections.singletonList(new ApolloSubscriptionConnectionListener() {
                }),
                Duration.ofSeconds(30) // Optional keep-alive interval
        );
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        GraphQLSubscriptionMapper mapper = new GraphQLSubscriptionMapper(graphQLObjectMapper);
        ApolloSubscriptionSession subscriptionSession = new ApolloSubscriptionSession(mapper);
        Consumer<String> consumer = subscriptionProtocolFactory.createConsumer(subscriptionSession);
        consumers.put(session.getId(), consumer);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Received message: {}", message.getPayload());
        Consumer<String> consumer = consumers.get(session.getId());
        if (consumer != null) {
            consumer.accept(message.getPayload());
        } else {
            logger.error("No consumer found for session: {}", session.getId());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        consumers.remove(session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        consumers.remove(session.getId());
    }
}