package com.microservice.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private String expireIn;

    @Autowired
    @Qualifier("userService")
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userName, List<String> roles){
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", roles);

        Date today = new Date();
        Date validate = new Date(today.getTime() + Long.parseLong(this.expireIn));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(today)
                .setExpiration(validate)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();

    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserName(String token){
        return Jwts.parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        return bearerToken != null && bearerToken.startsWith("Bearer ") ?
                bearerToken.substring(7, bearerToken.length()):
                null;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
