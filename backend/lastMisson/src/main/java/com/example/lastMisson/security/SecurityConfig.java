package com.example.lastMisson.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final DataSource dataSource;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement(manage -> manage.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                // CSRF
                .csrf((csrf) -> csrf //
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/api/**"))
                ).authorizeHttpRequests(request -> request //
                        .anyRequest().//
                                permitAll()//
                )
                // 콘솔 허용
                .headers((headers) -> headers//
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))//
                )
                // 세션 관리
                .sessionManagement(session -> session //
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
                )
                // 필터 관련
                .build();
    }
}
