package com.fawry.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fawry.userservice.entities.Role;

@Data
@AllArgsConstructor
public class ViewProfileDto {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
}
