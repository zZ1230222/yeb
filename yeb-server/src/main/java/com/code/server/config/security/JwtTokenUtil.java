package com.code.server.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据负载生成Token
     * @param claims 负载信息
     * @return
     */
    private String generatorToken(Claims claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generatorExpirationDate())
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();
    }

    /**
     * 生成过期时间
     * @return
     */
    private Date generatorExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从Token中获取负载
     * @param token Token字符串
     * @return
     */
    private Claims getClaimFromToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 判断Token是否过期
     * @param token Token字符串
     * @return
     */
    private boolean isTokenExpired(String token){
        Date expiredDate = getExpiredDateFromToken(token);
        return new Date().before(expiredDate);
    }

    /**
     * 从Token中获取过期时间
     * @param token Token字符串
     * @return
     */
    private Date getExpiredDateFromToken(String token){
        Claims claims = getClaimFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从Token中获取用户名
     * @param token Token字符串
     * @return
     */
    public String getUsernameFromToken(String token){
        String username;
        try {
            Claims claims = getClaimFromToken(token);
            username = claims.getSubject();
        } catch (Exception e){
            username = null;
        }
        return username;
    }

    /**
     * 验证Token是否有效
     * @param token Token字符串
     * @param userDetails 用户信息
     * @return
     */
    private boolean validateToken(String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断Token是否可以被刷新
     * @param token Token字符串
     * @return
     */
    private boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新Token
     * @param token Token字符串
     * @return
     */
    public String refreshToken(String token){
        Claims claims = getClaimFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generatorToken(claims);
    }

}
