package com.Job.JobService.JobService;

import com.Job.JobService.EntityDetails.EmployeeEntityDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8082", value = "partyclient")
public interface PartyClient {
    @GetMapping("party/getPartyMatchingskills")
    public String getPartyBySkills(@RequestParam String skills);

    @GetMapping("party/getPartyByPartyId")
    public EmployeeEntityDetails getPartyByPartyId(@RequestParam Long id, @RequestParam String user);




}
