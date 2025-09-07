package com.unsamo.unsamo_server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String email;
    private String username;
    private String profileImage;
    private String description;
    private String level;
    private Boolean isActive;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static UserResponse from(AppUser u) {
        return new UserResponse(
                u.getUserId(),
                u.getEmail(),
                u.getUsername(),
                u.getProfileImage(),
                u.getDescription(),
                u.getLevel(),
                u.getIsActive(),
                u.getCreatedAt(),
                u.getUpdatedAt()
        );
    }
}
