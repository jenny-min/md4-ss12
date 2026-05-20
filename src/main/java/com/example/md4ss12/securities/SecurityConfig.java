package com.example.md4ss12.securities;

import com.example.md4ss12.securities.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    //Bean: đăng ký object vào Spring Container; để Spring quản lý và inject

    @Bean
    public PasswordEncoder passwordEncoder() {
        //mã hóa password khi register
        //so sánh password khi login
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        //xử lý authenticate
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        //Nơi cấu hình toàn bộ security
        http
                ////Tắt cơ chế bảo vệ CSRF của Spring Security
                .csrf(csrf -> csrf.disable())

                //Cho phép frontend gọi API backend
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                //Cho phép truy cập không xác thực,k yêu cầu AuthZ, AuthN - phân quyền
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/homepage", "/api/auth/register","/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            var config = new CorsConfiguration();
            config.addAllowedOrigin("*");
            config.addAllowedMethod("*");
            config.addAllowedHeader("*");
            return config;
        };
    }
}
