package com.tpt.online_food_order.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        if (jwt != null) {
            String token = jwt.substring(7);
            System.out.println("Token: " + token);
            try {
                // GET SIGNING KEY TO PARSE
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());
                Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
                String email = String.valueOf(claims.get("email"));
                List<String> roles = claims.get("authorities", List.class); // Lấy thẳng List<String>
                List<GrantedAuthority> authorityList = roles.stream()
                        .map(SimpleGrantedAuthority::new) // Chuyển thành GrantedAuthority
                        .collect(Collectors.toList());
                // CREATE AN AUTHENTICATION
                Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authorityList);
                // SAVE AUTHENTICATION TO SECURITY CONTEXT
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
