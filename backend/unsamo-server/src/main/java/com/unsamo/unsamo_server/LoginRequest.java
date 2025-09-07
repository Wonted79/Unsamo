package com.unsamo.unsamo_server;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoginRequest {
    @Email @NotBlank @Size(max = 255)
    private String email;

    @NotBlank
    private String password;
}
