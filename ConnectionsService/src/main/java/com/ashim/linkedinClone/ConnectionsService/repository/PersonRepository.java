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