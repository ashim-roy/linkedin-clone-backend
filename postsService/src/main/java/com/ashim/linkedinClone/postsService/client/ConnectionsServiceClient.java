package com.ashim.linkedinClone.postsService.client;

import com.ashim.linkedinClone.postsService.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connections-service", path = "/connections")
public interface ConnectionsServiceClient {

    @GetMapping("/core/{userId}/first-degree")
    List<PersonDto> getFirstDegreeConnections(@PathVariable("userId") Long userId);
}


//@FeignClient(name = "connections-service", path = "/connections") + @GetMapping("/core/{userId}/first-degree") resolves to
// http://connections-service/connections/core/{userId}/first-degree.