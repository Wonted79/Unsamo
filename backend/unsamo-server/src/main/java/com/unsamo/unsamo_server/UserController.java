package com.unsamo.unsamo_server;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpRequest req) {
        return ResponseEntity.ok(userService.signUp(req));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(userService.login(req));
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    // 업데이트(프로필 수정)
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UpdateUserRequest req) {
        return ResponseEntity.ok(userService.update(id, req));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

