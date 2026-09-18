package com.ashim.linkedinClone.ConnectionsService.controller;

import com.ashim.linkedinClone.ConnectionsService.auth.AuthContextHolder;
import com.ashim.linkedinClone.ConnectionsService.entity.Person;
import com.ashim.linkedinClone.ConnectionsService.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionsService connectionsService;

//    @GetMapping("/{userId}/first-degree")
//    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable Long userId) {
//       // log.info("User id is {}", userIdFromHeader);
//        List<Person> personList = connectionsService.getFirstDegreeConnections(userId);
//        return ResponseEntity.ok(personList);
//    }

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections() {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Fetching 1st degree connections for user id from header: {}", userId);
        List<Person> personList = connectionsService.getFirstDegreeConnections(userId);
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/second-degree")
    public ResponseEntity<List<Person>> getSecondDegreeConnections() {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Fetching 2nd degree connections for user id from header: {}", userId);
        List<Person> personList = connectionsService.getSecondDegreeConnections(userId);
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/third-degree")
    public ResponseEntity<List<Person>> getThirdDegreeConnections() {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Fetching 3rd degree connections for user id from header: {}", userId);
        List<Person> personList = connectionsService.getThirdDegreeConnections(userId);
        return ResponseEntity.ok(personList);
    }



}

