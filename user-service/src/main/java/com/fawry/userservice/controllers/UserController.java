package com.fawry.userservice.controllers;

import com.fawry.userservice.Utils.JwtUtil;
import com.fawry.userservice.dtos.ViewProfileDto;
import com.fawry.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/me")
    public ResponseEntity<ViewProfileDto> getMyProfile(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtUtil.extractUserId(token);

        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewProfileDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }

}
