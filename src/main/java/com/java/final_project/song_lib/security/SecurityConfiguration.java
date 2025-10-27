package com.java.final_project.song_lib.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/songs/create", "/songs/edit/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/songs/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/songs/**").hasAuthority("ADMIN")
                        .requestMatchers("/songs", "/songs/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/authors/create", "/authors/edit/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/authors/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/authors/**").hasAuthority("ADMIN")
                        .requestMatchers("/authors", "/authors/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/genres/create", "/genres/edit/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/genres/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/genres/**").hasAuthority("ADMIN")
                        .requestMatchers("/genres", "/genres/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/**").permitAll())
                .formLogin(form -> form
                        .permitAll())
                .logout(logout -> logout
                        .permitAll());
        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    DatabaseUserDetailService userDetailsService() {
        return new DatabaseUserDetailService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}