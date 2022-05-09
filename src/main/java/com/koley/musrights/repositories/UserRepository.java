package com.koley.musrights.repositories;

import com.koley.musrights.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    User findByName(String name);
    User findByEmail(String email);
}