package com.app.ecom.security.jwt;

import com.app.ecom.security.services.UserDetailsImpl;
import com.app.ecom.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;




@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${spring.app.jwtCookieName}")
    private String jwtCookie;

//    public String getJwtFromHeader(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        logger.debug("Authorization Header : {}", bearerToken);
//        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
//            return bearerToken.substring(7);
//        }
//        return null;
//    }

    public String getJwtFromCookies(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if(cookie != null){
            return cookie.getValue();
        }else{
            return null;
        }
    }

    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }else{
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal){
        String jwt = generateTokenFromUsernname(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie(){
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null)
                .path("/api")
                .build();
        return cookie;
    }

    //getting token from username
    public String generateTokenFromUsernname(String userName){
//        String userName = userDetails.getUsername();
        return Jwts.builder()
                .setSubject(userName)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime() + jwtExpirationMs)))
                .signWith(key())
                .compact();
    }

    //getting username from JWT token
    public String getUsernameFromJWTToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    //generate Signing key
    public Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }
    //Validate JWT token
    public boolean validateJwtToke(String authToken){
        try{
            System.out.println("validate");
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseSignedClaims(authToken);
            return true;
        }catch (MalformedJwtException e){
            logger.error("Invalid JWT token : {}", e.getMessage());
        }catch (ExpiredJwtException exception){
            logger.error("JWT token is expired : {}", exception.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("JWT token is unsupported : {}", e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty : {}", e.getMessage());
        }
        return false;
    }


}
