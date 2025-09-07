package com.unsamo.unsamo_server;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(
        name = "app_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_app_user_email", columnNames = "email"),
                @UniqueConstraint(name = "ux_app_user_username", columnNames = "username")
        }
)
@Getter @Setter @NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGSERIAL과 호환
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", length = 32, unique = true)
    private String username; // 닉네임(표시용)

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;    // 로그인 ID (소문자 정규화)

    @Column(name = "password_hash", nullable = false)
    private String passwordHash; // BCrypt 해시

    @Column(name = "profile_image", columnDefinition = "text")
    private String profileImage;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "level", length = 10)
    private String level;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        createdAt = now;
        updatedAt = now;
        if (email != null) email = email.trim().toLowerCase();
        if (username != null) username = username.trim();
        if (isActive == null) isActive = true;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
        if (email != null) email = email.trim().toLowerCase();
        if (username != null) username = username.trim();
    }
}
