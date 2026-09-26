package com.fawry.userservice.services;

import com.fawry.userservice.dtos.ViewProfileDto;
import com.fawry.userservice.entities.User;
import com.fawry.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ViewProfileDto getProfile(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return new ViewProfileDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );

    }
}
