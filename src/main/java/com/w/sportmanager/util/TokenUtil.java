package com.w.sportmanager.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.w.sportmanager.pojo.Student;

import java.util.Date;

public class TokenUtil {
    // 24小时过期
    private static final long EXPIRE_TIME= 24*60*60*1000;
    private static final String TOKEN_SECRET="oUnigcBBqwtEkfos6MUZ";  //密钥盐

    /**
     * 签名生成
     * @param person
     * @return
     */
    public static String sign(Student person){
        System.out.println(person);
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("id", person.getId())
                    .withClaim("name", person.getName())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 签名验证
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            UserContextHolder.setCurrentUserId(jwt.getClaim("id").asString());
            System.out.println("id: " + jwt.getClaim("id").asString());
            System.out.println("token过期时间: " + jwt.getExpiresAt());
            return true;
        } catch (Exception e) {
            UserContextHolder.clearCurrentUserId();
            return false;
        }
    }
}
