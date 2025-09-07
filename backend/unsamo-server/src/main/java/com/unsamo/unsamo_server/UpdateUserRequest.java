package com.unsamo.unsamo_server;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Size(min = 2, max = 32)
    private String username;

    private String profileImage;
    private String description;

    @Size(max = 10)
    private String level;

    private Boolean isActive; // 관리자 화면 등에서 사용할 수 있음
}
