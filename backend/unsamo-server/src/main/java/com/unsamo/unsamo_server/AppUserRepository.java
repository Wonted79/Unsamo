package com.unsamo.unsamo_server;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);     // 서비스단에서 소문자 정규화 후 호출
    Optional<AppUser> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
