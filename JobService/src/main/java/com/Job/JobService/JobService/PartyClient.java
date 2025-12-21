package com.Job.JobService.JobService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8082", value = "partyclient")
public interface PartyClient {
    @GetMapping("PartyController/getPartyMatchingskills")
    public String getPartyBySkills(@RequestParam String skills);


}
