package com.ashim.linkedinClone.userService.controller;


import com.ashim.linkedinClone.userService.auth.AuthContextHolder;
import com.ashim.linkedinClone.userService.dto.LoginRequestDto;
import com.ashim.linkedinClone.userService.dto.SignupRequestDto;
import com.ashim.linkedinClone.userService.dto.UserDto;
import com.ashim.linkedinClone.userService.service.AuthService;
import com.ashim.linkedinClone.userService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService; // if we make it final then it will be reqd arg cons
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) {
        UserDto userDto = authService.signUp(signupRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/profile")     // http://localhost:8080/api/v1/users/auth/profile
    public ResponseEntity<UserDto> getMyProfile() {
        Long userId = AuthContextHolder.getCurrentUserId();
        UserDto userDto = userService.getProfileById(userId);
        return ResponseEntity.ok(userDto);
    }
}
