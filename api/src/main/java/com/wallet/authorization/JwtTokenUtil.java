package com.wallet.authorization;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import com.wallet.exception.WalletException;
import com.wallet.model.User;
import com.wallet.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ratpack.http.Status;


public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // private String secret;
    private static byte[] secret = Base64.getDecoder().decode("eiT8zVGznc1hxBmvzSMjd+2qUM72Z3nw6TwgoU+WaEQ=");

    //retrieve username from jwt token
    public static String getUidFromToken(String token) throws WalletException {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public static Date getExpirationDateFromToken(String token) throws WalletException {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws WalletException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private static Claims getAllClaimsFromToken(String token) throws WalletException {
        try {
            return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret)).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new WalletException(Status.UNAUTHORIZED, Constants.INVALID_TOKEN);
        }
    }

    //check if the token has expired
    private static Boolean isTokenExpired(String token) throws WalletException {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getUid());
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();
    }

    //validate token
    public static Boolean validateToken(String token, User user) throws WalletException {
        final String uid = getUidFromToken(token);
        return (uid.equals(user.getUid()) && !isTokenExpired(token));
    }
}

