package com.task.backend.repository;

import com.task.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByName(String username);

    boolean existsByEmail(String email);

    Optional<User> findByName(String username);

}
