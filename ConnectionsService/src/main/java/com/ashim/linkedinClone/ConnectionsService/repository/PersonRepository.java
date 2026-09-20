package com.ashim.linkedinClone.ConnectionsService.repository;

import com.ashim.linkedinClone.ConnectionsService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Optional<Person> findByUserId(Long userId);

    // Add DISTINCT here
    @Query("MATCH (personA:Person {userId: $userId})-[:CONNECTED_TO]-(personB:Person) RETURN DISTINCT personB")
    List<Person> getFirstDegreeConnections(Long userId);

    // 1st Degree
//    @Query("MATCH (personA:Person {userId: $userId})-[:CONNECTED_TO]-(personB:Person) RETURN personB")
//    List<Person> getFirstDegreeConnections(Long userId);

    //@Query("""
//    MATCH (personA:Person)-[:REQUESTED_TO]-(personB:Person)
//    WHERE personA.userId = $userId
//    RETURN personB
//    """)
//    List<Person> getFirstDegreeConnections(@Param("userId") Long userId);
//

    // CONNECTION REQUEST CODE
    @Query("MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person) " +
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId " +
            "RETURN count(r) > 0")
    boolean connectionRequestExists(Long senderId, Long receiverId);

    @Query("MATCH (p1:Person)-[r:CONNECTED_TO]-(p2:Person) " +
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId " +
            "RETURN count(r) > 0")
    boolean alreadyConnected(Long senderId, Long receiverId);

    @Query("MATCH (p1:Person), (p2:Person) " +
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId " +
            "CREATE (p1)-[:REQUESTED_TO]->(p2)")
    void addConnectionRequest(Long senderId, Long receiverId);

    @Query("MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person) " +
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId " +
            "DELETE r " +
            "CREATE (p1)-[:CONNECTED_TO]->(p2)")
    void acceptConnectionRequest(Long senderId, Long receiverId);

    @Query("MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person) " +
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId " +
            "DELETE r")
    void rejectConnectionRequest(Long senderId, Long receiverId);

    // 2nd Degree: Friends of friends (path length 2), ensuring we don't return the user or their direct friends
    @Query("""
    MATCH (personA:Person)-[:REQUESTED_TO*2]-(personB:Person)
    WHERE personA.userId = $userId 
      AND personA <> personB
      AND NOT (personA)-[:REQUESTED_TO]-(personB)
    RETURN DISTINCT personB
    """)
    List<Person> getSecondDegreeConnections(@Param("userId") Long userId);

    // 3rd Degree: Path length 3, excluding user, 1st-degree, and 2nd-degree connections
    @Query("""
    MATCH (personA:Person)-[:REQUESTED_TO*3]-(personB:Person)
    WHERE personA.userId = $userId 
      AND personA <> personB
      AND NOT (personA)-[:REQUESTED_TO]-(personB)
      AND NOT (personA)-[:REQUESTED_TO*2]-(personB)
    RETURN DISTINCT personB
    """)
    List<Person> getThirdDegreeConnections(@Param("userId") Long userId);

}

// MATCH (personA:Person)-[:CONNECTED_TO]-(personB:Person)