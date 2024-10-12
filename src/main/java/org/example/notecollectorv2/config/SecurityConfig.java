package org.example.notecollectorv2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Value("${secure.username}")
    private String username;
    @Value("${secure.password}")
    private String password;
    @Value("${secure.roles}")
    private String roles;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        return http.build();
    };
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()//principle user
                .username(username)
                .password(password)
                .roles(roles)
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
