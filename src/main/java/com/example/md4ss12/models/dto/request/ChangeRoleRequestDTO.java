package com.example.md4ss12.models.dto.request;

import com.example.md4ss12.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleRequestDTO {
    private Role role;
}
