package com.example.md4ss12.securities;

import com.example.md4ss12.securities.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .csrf(csrf -> csrf.disable())

                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )

                .authorizeHttpRequests(auth -> {

                    auth

                            // public
                            .requestMatchers(
                                    "/api/auth/**"
                            ).permitAll()

                            // GET product ai cũng xem được
                            .requestMatchers(
                                    HttpMethod.GET,
                                    "/api/products/**"
                            ).permitAll()

                            // POST product
                            .requestMatchers(
                                    HttpMethod.POST,
                                    "/api/products/**"
                            ).hasAnyRole("ADMIN", "STAFF")

                            // PUT product
                            .requestMatchers(
                                    HttpMethod.PUT,
                                    "/api/products/**"
                            ).hasAnyRole("ADMIN", "STAFF")

                            // DELETE product
                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    "/api/products/**"
                            ).hasAnyRole("ADMIN", "STAFF")

                            // còn lại cần login
                            .anyRequest().authenticated();
                })

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

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
