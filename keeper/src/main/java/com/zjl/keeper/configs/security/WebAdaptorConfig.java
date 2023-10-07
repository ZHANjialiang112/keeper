package com.zjl.keeper.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebAdaptorConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //过滤请求
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll()
                            .requestMatchers("/test/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/", "/*.html", "/*.css", "/*.js", "/*/*.html", "/*/*.css", "/*/*.js", "/*/api-docs/**","/webjars/**","/*.ioc").permitAll()
                            .anyRequest().authenticated();
                })
                // 关闭security 的表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 关闭security 的csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 关闭security 的session
                .sessionManagement(AbstractHttpConfigurer::disable)
                // 跨域解决
                .cors(AbstractHttpConfigurer::disable);

        return http.build();
    }

}