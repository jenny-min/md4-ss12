package com.example.md4ss12.services;

import com.example.md4ss12.enums.Role;
import com.example.md4ss12.models.dto.request.LoginRequestDTO;
import com.example.md4ss12.models.dto.request.RegisterRequestDTO;
import com.example.md4ss12.models.dto.response.ApiResponse;
import com.example.md4ss12.models.dto.response.LoginResponseDTO;
import com.example.md4ss12.models.entity.User;
import com.example.md4ss12.repositories.UserRepository;
import com.example.md4ss12.securities.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //register
    public ApiResponse<String> register(RegisterRequestDTO request){

        // check email tồn tại
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email đã tồn tại");
        }

        // tạo user
        User user = new User();

        user.setEmail(request.getEmail());

        user.setPhone(request.getPhone());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.ROLE_USER);

        // save DB
        userRepository.save(user);

        return new ApiResponse<>(
                true,
                "Đăng ký thành công",
                request.getEmail()
        );
    }

    // login
    public ApiResponse<LoginResponseDTO> login(LoginRequestDTO request) {

        // xác thực
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        // tìm user
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("Không tìm thấy user")
                );

        // tạo token
        String accessToken =
                jwtProvider.generateToken(user);

        // tạo refresh token
        String refreshToken =
                jwtProvider.generateRefreshToken(user);

        // tạo response
        LoginResponseDTO response =
                new LoginResponseDTO(accessToken, refreshToken);

        return new ApiResponse<>(
                true,
                "Đăng nhập thành công",
                response
        );
    }
}
