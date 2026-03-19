package com.Resume.ResumeService.RsumService;


import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    public String saveResume(Long PartyId, MultipartFile file);
    public List<Long> saerchByKeyWord(String keyWord);
    public String getResumeTextByPartyId(Long partyId);

    String downloadResume(Long partyId);
}
