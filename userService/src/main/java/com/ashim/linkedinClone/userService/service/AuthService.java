package com.ashim.linkedinClone.userService.service;

import com.ashim.linkedinClone.userService.dto.LoginRequestDto;
import com.ashim.linkedinClone.userService.dto.SignupRequestDto;
import com.ashim.linkedinClone.userService.dto.UserDto;
import com.ashim.linkedinClone.userService.entity.User;
import com.ashim.linkedinClone.userService.event.UserCreatedEvent;
import com.ashim.linkedinClone.userService.exception.BadRequestException;
import com.ashim.linkedinClone.userService.repository.UserRepository;
import com.ashim.linkedinClone.userService.utils.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final KafkaTemplate<Long, UserCreatedEvent> userCreatedEventKafkaTemplate;

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

        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();

        userCreatedEventKafkaTemplate.send("user_created_topic", userCreatedEvent);

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
