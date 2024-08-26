package com.dwi.users.shvb.backend_users_shvb.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dwi.users.shvb.backend_users_shvb.config.security.filter.JwtAutheticationFilter;
import com.dwi.users.shvb.backend_users_shvb.config.security.filter.JwtValidationfilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http.csrf(csrfConfig -> csrfConfig.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilter(new JwtAutheticationFilter(authenticationManager()))
            .addFilter(new JwtValidationfilter(authenticationManager()))
            .authorizeHttpRequests(authConfig -> {
                authConfig.requestMatchers(HttpMethod.GET, "/users").permitAll();
                authConfig.requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("USER", "ADMIN");
                authConfig.requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN");
                authConfig.requestMatchers("/users/**").hasRole("ADMIN");
                authConfig.anyRequest().authenticated();
            }).build();
    }

}
