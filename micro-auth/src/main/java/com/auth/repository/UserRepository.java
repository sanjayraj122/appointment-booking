package com.auth.repository;


import com.auth.entity.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDtls, Long> {
    Optional<UserDtls> findByUsername(String username);
}

