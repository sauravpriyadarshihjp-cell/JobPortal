package com.Job.JobService.JobService;

//import org.apache.tomcat.util.http.parser.MediaType;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.awt.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(url = "http://localhost:8084", value = "jobclient")
public interface JobClient {
    @PostMapping(value = "resume/uploadResume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String UploadResume(@RequestPart String partyId, @RequestPart MultipartFile file);

    @GetMapping(value = "resume/GetPartyByResumeSkills")
    public List<Long> GetPartyByResumeSkills(@RequestParam String keyword);

    @GetMapping(value = "resume/GetResumeTextByPartyId")
    public String GetResumeTextByPartyId(@RequestParam String partyId);
}
