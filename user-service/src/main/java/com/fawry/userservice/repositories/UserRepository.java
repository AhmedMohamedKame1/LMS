package com.fawry.userservice.repositories;

import com.fawry.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    public boolean existsByEmail(String email);
}
