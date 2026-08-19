package com.ashim.linkedinClone.ConnectionsService.repository;

import com.ashim.linkedinClone.ConnectionsService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Optional<Person> findByUserId(String userId);

    @Query("""
    MATCH (personA:Person)-[:CONNECTED_TO]-(personB:Person)
    WHERE personA.userId = $userId
    RETURN personB
    """)
    List<Person> getFirstDegreeConnections(@Param("userId") Long userId);
}
