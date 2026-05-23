package com.example.md4ss12.controllers;

import com.example.md4ss12.models.dto.request.ChangeRoleRequestDTO;
import com.example.md4ss12.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    //ADMIN cập nhật quyền cho user khác
    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeRole(@PathVariable Long id, @RequestBody ChangeRoleRequestDTO request) {
        return ResponseEntity.ok(userService.changeRole(id, request.getRole()));
    }

    //Lấy thông tin người dùng đang đăng nhập
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
