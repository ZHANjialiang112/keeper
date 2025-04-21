package com.zjl.keeper.configs.filter;

import com.zjl.keeper.core.jwt.EncryptService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author wenman
 */

@Order(1)
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final EncryptService encryptService;

    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (verityToken(token)) {
            encryptService.verifyToken(getToken(token));
        } else {
            throw new RuntimeException("token is invalid");
        }
        filterChain.doFilter(request, response);
    }

    public boolean verityToken(String token) {
        return StringUtils.isNotBlank(token) && token.startsWith(TOKEN_PREFIX) && !token.replace(TOKEN_PREFIX, "").isEmpty();
    }

    public String getToken(String token) {
        return token.replace(TOKEN_PREFIX, "");
    }
}
