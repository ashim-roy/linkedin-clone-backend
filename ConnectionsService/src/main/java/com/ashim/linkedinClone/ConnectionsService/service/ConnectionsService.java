package com.ashim.linkedinClone.ConnectionsService.service;

import com.ashim.linkedinClone.ConnectionsService.auth.AuthContextHolder;
import com.ashim.linkedinClone.ConnectionsService.entity.Person;
import com.ashim.linkedinClone.ConnectionsService.exception.BadRequestException;
import com.ashim.linkedinClone.ConnectionsService.exception.ResourceNotFoundException;
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

    public List<Person> getSecondDegreeConnections(Long userId) {
        log.info("Getting second degree connections for user {}", userId);
        return personRepository.getSecondDegreeConnections(userId);
    }

    public List<Person> getThirdDegreeConnections(Long userId) {
        log.info("Getting third degree connections for user {}", userId);
        return personRepository.getThirdDegreeConnections(userId);
    }

    public void sendConnectionRequest(Long receiverId){
        Long senderId = AuthContextHolder.getCurrentUserId();

        // check if connection exists, if yes throw exception
        log.info("sending connection request with senderId: {}, receiverId: {}", senderId, receiverId);

        if (senderId.equals(receiverId)) {
            throw new BadRequestException("Both sender and receiver are the same");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        boolean alreadyReceivedRequest = personRepository.connectionRequestExists(receiverId, senderId);
        if (alreadySentRequest || alreadyReceivedRequest ) {
            throw new BadRequestException("Connection request already exists, cannot send again");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected) {
            throw new BadRequestException("Already connected users, cannot add connection request");
        }


        personRepository.addConnectionRequest(senderId, receiverId);
        log.info("Successfully sent the connection request");

    }

    public void acceptConnectionRequest(Long senderId){
        Long receiverId = AuthContextHolder.getCurrentUserId();
        log.info("Accepting a connection request with senderId: {}, receiverId: {}", senderId, receiverId);

        // if already a connection req exist?
        if (senderId.equals(receiverId)) {
            throw new BadRequestException("Both sender and receiver are the same");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected) {
            throw new BadRequestException("Already connected users, cannot accept connection request again");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (! alreadySentRequest) {
            throw new ResourceNotFoundException("No Connection request exists, cannot accept without Request");
        }

        personRepository.acceptConnectionRequest(senderId, receiverId);

        log.info("Successfully accepted the connection request with senderId: {}, receiverId: {}", senderId, receiverId);


    }


    public void rejectConnectionRequest(Long senderId) {
        Long receiverId = AuthContextHolder.getCurrentUserId();
        log.info("Rejecting a connection request with senderId: {}, receiverId: {}", senderId, receiverId);

        // if already a connection req exist?
        if (senderId.equals(receiverId)) {
            throw new BadRequestException("Both sender and receiver are the same");
        }

        // to reject you should have a conenction req open

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (!alreadySentRequest) {
            throw new ResourceNotFoundException("No Connection request exists, cannot reject it");
        }

        personRepository.rejectConnectionRequest(senderId, receiverId);

        log.info("Successfully rejected the connection request with senderId: {}, receiverId: {}", senderId, receiverId);

    }

}
