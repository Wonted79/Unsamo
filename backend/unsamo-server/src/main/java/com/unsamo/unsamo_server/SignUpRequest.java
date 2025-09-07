package com.unsamo.unsamo_server;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpRequest {

    @Email
    @NotBlank @Size(max = 255)
    private String email;

    @NotBlank @Size(min = 8, max = 72)  // BCrypt 권장 길이
    private String password;

    @Size(min = 2, max = 32)
    private String username;            // 닉네임(선택/유니크)

    private String profileImage;        // 선택
    private String description;         // 선택

    @Size(max = 10)
    private String level;               // 선택
}
