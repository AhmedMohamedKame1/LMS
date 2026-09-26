package com.fawry.userservice.dtos;

import lombok.Data;
import com.fawry.userservice.entities.Role;

@Data
public class RegisterDto {
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private Role role;
}
