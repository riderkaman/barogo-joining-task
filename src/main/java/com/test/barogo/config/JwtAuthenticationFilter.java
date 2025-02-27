package com.test.barogo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String jwtSecret;

    public JwtAuthenticationFilter(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isPassRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJwtFromRequest(request);
        if (token != null && validateToken(token)) {
            Claims claims = parseToken(token);

            Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid or missing token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPassRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.equals("/api/v1/member") || requestURI.equals("/api/v1/member/login");
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // "Bearer "를 제거한 토큰 반환
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
