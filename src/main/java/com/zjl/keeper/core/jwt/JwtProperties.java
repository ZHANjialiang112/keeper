package com.zjl.keeper.core.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wenman
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secret;
    private Long expiration;

    public String getSecret() {
        return Optional.ofNullable(secret).orElseThrow(NullPointerException::new);
    }

    public Long getExpiration() {
        return Optional.ofNullable(expiration).orElseThrow(NullPointerException::new);
    }
}
