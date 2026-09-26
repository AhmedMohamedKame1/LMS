package com.fawry.userservice.dtos;

import com.fawry.userservice.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private Role role;
}
