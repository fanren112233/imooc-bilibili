package com.imooc.bilibili.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.imooc.bilibili.exception.ConditionExppection;
import org.bouncycastle.math.ec.rfc8032.Ed25519;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {
    private static final String ISSUER = "签发者";

    public static String generateToken(Long userid) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 30);
        return JWT.create().withKeyId(String.valueOf(userid)).withIssuer(ISSUER).withExpiresAt(calendar.getTime()).sign(algorithm);
    }
    public static Long verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String userId = jwt.getKeyId();
            return Long.valueOf(userId) ;
        }catch (TokenExpiredException tokenExpiredException){
            throw new ConditionExppection("555","token过期");
        }catch (Exception e){
            throw new ConditionExppection("非法token!");
        }
    }

}
