package com.aa.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()  // Allow access to H2 console
            .antMatchers("/actuator/**").permitAll()  // Allow access to actuator
            .antMatchers("/graphql").permitAll()  // Allow access to GraphQL endpoint
            .antMatchers("/subscriptions").permitAll()  // Allow access to GraphQL subscriptions defined by WebSocketConfig
            .anyRequest().authenticated()
            .and()
            .csrf().disable()  // Disable CSRF protection for H2 console
            .headers().frameOptions().disable();  // Disable frame options for H2 console
    }
}
