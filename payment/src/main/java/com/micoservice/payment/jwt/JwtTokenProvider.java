package com.micoservice.payment.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;


    @PostConstruct
    protected void init(){
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes(StandardCharsets.UTF_8));
    }


    public Authentication getAuthentication(String token){
        UserDetails userDetails = new UserDetails() {
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }
            public String getPassword() {
                return "";
            }
            public String getUsername() {
                return "";
            }
            public boolean isAccountNonExpired() {
                return true;
            }
            public boolean isAccountNonLocked() {
                return true;
            }
            public boolean isCredentialsNonExpired() {
                return true;
            }
            public boolean isEnabled() {
                return true;
            }
        };

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
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
