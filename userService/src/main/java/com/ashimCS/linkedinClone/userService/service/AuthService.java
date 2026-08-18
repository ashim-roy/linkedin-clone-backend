package com.ashimCS.linkedinClone.userService.service;

import com.ashimCS.linkedinClone.userService.dto.LoginRequestDto;
import com.ashimCS.linkedinClone.userService.dto.SignupRequestDto;
import com.ashimCS.linkedinClone.userService.dto.UserDto;
import com.ashimCS.linkedinClone.userService.entity.User;
import com.ashimCS.linkedinClone.userService.exception.BadRequestException;
import com.ashimCS.linkedinClone.userService.repository.UserRepository;
import com.ashimCS.linkedinClone.userService.utils.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto) {
        // each user has one unique email jbcrypt lib to encrypt the pw
        log.info("Signup a user with email: {}", signupRequestDto.getEmail());

        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if (exists) {
            throw new BadRequestException("User with email already exists");
        }

        User user =  modelMapper.map(signupRequestDto, User.class);
        user.setPassword(BCrypt.hashPassword(signupRequestDto.getPassword()));

        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);

    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login request for a user with email: {}", loginRequestDto.getEmail());
        // check if user is there, if yes get that user
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() ->
                        new BadRequestException(
                                "User  not found with email id: {}" + loginRequestDto.getEmail()
                        ));

        // check pw match
        boolean ifPasswordMatch = BCrypt.match(loginRequestDto.getPassword(), user.getPassword());
        if (!ifPasswordMatch) {
            throw new BadRequestException("Incorrect password");
        }
        return jwtService.generateAccessToken(user);
    }
}
