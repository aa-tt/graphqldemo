package com.aa.graphql;


import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aa.dto.User;
import com.aa.service.UserSubscriptionService;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;

@Component
public class UserSubscriptionResolver implements GraphQLSubscriptionResolver {

    private final UserSubscriptionService userSubscriptionService;

    @Autowired
    public UserSubscriptionResolver(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    public Publisher<User> userUpdated(Long id) {
        return userSubscriptionService.getUserUpdates(id);
    }
}
