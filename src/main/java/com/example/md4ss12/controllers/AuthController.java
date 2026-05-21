package com.example.md4ss12.controllers;

import com.example.md4ss12.models.dto.request.LoginRequestDTO;
import com.example.md4ss12.models.dto.request.RegisterRequestDTO;
import com.example.md4ss12.models.dto.response.ApiResponse;
import com.example.md4ss12.models.dto.response.LoginResponseDTO;
import com.example.md4ss12.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private AuthService authService;

    //api/auth/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    //api/auth/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
