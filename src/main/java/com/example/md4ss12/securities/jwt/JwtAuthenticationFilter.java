package com.example.md4ss12.securities.jwt;

import com.example.md4ss12.securities.custom.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // lấy header Authorization
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // kiểm tra Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtProvider.getEmailFromToken(token);
        }

        // nếu có email và chưa authenticate
        if (email != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            //load user
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            //validate token
            try {
                if (jwtProvider.validateToken(token)) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // authenticate
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authToken);
                }
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }
        }

        // chạy filter tiếp theo
        filterChain.doFilter(request, response);
    }
}
