package com.ashimCS.linkedinClone.userService.controller;


import com.ashimCS.linkedinClone.userService.dto.LoginRequestDto;
import com.ashimCS.linkedinClone.userService.dto.SignupRequestDto;
import com.ashimCS.linkedinClone.userService.dto.UserDto;
import com.ashimCS.linkedinClone.userService.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService; // if we make it final then it will be reqd arg cons

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

}
