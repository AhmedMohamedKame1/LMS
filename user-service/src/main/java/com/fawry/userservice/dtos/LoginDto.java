package com.fawry.userservice.dtos;

import lombok.Data;

@Data
public class LoginDto {
    private String password;
    private String email;
}
