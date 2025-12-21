package com.Resume.ResumeService.RsumController;

import com.Resume.ResumeService.RsumService.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;
    @PostMapping("/uploadResume")
    public String saveResume(@RequestParam String partyId,@RequestParam MultipartFile file){
        System.out.println("Hey I am inside saveResume method in controller");
        String msg = "";

        try{
            msg = resumeService.saveResume(Long.valueOf(partyId), file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }
    @GetMapping("/GetPartyByResumeSkills")
    public List<Long> searchByKeyword(@RequestParam String keyword){
        System.out.println("Hey I am inside searchByKeyword method of controller");
        List<Long> lstParty = new ArrayList<>();

        try{
            lstParty = resumeService.saerchByKeyWord(keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lstParty;
    }

    @GetMapping("GetResumeTextByPartyId")
    public String getResumeTextByPartyId(@RequestParam String partyId){
        System.out.println("Inside GetResumeTextByPartyId method of controller class");
        String resumeText = "";
        try{
            resumeText = resumeService.getResumeTextByPartyId(Long.valueOf(partyId));
        }catch (Exception e){
            e.printStackTrace();
        }

        return resumeText;
    }

}
