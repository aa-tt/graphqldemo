package com.aa.graphql;

import com.aa.dto.User;
import com.aa.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private UserService userService;

    public User updateUser(Long id, UserInput input) {
        User updates = new User();
        updates.setName(input.getName());
        updates.setEmail(input.getEmail());
        return userService.updateUser(id, updates);
    }

    public User user(Long id) {
        return userService.getUserById(id);
    }
}
