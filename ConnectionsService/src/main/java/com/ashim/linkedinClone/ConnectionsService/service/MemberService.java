package com.ashim.linkedinClone.ConnectionsService.service;

import com.ashim.linkedinClone.ConnectionsService.entity.Person;
import com.ashim.linkedinClone.ConnectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final PersonRepository personRepository;

    public void createMember(Long userId, String name){
        Person person = Person.builder()
                .name(name)
                .userId(userId)
                .build();

        personRepository.save(person);
    }

}
