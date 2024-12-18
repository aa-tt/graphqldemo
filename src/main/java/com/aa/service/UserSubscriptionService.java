package com.aa.service;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import com.aa.dto.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class UserSubscriptionService {

    private final Sinks.Many<User> sink;

    public UserSubscriptionService() {
        // Use a multicast sink to publish User updates
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Publisher<User> getUserUpdates(Long id) {
        // Filter updates for a specific user ID
        return sink.asFlux()
                   .filter(user -> user.getId().equals(id));
    }

    public void publishUserUpdate(User user) {
        // Publish a user update
        sink.tryEmitNext(user);
    }
}

