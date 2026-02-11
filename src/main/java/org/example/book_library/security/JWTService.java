package org.example.book_library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.book_library.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRET_KEY = "JWTServiceVerySecretKeyForLibraryTrainingProjectInBig2026";

    public String generateJWTToken(UserDetails user) {
        return generateJWTToken(new HashMap<>(),user );
    }

    public String generateJWTToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000* 60 * 60 * 24))
                .signWith(getSecretKey())
                .compact();
    }

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        //Keys is a Fabric-class, he helped to create keys for JWT
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
    //Resolver - распознаватель
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);//apply - применять
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails user){
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) &&  !isTokenExpired(token));
    }

}
