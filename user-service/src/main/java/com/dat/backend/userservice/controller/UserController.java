package com.dat.backend.userservice.controller;

import com.dat.backend.userservice.dto.CreateUserDto;
import com.dat.backend.userservice.dto.LoginDto;
import com.dat.backend.userservice.dto.UserDto;
import com.dat.backend.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //test cd

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(userService.create(createUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) {
        // Implement login logic here
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUser() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
