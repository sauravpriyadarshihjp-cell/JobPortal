package com.Party.PartyService.UWRService;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(url = "http://localhost:8084", name = "resumeClient")
public interface ResumeClient {
    @PostMapping(value = "resume/uploadResume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String UploadResume(@RequestPart String partyId, @RequestPart MultipartFile file);

    @GetMapping(value = "resume/downloadResume")
    public String downloadResume(@RequestParam Long partyId);

}
