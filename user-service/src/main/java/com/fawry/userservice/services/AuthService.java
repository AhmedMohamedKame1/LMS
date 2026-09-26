package com.fawry.userservice.services;

import com.fawry.userservice.Utils.JwtUtil;
import com.fawry.userservice.dtos.AuthResponseDto;
import com.fawry.userservice.dtos.LoginDto;
import com.fawry.userservice.dtos.RegisterDto;
import com.fawry.userservice.entities.User;
import com.fawry.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthResponseDto register(RegisterDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new RuntimeException("password do not match");
        }
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        user.setRole(dto.getRole());

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDto(token, user.getRole());
    }

    public AuthResponseDto login(LoginDto dto){
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if(!BCrypt.checkpw(dto.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDto(token, user.getRole());
    }
}
