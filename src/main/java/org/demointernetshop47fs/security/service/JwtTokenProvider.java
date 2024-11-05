package org.demointernetshop47fs.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.demointernetshop47fs.service.exception.InvalidJwtException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProvider {

    private String jwtSecret = "984jhgkj0879ghdlskjhdl;f969s976sd8fhdsfh8d69sdfdhfsd";

    private long jwtLifeTime = 60000;

    public String createToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtLifeTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token){

        try {
            Jwts
                    .parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJwt(token);

            return true;
        } catch (SignatureException e) {
            // Invalid JWT signature
            throw new InvalidJwtException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            // Invalid JWT token
            throw new InvalidJwtException("Invalid JWT token");
        } catch (ExpiredJwtException e){
            // Expired JWT token
            //throw new InvalidJwtException("Expired JWT token");
            return false;
        } catch (UnsupportedJwtException e){
            // Unsupported JWT token
            throw new InvalidJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException e){
            // JWT claims is empty
            throw new InvalidJwtException("JWT claims is empty");
        }
    }

    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
