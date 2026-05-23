package com.example.md4ss12.services;

import com.example.md4ss12.enums.Role;
import com.example.md4ss12.models.entity.User;

public interface UserService {
    // thay đổi role người dùng
    User changeRole(Long id, Role role);

    //lấy thông tin user hiện tại
    User getCurrentUser();
}
