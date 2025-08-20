package com.mokiu.common.utils;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

// 这个 JwtUtil 类是一个基于 JWT（JSON Web Token） 的工具类，主要用于 生成、解析和验证 JWT 令牌，适用于用户认证和授权场景（如登录、API 鉴权）
@Component
public class JwtUtil {
    // 有效期
    private static final long JWT_EXPIRE = 30*60*1000L;  // 半小时(ms)
    // 令牌密钥
    private static final String JWT_KEY = "123456";

    // 创建jwt
    public String createToken(Object data){
        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 过期时间
        long expTime = currentTime + JWT_EXPIRE;
        // 构建jwt
        JwtBuilder builder = Jwts.builder()
                .setId((UUID.randomUUID() + ""))
                .setSubject(JSON.toJSONString(data))
                .setIssuer("system")
                .setIssuedAt(new Date(currentTime))
                .signWith(SignatureAlgorithm.HS256, encodeSecret(JWT_KEY)) // 对称加密,密钥加密处理
                .setExpiration(new Date(expTime));
        return builder.compact();
    }

    // Base64的转码，符合signWith的参数要求
    private SecretKey encodeSecret(String key){
        byte[] encode = Base64.getEncoder().encode(key.getBytes());
        SecretKeySpec aes = new SecretKeySpec(encode, 0, encode.length, "AES");
        return aes;
    }

    // 解析jwt
    public Claims parseToken(String token){
        Claims body = Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

    // 实现数据转换，得到用户对象
    public <T> T parseToken(String token, Class<T> tClass){
        Claims body = Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
        return JSON.parseObject(body.getSubject(), tClass);
    }
}
