package com.zjl.keeper.configs.security;

import com.zjl.keeper.configs.filter.JwtAuthenticationFilter;
import com.zjl.keeper.core.jwt.EncryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author wenman
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebAdaptorConfig {
    private final EncryptService encryptService;

    // 所有白名单请求
    private static final String[] WHITE_LIST = {"/*.html", "/*.css", "/*.js", "/*/*.html", "/*/*.css", "/*/*.js",
            "/*/api-docs/**", "/webjars/**", "/*.ioc", "/login/*", "/login/user-login"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)// 关闭security 的csrf
                //过滤请求
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                // 关闭security 的表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 关闭security 的session

                // 跨域解决
                .cors(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(encryptService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new UserAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}