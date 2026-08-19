package com.ashim.linkedinClone.ConnectionsService.service;

import com.ashim.linkedinClone.ConnectionsService.entity.Person;
import com.ashim.linkedinClone.ConnectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnections(Long userId) {
        log.info("Getting first degree connections for user {}", userId);

        return personRepository.getFirstDegreeConnections(userId);
    }
}
