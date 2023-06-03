//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.cubingtr.cubingtrapi.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final String AUTHENTICATED_USER_TOKEN = "token";
    public static final String WCA_ID = "wca_id";
    public static final String WCA_PK = "wca_pk";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.validity.duration.inhours}")
    private float getJwtTokenValidityInHours;

    public JwtTokenUtil() {
    }

    public String getUsernameFromToken(String token) {
        return (String)this.getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return (Date)this.getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return (Claims)Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap();
        return this.generateToken(claims, userDetails.getUsername());
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + (long)(this.getJwtTokenValidityInHours * 60.0F * 60.0F * 1000.0F))).signWith(SignatureAlgorithm.HS512, this.secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = this.getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }
}
