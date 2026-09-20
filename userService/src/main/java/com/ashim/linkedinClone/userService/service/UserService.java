package com.ashim.linkedinClone.userService.service;

import com.ashim.linkedinClone.userService.dto.UserDto;
import com.ashim.linkedinClone.userService.entity.User;
import com.ashim.linkedinClone.userService.exception.BadRequestException;
import com.ashim.linkedinClone.userService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDto getProfileById(Long userId) {
        log.info("Fetching profile for user ID: {}", userId);


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with id: " + userId));

        return modelMapper.map(user, UserDto.class);
    }
}
