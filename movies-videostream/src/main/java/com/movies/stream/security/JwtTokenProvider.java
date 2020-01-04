package com.movies.stream.security;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMS;


    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
    
    public UserDetails getUserDetails(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
                .getBody();
        
        List<String> roles = claims.get("authorities", ArrayList.class);

        List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
			return new SimpleGrantedAuthority(r);
		}).collect(Collectors.toList());
        
        UserPrincipal user = new UserPrincipal(claims.get("id", Integer.class).longValue(),
        		claims.get("firstName",  String.class),
        		claims.get("lastName",  String.class),
        		claims.get("username",  String.class),
        		claims.get("email",  String.class),
        		claims.get("password",  String.class),
        		grantedAuthorities);      
        

        return user;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid jwt signature!");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid jwt token!");
        } catch (ExpiredJwtException ex) {
            logger.error("Unsupported jwt token!");
        } catch (IllegalArgumentException ex) {
            logger.error("Jwt claims string is empty!");
        }
        return false;
    }
}
