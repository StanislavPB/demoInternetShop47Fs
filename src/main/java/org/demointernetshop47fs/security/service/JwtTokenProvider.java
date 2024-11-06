package org.demointernetshop47fs.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.demointernetshop47fs.service.exception.InvalidJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.lifetime}")
    private long jwtLifeTime;

    public String createToken(String username){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtLifeTime);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {

        try {

            byte[] keyByte = jwtSecret.getBytes(StandardCharsets.UTF_8);
            Key key = new SecretKeySpec(keyByte, SignatureAlgorithm.HS256.getJcaName());

            Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // !!!!!!!!

            return true;
        } catch (JwtException e) {
            throw new InvalidJwtException("Invalid JWT token: " + e.getMessage());
        }
    }
//        } catch (SignatureException e) {
//            // Invalid JWT signature
//            throw new InvalidJwtException("Invalid JWT signature");
//        } catch (MalformedJwtException e) {
//            // Invalid JWT token
//            throw new InvalidJwtException("Invalid JWT token");
//        } catch (ExpiredJwtException e){
//            // Expired JWT token
//            //throw new InvalidJwtException("Expired JWT token");
//            return false;
//        } catch (UnsupportedJwtException e){
//            // Unsupported JWT token
//            throw new InvalidJwtException("Unsupported JWT token");
//        } catch (IllegalArgumentException e){
//            // JWT claims is empty
//            throw new InvalidJwtException("JWT claims is empty");
//        }
//    }

    public String getUserNameFromJwt(String token){
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        log.info("key: {}", key);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        log.info("claims: {}", claims);
        return claims.getSubject();
    }

}
