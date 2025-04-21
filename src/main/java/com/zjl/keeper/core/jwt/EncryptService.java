package com.zjl.keeper.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zjl.keeper.core.context.User;
import com.zjl.keeper.core.context.UserContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author wenman
 */
@Component
@RequiredArgsConstructor
public class EncryptService {

    private final JwtProperties jwtProperty;
    public String createToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperty.getSecret());
        // 使用User 生成对应的负载
        return JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("password", user.getPassword())
                // 过期时间设置3600 秒 设置时区为东八区
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperty.getExpiration()))
                .sign(algorithm);

    }

    public void verifyToken(String token) {
        // 校验Token 已经过期 抛出异常
        Algorithm algorithm = Algorithm.HMAC256(jwtProperty.getSecret());
        DecodedJWT verify = JWT.require(algorithm).build().verify(token);
        Map<String, Claim> claims = verify.getClaims();
        User user = new User(claims.get("username").asString(), claims.get("password").asString());
        // 将用户信息放入到ThreadLocal 中
        UserContextHolder.set(user);
    }


}