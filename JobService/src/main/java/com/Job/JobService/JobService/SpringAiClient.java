package com.Job.JobService.JobService;

import com.Job.JobService.EntityDetails.ResumeScoreRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "http://localhost:8085", value = "springAiClient")
public interface SpringAiClient {
    @GetMapping("SpringAiController/askQuery")
    public String askQuery(@RequestParam String text);

    @PostMapping(value = "SpringAiController/scoreResume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String scoreResume(@RequestBody ResumeScoreRequest resumeScoreRequest);

    @PostMapping(value = "SpringAiController/findApplyJobs")
    public String findApplyJobs(@RequestParam String prompt);

    @PostMapping("/sendNotification")
    public void SendNotification(@RequestParam String text);
}
