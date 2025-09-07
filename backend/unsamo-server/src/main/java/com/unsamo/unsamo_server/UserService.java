package com.unsamo.unsamo_server;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signUp(SignUpRequest req) {
        String email = req.getEmail().trim().toLowerCase();

        if (repo.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (req.getUsername() != null && !req.getUsername().isBlank()) {
            String username = req.getUsername().trim();
            if (repo.existsByUsername(username)) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }
        }

        AppUser u = new AppUser();
        u.setEmail(email);
        u.setUsername(req.getUsername() != null ? req.getUsername().trim() : null);
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u.setProfileImage(req.getProfileImage());
        u.setDescription(req.getDescription());
        u.setLevel(req.getLevel());
        u.setIsActive(true);

        AppUser saved = repo.save(u);
        return UserResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse login(LoginRequest req) {
        String email = req.getEmail().trim().toLowerCase();
        AppUser u = repo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!Boolean.TRUE.equals(u.getIsActive())) {
            throw new IllegalArgumentException("비활성화된 계정입니다.");
        }
        if (!passwordEncoder.matches(req.getPassword(), u.getPasswordHash())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        // 실제 운영에서는 여기서 JWT 발급/세션 처리
        return UserResponse.from(u);
    }

    @Transactional(readOnly = true)
    public UserResponse get(Long id) {
        AppUser u = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return UserResponse.from(u);
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest req) {
        AppUser u = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (req.getUsername() != null) {
            String newName = req.getUsername().trim();
            if (!newName.equals(u.getUsername()) && repo.existsByUsername(newName)) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }
            u.setUsername(newName);
        }
        if (req.getProfileImage() != null) u.setProfileImage(req.getProfileImage());
        if (req.getDescription() != null)  u.setDescription(req.getDescription());
        if (req.getLevel() != null)        u.setLevel(req.getLevel());
        if (req.getIsActive() != null)     u.setIsActive(req.getIsActive());

        return UserResponse.from(u); // 영속 상태 → 변경감지로 업데이트
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        repo.deleteById(id);
    }
}

